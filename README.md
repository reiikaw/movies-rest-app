# Простой бекенд веб-приложения по работе с сущностью фильма

В рамках проекта было создано веб-приложение, реализующее REST API для управления сущностью фильма (Movie) посредством операций CRUD (create, read, update, delete).
Сущность фильма в базе данных представлена в виде таблицы:

```
movies (
    id uuid NOTNULL UNIQUE PK,      // идентификатор фильма
    title varchar(255) NOTNULL,     // название фильма
    director varchar(255) NOTNULL,  // режисер фильма
    release_year int4 NOTNULL,      // год показа
    rating float8 NOTNULL,          // рейтинг фильма
    available bool NOTNULL          // доступность
)
```

Фильм имеет флаг `available`, который позволяет отображать пользователям только доступные фильмы. Недоступные фильмы доступны только пользователя с правами администратора.

В системе также хранится информация о пользователе:

```
users (
    id uuid NOTNULL UNIQUE PK,      // идентификатор пользователя
    username varchar(255) NOTNULL,  // имя пользователя
    password varchar(255) NOTNULL,  // хеш пароля пользователя
    is_admin bool NOTNULL,          // флаг прав администратора
    registered_at timestamp NOTNULL // дата и время регистрации
)
```

Пользователи регистрируются в системе и автоматически получают `is_admin = false` и роль `USER`. Получение прав администратора осуществляется путем внесения правок в базу данных. В этом случае у пользователя будет роль `ADMIN`.

В качестве авторизации используется Json Web Token (JWT), который выдается пользователю при регистрации или авторизации. Данный токен должен быть указан во всех запросах по управлению сущностью фильма в виде заголовка к запросу `Authorization: Bearer <jwt_token>`.

## Стек технологий
- Основной ЯП: Java 21
- Основной фреймворк: Spring Boot 4.0.3
- Работа с REST и разворачивание веб сервера: Spring Web MVC
- Работа с БД: Spring Data JPA
- Реализация авторизации: Spring Security
- Реализация валидации: Spring Validation
- Подключение Swagger: SpringDoc OpenAPI
- Миграции: Liquibase
- Реализация мапперов: MapStruct
- Работа с JWT: jjwt-api, jjwt-impl, jjwt-jackson (Homepage: https://github.com/jwtk/jjwt)
- Вспомогательный инструмент для упрощения кода: Lombok
- СУБД: PostgreSQL + JDBC driver
- Сборка: Apache Maven

## Как запустить

Требования:
- Java 21 SDK
- Apache Maven 3.9+
- PostgreSQL 15+
- Docker

1. Загрузить проект
2. В файл `.env` указать все необходимые секреты
3. Из корневой директории проекта выполнить команды:
```
# Создание wrapper
mvn wrapper:wrapper

# Сборка контейнера
docker build -t movies-rest-app . 

# Запуск контейнера
docker run -p 8080:8080 --env-file=.env movies-rest-app
```

### Список конечных точек приложения
Swagger доступен по ссылке после запуска приложения: `http://localhost:8080/swagger-ui/index.html#/`