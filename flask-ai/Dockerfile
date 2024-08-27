FROM python:3.8-slim

WORKDIR /app

COPY risk_model.py /app/risk_model.py
COPY water_usage_20221231.csv /app/water_usage_20221231.csv
COPY service_data_240501.xlsx /app/service_data_240501.xlsx

RUN pip install flask pandas openpyxl

ENTRYPOINT ["python", "risk_model.py"]
