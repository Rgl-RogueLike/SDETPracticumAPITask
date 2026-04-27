SDET Practicum API Task

Проект для автоматизации тестирования API (Entity Management) с использованием Java 17, JUnit 5 и Rest Assured.

Тестируемый сервис: https://github.com/sun6r0/test-service

Стек технологий

    Java 17
    Maven
    JUnit 5
    Jackson
    Rest Assured
    Allure
    Owner
    Java Faker
    Lombok
    Docker & Docker Compose

Тестовые сценарии:

    Create Entity (testCreateEntity): Проверяет, что новая сущность может быть успешно создана;
    Get Entity (testGetEntiity): Проверяет, что созданная сущность возвращает правильные данные;
    Get All  Entities (testGetAllEntities): Проверяет, возвращает ли API список объектов;
    Update Entity (testPatchEntity): Проверяет, что поля сущности обновлены с помощью PATCH;
    Delete Entity (testDeleteEntity): Проверяет, что созданная сущность успешно удалена.

