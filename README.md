# Weather Service

Простой сервис для получения прогноза погоды на ближайшие 24 часа по названию города с кэшированием в Redis.

---

## Особенности

- GET `/weather?city={city}` — получение прогноза погоды по названию города (например, `Moscow`, `Berlin`).
- При первом запросе:
    1. Получает координаты города через API геокодинга.
    2. Получает прогноз погоды на ближайшие 24 часа через погодный API.
    3. Сохраняет данные в Redis с временем жизни 15 минут.
- При последующих запросах в течение 15 минут данные берутся из Redis, без повторного обращения к внешним API.
- Если данные устарели или отсутствуют в кэше, сервис обновляет информацию автоматически.

---
## Фронтенд
В проекте есть папка front, которая содержит:
* index.html — основная страница приложения
* style.css — стили для красивого отображения
* script.js — логика

Чтобы использовать откройте index.html в браузере.

---

## Установка и запуск
1. Склонируйте репозиторий:

```bash
git clone <repo-url>
cd weather-service
```

2. В корне проекта выполните:
```bash
docker-compose up --build
```

3. Проверка работы
```bash
curl http://localhost:8080/weather?city=Moscow

```
4. Остановка и очистка
```bash
docker-compose down
```
Очистить данные Redis (если нужно):
```bash
docker-compose down -v
```

---

## Структура проекта

```
src/
└─ main/
└─ java/
└─ org/example/
├─ api/ # Клиенты для внешних API
│  ├─ geo/ # Работа с геоданными
│  └─ weather/ # Погодные API
│     ├─ OpenMeteoApi
│     └─ WeatherApi
├─ controllers/ # Контроллеры для обработки запросов
│  └─ WeatherController
├─ http/ # HTTP клиент
│  ├─ HttpClient
│  └─ HttpClientInterface
├─ server/ # HTTP сервер и wrapper
│  ├─ AppConfig
│  ├─ AppContext
│  ├─ HandlerWrapper
│  └─ Server
├─ services/ # Логика приложения
│  ├─ cache/ # Сервисы кэширования
│  │  ├─ ICacheService
│  │  └─ RedisCacheService
│  └─ weather/ # Погодные сервисы
│     ├─ IWeatherService
│     └─ WeatherService
└─ utils/ # Вспомогательные методы
```
