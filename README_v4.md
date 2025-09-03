# TechTally Inspector v4.0

Современная система учёта оргтехники, мигрированная с PHP на современный стек технологий.

## Стек технологий

### Backend
- **Spring Boot 3.2** - основной фреймворк
- **Spring WebFlux** - реактивное программирование
- **R2DBC** - реактивная работа с базой данных
- **Liquibase** - миграции базы данных
- **Quartz Scheduler** - планировщик задач
- **PostgreSQL/MySQL** - базы данных (автоматический выбор)

### Frontend
- **React 18** - пользовательский интерфейс
- **TypeScript** - типизированный JavaScript
- **Redux Toolkit** - управление состоянием
- **TanStack Query** - кеширование и синхронизация данных
- **Vite.js** - система сборки
- **SCSS** - препроцессор CSS

### Build & Deployment
- **Gradle** - система сборки
- **Java 17** - среда выполнения
- **Docker-ready** - готово к контейнеризации

## Возможности

### Управление оборудованием
- Каталогизация всего оборудования
- Отслеживание серийных и инвентарных номеров
- Управление статусами (активное, неактивное, на обслуживании, списанное)
- Отслеживание сроков гарантии
- История изменений оборудования

### Управление пользователями
- Аутентификация и авторизация
- Ролевая модель доступа
- Профили пользователей
- Сессии и токены безопасности

### Отчётность и аналитика
- Дашборды с ключевыми метриками
- Отчёты по оборудованию
- Уведомления о истекающих гарантиях
- Аудит действий пользователей

### Интеграции
- Поддержка биллинговых систем
- Файловое хранилище
- Система чата и поддержки
- REST API для внешних интеграций

## Быстрый старт

### Предварительные требования
- Java 17+
- Node.js 18+
- PostgreSQL 12+ или MySQL 8+
- Gradle 8.5+

### Установка и запуск

1. **Клонирование репозитория**
```bash
git clone https://github.com/NikolaiJDev/techtallyinspector.git
cd techtallyinspector
```

2. **Настройка базы данных**
```bash
# Для PostgreSQL
export DB_TYPE=postgresql
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=techtallyinspector
export DB_USERNAME=postgres
export DB_PASSWORD=your_password

# Для MySQL
export DB_TYPE=mysql
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=techtallyinspector
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

3. **Сборка и запуск**
```bash
# Полная сборка (backend + frontend)
./gradlew build

# Запуск приложения
./gradlew bootRun

# Или запуск JAR файла
java -jar build/libs/techtallyinspector-app.jar
```

4. **Доступ к приложению**
- Веб-интерфейс: http://localhost:8080
- API документация: http://localhost:8080/actuator
- Логин по умолчанию: `admin` / `admin123`

### Разработка

#### Backend разработка
```bash
# Запуск только backend в режиме разработки
./gradlew bootRun --args='--spring.profiles.active=development'
```

#### Frontend разработка
```bash
cd frontend
npm install
npm run dev
```
Frontend будет доступен на http://localhost:3000 с proxy на backend.

## Конфигурация

### Переменные окружения

| Переменная | Описание | По умолчанию |
|------------|----------|--------------|
| `DB_TYPE` | Тип БД (postgresql/mysql) | `postgresql` |
| `DB_HOST` | Хост БД | `localhost` |
| `DB_PORT` | Порт БД | `5432` |
| `DB_NAME` | Имя БД | `techtallyinspector` |
| `DB_USERNAME` | Пользователь БД | `postgres` |
| `DB_PASSWORD` | Пароль БД | `postgres` |
| `JAVA_OPTS` | Опции JVM | `-Xmx1g` |

### Профили Spring

- `development` - режим разработки
- `production` - продакшн режим
- `mysql` - специфичные настройки для MySQL

## Миграция с версии 3.x

Приложение включает автоматическую миграцию схемы базы данных при первом запуске.

1. Создайте резервную копию текущей БД
2. Настройте новые переменные окружения
3. Запустите новую версию - миграции выполнятся автоматически

## API

### Основные endpoints

#### Оборудование
- `GET /api/equipment` - список оборудования
- `POST /api/equipment` - добавить оборудование
- `PUT /api/equipment/{id}` - обновить оборудование
- `DELETE /api/equipment/{id}` - удалить оборудование

#### Пользователи
- `GET /api/users` - список пользователей
- `POST /api/users` - создать пользователя
- `PUT /api/users/{id}` - обновить пользователя

#### Мониторинг
- `GET /actuator/health` - статус приложения
- `GET /actuator/metrics` - метрики
- `GET /actuator/info` - информация о версии

## Лицензия

GPL v3 - см. файл LICENSE

## Поддержка

- GitHub Issues: https://github.com/NikolaiJDev/techtallyinspector/issues
- Telegram: @DonPadlos

## Changelog

### v4.0.0 (2024)
- ✅ Полная миграция на Spring Boot + React
- ✅ Реактивное программирование с WebFlux
- ✅ Современный UI с TypeScript
- ✅ Liquibase для миграций БД
- ✅ Quartz для планировщика
- ✅ Поддержка PostgreSQL и MySQL
- ✅ Единый JAR с встроенным frontend

### v3.x (2016)
- Последняя PHP версия
- Устарела и не поддерживается