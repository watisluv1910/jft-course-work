FROM python:3.12-alpine

WORKDIR /app

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
ENV DEBUG 0

RUN apk update \
    && apk add --virtual build-essential gcc python3-dev musl-dev

COPY ./requirements.txt .
RUN pip install -r requirements.txt

COPY . .

RUN adduser -D myuser
USER myuser

CMD gunicorn dashboard.wsgi:application --bind 0.0.0.0:$PORT

# docker build -t pract11:latest .
# docker run -d --name ssd_pract11 -e "PORT=8765" -e "DEBUG=1" -p 8007:8765 pract11:latest