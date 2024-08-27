from flask import Flask, request, jsonify
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import f1_score

app = Flask(__name__)


# 데이터 전처리 함수
def preprocess_data():
    phone_data = pd.read_excel(
        'service_data_240501.xlsx',
        engine='openpyxl')
    water_data = pd.read_csv(
        'water_usage_20221231.csv',
        encoding='euc-kr')

    # 전화 사용량 데이터 정리
    phone_data['Phone_Usage'] = pd.to_numeric(phone_data['Unnamed: 4'].str.replace('건', '').str.strip(),
                                              errors='coerce')
    phone_data = phone_data.dropna(subset=['Phone_Usage'])

    # 수도 사용량 데이터 정리
    water_data['사용량'] = pd.to_numeric(water_data['사용량'], errors='coerce')
    water_data = water_data.dropna(subset=['사용량'])

    # 필수 데이터 추출
    household_water_usage = water_data[water_data['업종'] == '가정용'].copy()
    household_water_usage['총 사용량 (리터)'] = household_water_usage['사용량'] * 1000
    household_water_usage['1인 가구 사용량'] = household_water_usage['총 사용량 (리터)'] / household_water_usage['가구수']
    household_water_usage['일평균 1인 가구 사용량'] = household_water_usage['1인 가구 사용량'] / 365

    return phone_data, household_water_usage


# 랜덤 데이터를 기반으로 위험 판별 함수 (AND 조건 사용)
def generate_random_data(phone_data, household_water_usage, n_samples=1000):
    # 수도 사용량, 전화 사용량, 전력 사용량의 평균과 표준편차를 구함
    avg_water_usage = household_water_usage['일평균 1인 가구 사용량'].mean()
    stddev_water_usage = household_water_usage['일평균 1인 가구 사용량'].std()

    avg_phone_usage = phone_data['Phone_Usage'].mean()
    stddev_phone_usage = phone_data['Phone_Usage'].std()

    avg_power_usage = 211
    stddev_power_usage = 70  # 전력 사용량에 대한 임의의 표준편차

    # 랜덤 데이터 생성
    random_data = pd.DataFrame({
        'Water_Usage': np.random.normal(avg_water_usage, stddev_water_usage, n_samples),
        'Phone_Usage': np.random.normal(avg_phone_usage, stddev_phone_usage, n_samples),
        'Power_Usage': np.random.normal(avg_power_usage, stddev_power_usage, n_samples)
    })

    # 음수 값을 0으로 변환
    random_data['Phone_Usage'] = random_data['Phone_Usage'].clip(lower=0)

    return random_data


# 모델을 통해 최적의 임계값 설정
# 모델을 통해 최적의 임계값 설정
def find_best_threshold(data, percentiles):
    best_f1_score = 0
    best_threshold = {}

    for perc in percentiles:
        data['Water_Risk'] = (data['Water_Usage'] < data['Water_Usage'].quantile(perc)).astype(int)
        data['Phone_Risk'] = (data['Phone_Usage'] < data['Phone_Usage'].quantile(perc)).astype(int)
        data['Power_Risk'] = (data['Power_Usage'] < data['Power_Usage'].quantile(perc)).astype(int)

        data['Final_Risk'] = data.apply(
            lambda row: 1 if row['Water_Risk'] == 1 and row['Phone_Risk'] == 1 and row['Power_Risk'] == 1 else 0,
            axis=1)

        X = data[['Water_Usage', 'Phone_Usage', 'Power_Usage']]
        y = data['Final_Risk']

        # 데이터가 단일 클래스(모두 0 또는 모두 1)로만 이루어져 있는 경우를 건너뜁니다.
        if len(y.unique()) < 2:
            continue  # 이 부분에서 continue를 통해 학습을 건너뜁니다.

        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

        model = LogisticRegression()
        model.fit(X_train, y_train)

        y_pred = model.predict(X_test)

        f1 = f1_score(y_test, y_pred)
        if f1 > best_f1_score:
            best_f1_score = f1
            best_threshold = {
                'percentile': perc,
                'water_threshold': data['Water_Usage'].quantile(perc),
                'phone_threshold': data['Phone_Usage'].quantile(perc),
                'power_threshold': data['Power_Usage'].quantile(perc),
            }

    return best_threshold



def predict_risk(water_usage, phone_usage, power_usage, best_thresholds):
    water_risk = 1 if water_usage < best_thresholds['water_threshold'] else 0
    phone_risk = 1 if phone_usage < best_thresholds['phone_threshold'] else 0
    power_risk = 1 if power_usage < best_thresholds['power_threshold'] else 0

    final_risk = 1 if water_risk or phone_risk or power_risk else 0

    return final_risk



@app.route('/predict-risk', methods=['POST'])
def predict_risk_endpoint():
    data = request.get_json()

    if not data:
        return jsonify({'error': 'No data provided'}), 400

    try:
        phone_data, household_water_usage = preprocess_data()

        random_data = generate_random_data(phone_data, household_water_usage, n_samples=1000)

        percentiles = np.linspace(0.01, 0.3, 30)

        best_thresholds = find_best_threshold(random_data, percentiles)

        water_usage = float(data['water_usage'])
        phone_usage = float(data['phone_usage'])
        power_usage = float(data['power_usage'])

        final_risk = predict_risk(water_usage, phone_usage, power_usage, best_thresholds)

        return jsonify({'final_risk': final_risk})

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
