# Interview Flow

Interview Flow — это backend-приложение для подготовки к техническим интервью.  
Система позволяет пользователям проходить тренировочные интервью по различным профессиям и темам, отслеживать прогресс и повторять сложные вопросы.

## Основные возможности

- Регистрация и авторизация пользователей
- Прохождение интервью по выбранной профессии
- Поддержка вопросов:
    - SINGLE choice
    - MULTIPLE choice
    - TEXT
- Проверка ответов и подсчёт результатов
- Хранение прогресса пользователя
- Алгоритм повторения сложных вопросов
- REST API
- Session-based authentication
- Docker support

---

# Технологии

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MapStruct

## Database
- PostgreSQL
- Redis

## Infrastructure
- Docker
- Docker Compose

## Migration
- Liquibase
- 
## Build Tool
- Gradle

---


# Основной функционал

## Авторизация

Используется session-based authentication:
- cookies;
- server-side sessions;
- Spring Security.

Сессии могут храниться в Redis.

---

## Интервью

Пользователь:
1. выбирает профессию;
2. выбирает тему;
3. запускает попытку;
4. отвечает на вопросы;
5. получает результат.

---

## Алгоритм повторений

Система отслеживает:
- правильность ответов;
- частоту ошибок;
- сложные вопросы.

На основе статистики вопросы могут повторяться чаще или реже.

---


# Запуск проекта

---

## Клонирование репозитория

```bash
git clone https://github.com/progrohan/interview-flow
cd interview-flow
```
---

## .env

Создайте в корне проекта .env файл и заполните по следующему шаблону:

##### DB_HOST= 
##### DB_PORT= 
##### DB_NAME= 
##### DB_USERNAME= 
##### DB_PASSWORD=


---

## Запуск через Docker

```bash
docker-compose up --build
```

---

# Безопасность

Используется:
- Spring Security;
- BCrypt password hashing;
- session authentication;
- cookies;
- CORS configuration.

---

