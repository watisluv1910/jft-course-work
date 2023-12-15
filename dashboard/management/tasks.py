from celery import shared_task
import requests
import time


@shared_task
def fetch_management_data():
    response = requests.get(
        'http://localhost:8080/management')
    data = response.json()
    print(data)
