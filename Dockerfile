# Базовый образ с необходимыми языками
FROM ubuntu:22.04

# Установка зависимостей
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    tzdata \
    openjdk-17-jdk \
    python3 \
    python3-pip \
    curl \
    git \
    postgresql \
    postgresql-contrib \
    && ln -fs /usr/share/zoneinfo/Europe/Moscow /etc/localtime \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

# Установка зависимостей Python
WORKDIR /python-app
COPY Vault-add_auth_system/requirements.txt ./
RUN pip3 install --no-cache-dir -r requirements.txt

# Установка зависимостей Node.js (React)
WORKDIR /react-app
COPY package.json ./
RUN npm install

# Сборка React приложения
COPY Vault-Roman.K-frontend ./ 
COPY Vault-Roman.K-frontend/src ./src/
RUN npm run build

# Копируем Java-приложение
WORKDIR /java-app
COPY AnotherVaultApplication.jar ./

# Копируем SQL-дамп файла в контейнер
WORKDIR /
COPY dump_file.sql /docker-entrypoint-initdb.d/dump_file.sql

# Установка supervisor для запуска нескольких процессов
RUN apt-get install -y supervisor

# Создание конфигурационного файла для supervisor
RUN mkdir -p /etc/supervisor/conf.d

# Создание директории для логов и изменение прав
RUN mkdir -p /var/log/supervisor && \
    chmod 777 /var/log/supervisor

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Конфигурация PostgreSQL
USER postgres
RUN service postgresql start && \
    psql -c "CREATE USER myuser WITH PASSWORD 'mypassword';" && \
    psql -c "CREATE DATABASE mydb OWNER myuser;"

# Загрузка дампа базы данных
RUN service postgresql start && \
    psql -d mydb -f /docker-entrypoint-initdb.d/dump_file.sql

# Открытие необходимых портов
EXPOSE 8080 5000 80

# Возврат к пользователю root для запуска supervisord
USER root

# Запуск supervisor для управления всеми процессами
CMD ["supervisord", "-c", "/etc/supervisor/supervisord.conf"]

