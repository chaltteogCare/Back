from flask import Flask, request, jsonify
import pandas as pd
from tensorflow.keras.models import load_model

app = Flask(__name__)

model = load_model('C:/Users/dooly/Downloads/path_to_my_model/my_model.h5')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json(force=True)
    df = pd.DataFrame(data, index=[0])

    df = df[['Water Usage', 'Electricity Usage', 'Phone Usage']]
    df = df.apply(pd.to_numeric, errors='coerce')

    prediction = model.predict(df)
    return jsonify({'prediction': prediction.tolist()})


if __name__ == '__main__':
    app.run(debug=True, port=5000)
