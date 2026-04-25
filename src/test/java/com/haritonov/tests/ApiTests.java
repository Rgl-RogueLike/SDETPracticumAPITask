package com.haritonov.tests;

import com.haritonov.dto.EntityFilterResponse;
import com.haritonov.dto.EntityResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.haritonov.steps.EntitySteps;

public class ApiTests extends BaseTest{

    private static final EntitySteps entitySteps = new EntitySteps();

    @Test
    @DisplayName("Create entity")
    public void testCreateEntity() {
        Integer id = entitySteps.createEntity(requestSpecification, config.createUrl(),
                config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                config.lowerNumber(), config.upperNumber());

        Assertions.assertNotNull(id);
    }

    @Test
    @DisplayName("Get entity and check data")
    public void testGetEntity() {
        Integer createdId = entitySteps.createEntity(requestSpecification, config.createUrl(),
                config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                config.lowerNumber(), config.upperNumber());

        EntityResponse response = entitySteps.getEntity(requestSpecification, createdId, config.getUrl());

        Assertions.assertEquals(createdId, response.getId(), "Id должен совпадать");
        Assertions.assertNotNull(response.getTitle(), "Title не должен быть null");
    }

    @Test
    @DisplayName("Get all entities")
    public void testGetAllEntities() {
        entitySteps.createEntity(requestSpecification, config.createUrl(),
                config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                config.lowerNumber(), config.upperNumber());

        EntityFilterResponse response = entitySteps.getAllEntities(requestSpecification, config.getAllUrl());
        Assertions.assertNotNull(response.getEntity(), "Список не должен быть null");
        Assertions.assertFalse(response.getEntity().isEmpty(), "Список не должен быть пустым");
    }
}
