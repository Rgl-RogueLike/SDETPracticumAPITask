# SDET Practicum: API Test Automation

CRUD-автотесты для учебного сервиса управления сущностями. Проверяются создание, чтение, обновление и удаление сущностей через REST API, а также очистка тестовых данных после прогона.

## Тестируемый сервис

Тестируемый сервис — публичный репозиторий [sun6r0/test-service](https://github.com/sun6r0/test-service). Он **не является моей разработкой** и используется как тестовый стенд. Код сервиса не входит в этот репозиторий и не распространяется вместе с ним: он подключён как git submodule.

## Что проверяется

| Тест | Запросы | Проверка |
|---|---|---|
| `testCreateEntity` | `POST /api/create` | сущность создаётся, возвращается непустой `id` |
| `testGetEntity` | `POST /api/create` → `GET /api/get/{id}` | `id` совпадает, `title` заполнен |
| `testGetAllEntities` | `POST /api/create` → `GET /api/getAll` | список сущностей не пустой |
| `testPatchEntity` | создание → `PATCH /api/patch/{id}` → чтение | изменились `title`, `verified`, `important_numbers` и вложенный `addition` |
| `testDeleteEntity` | `POST /api/create` → `DELETE /api/delete/{id}` → `GET` | после удаления сущность недоступна (статус не `200`) |

После всех тестов `BaseTest.cleanUp()` удаляет созданные сущности, чтобы прогон был повторяемым.

## Стек

- Java 17
- Maven
- JUnit 6 (Jupiter)
- REST Assured
- Jackson — сериализация DTO
- Lombok
- Owner — конфигурация из `config.properties`
- JavaFaker — генерация тестовых данных
- Allure
- Docker Compose — запуск сервиса и PostgreSQL

## Структура проекта

```text
.github/workflows/api-tests.yml       # CI: submodule, docker compose, тесты, Allure
docker-compose.yml                    # PostgreSQL, миграции и test-service
.env.example                          # шаблон переменных окружения
src/test/java/com/haritonov/
├── config/Configuration.java         # Owner-конфигурация
├── dto/                              # запросы и ответы API
├── steps/EntitySteps.java            # шаги работы с API
└── tests/
    ├── BaseTest.java                 # спецификация запросов и очистка данных
    └── ApiTests.java                 # пять CRUD-тестов
src/test/resources/configurations/config.properties
```

## Запуск

Требования: JDK 17, Maven, Docker и Docker Compose.

### 1. Клонирование вместе с submodule

```bash
git clone --recurse-submodules https://github.com/Rgl-RogueLike/SDETPracticumAPITask.git
cd SDETPracticumAPITask
```

Если репозиторий уже склонирован без submodule:

```bash
git submodule update --init --recursive
```

### 2. Переменные окружения

```bash
cp .env.example .env
```

Файл `.env` содержит только локальные тестовые значения и не должен коммититься в git.

### 3. Запуск сервиса и базы

```bash
docker compose up -d --build
```

Дождитесь готовности сервиса:

```bash
curl http://localhost:8080/api/time
```

### 4. Прогон тестов

```bash
mvn clean test
```

### 5. Отчёт Allure

```bash
mvn allure:serve
```

Пример отчёта — в [`Screenshots/AllureScreenshot.png`](Screenshots/AllureScreenshot.png).

### Остановка стенда

```bash
docker compose down -v
```

## Конфигурация

`src/test/resources/configurations/config.properties`:

| Параметр | Значение по умолчанию |
|---|---|
| `baseUrl` | `http://localhost:8080/` |
| `createUrl`, `getUrl`, `getAllUrl`, `patchUrl`, `deleteUrl` | пути `/api/*` |
| `lowerLimitSizeImportantNumbers`, `upperLimitSizeImportantNumbers` | границы количества чисел в `important_numbers` |
| `lowerNumber`, `upperNumber` | диапазон генерируемых чисел |

## CI

Workflow `.github/workflows/api-tests.yml` запускается на push и pull request:

1. клонирует репозиторий с submodule;
2. поднимает JDK 17;
3. запускает `docker compose up -d --build`;
4. ждёт `http://localhost:8080/api/time`;
5. выполняет `mvn clean test`;
6. генерирует и сохраняет Allure-отчёт артефактом.

## Особенности

- Тесты работают с локальным стендом на `localhost:8080`.
- Созданные сущности удаляются в `@AfterAll`.
- Запросы к серверу синхронизированы через `SERVER_LOCK`, чтобы параллельный запуск не влиял на результаты.
- Если submodule недоступен, поднимите любой mock с теми же эндпоинтами на `localhost:8080` — тесты не зависят от внутреннего устройства сервиса.
