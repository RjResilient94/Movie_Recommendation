FROM python:3.10-slim

WORKDIR /app

COPY . /app

RUN pip install --no-cache-dir flask scikit-surprise pandas

EXPOSE 5000

CMD ["python", "app.py"]
