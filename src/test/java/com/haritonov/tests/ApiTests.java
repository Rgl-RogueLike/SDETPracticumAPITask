package com.haritonov.tests;

import com.haritonov.dto.EntityFilterResponse;
import com.haritonov.dto.EntityResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import com.haritonov.steps.EntitySteps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiTests extends BaseTest{

    private static final EntitySteps entitySteps = new EntitySteps();
    private static final List<Integer> createdIds = Collections.synchronizedList(new ArrayList<>());

    private static final Object SERVER_LOCK = new Object();

    @Test
    @Order(1)
    @DisplayName("Create entity")
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
    @Order(2)
    @DisplayName("Get entity and check data")
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
    @Order(3)
    @DisplayName("Get all entities")
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
    @Order(4)
    @DisplayName("Update entity")
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
    @Order(5)
    @DisplayName("Delete entity")
    public void testDeleteEntity() {
        Integer createdId;
        Response response;
        synchronized (SERVER_LOCK) {
            createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                    config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                    config.lowerNumber(), config.upperNumber());
            entitySteps.deleteEntity(requestSpecification, config.deleteUrl(), createdId);
            response = entitySteps.tryGetDeletedEntity(requestSpecification, createdId, config.getUrl());
        }
        Assertions.assertNotEquals(HttpStatus.SC_OK, response.getStatusCode(), "Entity не должна быть доступна после удаления");
    }

    @AfterAll
    public static void cleanUp() {
        for (Integer id : createdIds) {
            try {
                entitySteps.deleteEntity(requestSpecification, config.deleteUrl(), id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
