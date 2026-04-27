package com.haritonov.tests;

import com.haritonov.dto.EntityFilterResponse;
import com.haritonov.dto.EntityResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

/**
 * Класс тестирования API управления сущностями (Entity).
 * Содержит позитивные проверки методов CRUD (Create, Read, Update, Delete).
 * Использует параллельное выполнение с синхронизацией доступа к серверу.
 */
@Epic("Entity Management API")
@Feature("Entity Operations")
public class ApiTests extends BaseTest{

    private static final Object SERVER_LOCK = new Object();

    @Test
    @DisplayName("Create entity")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create entity with random data")
    @Description("Verify that entity can be successfully created")
    public void testCreateEntity() {
        Integer createdId;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
        }
        createdIds.add(createdId);
        Assertions.assertNotNull(createdId);
    }

    @Test
    @DisplayName("Get entity and check data")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get entity by ID and verify fields")
    @Description("Verify that GET /api/get/{id} returns correct data")
    public void testGetEntity() {
        Integer createdId;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
        }
        createdIds.add(createdId);
        EntityResponse response;
        synchronized (SERVER_LOCK) {
            response = entitySteps.getEntity(requestSpecification, config.getUrl(), createdId);
        }
        Assertions.assertEquals(createdId, response.getId(), "Id должен совпадать");
        Assertions.assertNotNull(response.getTitle(), "Title не должен быть null");
    }

    @Test
    @DisplayName("Get all entities")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get list of all entities")
    @Description("Verify that GET /api/getAll returns a list of entities")
    public void testGetAllEntities() {
        Integer createdId;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
        }
        createdIds.add(createdId);
        EntityFilterResponse response;
        synchronized (SERVER_LOCK) {
            response = entitySteps.getAllEntities(requestSpecification, config.getAllUrl());
        }
        Assertions.assertNotNull(response.getEntity(), "Список не должен быть null");
        Assertions.assertFalse(response.getEntity().isEmpty(), "Список не должен быть пустым");
    }

    @Test
    @DisplayName("Update entity")
    @Severity(SeverityLevel.NORMAL)
    @Story("Update entity fields and verify changes")
    @Description("Verify that PACTH /api/patch/{id} updates entity data")
    public void testPatchEntity() {
        Integer createdId;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
        }
        createdIds.add(createdId);
        EntityResponse entityBeforeUpdate;
        EntityResponse entityAfterUpdate;
        synchronized (SERVER_LOCK) {
            entityBeforeUpdate = entitySteps.getEntity(requestSpecification, config.getUrl(), createdId);
            entitySteps.updateEntity(requestSpecification, config.patchUrl(), createdId, !entityBeforeUpdate.getVerified(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
            entityAfterUpdate = entitySteps.getEntity(requestSpecification, config.getUrl(), createdId);
        }

        Assertions.assertNotNull(entityBeforeUpdate.getAddition(), "Addition не должен быть null после создания");
        Assertions.assertNotEquals(entityBeforeUpdate.getTitle(), entityAfterUpdate.getTitle(), "Title не обновился");
        Assertions.assertNotSame(entityBeforeUpdate.getVerified(), entityAfterUpdate.getVerified(), "Verified не обновился");

        Assertions.assertNotEquals(entityBeforeUpdate.getImportantNumbers(),
                entityAfterUpdate.getImportantNumbers(),
                "ImportantNumbers не обновилось");

        Assertions.assertNotEquals(entityBeforeUpdate.getAddition().getAdditionalInfo(),
                entityAfterUpdate.getAddition().getAdditionalInfo(),
                "AdditionalInfo не обновилась");

        Assertions.assertNotEquals(entityBeforeUpdate.getAddition().getAdditionalNumber(),
                entityAfterUpdate.getAddition().getAdditionalNumber(),
                "AdditionNumber не обновилось");
    }

    @Test
    @DisplayName("Delete entity")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add and delete entity")
    public void testDeleteEntity() {
        Integer createdId;
        Response response;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
            entitySteps.deleteEntity(requestSpecification, config.deleteUrl(), createdId);
            response = entitySteps.tryGetDeletedEntity(requestSpecification, config.getUrl(), createdId);
        }
        Assertions.assertNotEquals(HttpStatus.SC_OK, response.getStatusCode(), "Entity не должна быть доступна после удаления");
    }
}
