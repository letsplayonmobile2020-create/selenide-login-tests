# Selenide Login Tests

Автотесты для страницы логина на сайте [the-internet](https://the-internet.herokuapp.com).  
Реализовано по паттерну Page Object: элементы и логика вынесены в отдельные классы.

## Стек

- Java 11+
- JUnit 5
- Selenide 6.x
- WebDriverManager
- Maven

## Как запустить

1. Убедись, что установлен JDK 11+ и Maven.
2. В корне проекта выполни:
   ```bash
   mvn clean test
   ```
3. Отчёты будут в папке `build/reports/tests`.

## Структура проекта

- `src/test/java/com/qa/simple/pages` — Page Object классы (элементы и их логика).
- `src/test/java/com/qa/simple/steps` — шаги сценариев (декомпозиция действий).
- `src/test/java/com/qa/simple/tests` — тестовые классы (сценарии).
- `src/test/resources` — конфигурационные файлы.
- `com/qa/simple/config` — утилита для чтения конфигурации.

## Конфигурация

URL целевой страницы задаётся:
- либо в переменной окружения: `-Dselenide.baseUrl=https://...`
- либо в `config.properties` (не хранится в Git).

Пример запуска с переопределением URL:
```bash
mvn clean test -Dselenide.baseUrl=https://the-internet.herokuapp.com
```

## Особенности реализации

- **Page Object**: все локаторы и действия инкапсулированы в `LoginPage`.
- **Конфигурация**: вынесена в отдельный класс `TestConfig`, поддерживает подмену через переменные окружения.
- **Игнорирование мусора**: `.gitignore` исключает `target`, отчёты и конфиги с данными.

## Лицензия

Проект для учебных целей.
```