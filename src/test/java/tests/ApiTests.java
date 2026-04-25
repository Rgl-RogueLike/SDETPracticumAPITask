package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import steps.EntitySteps;

public class ApiTests extends BaseTest{

    private static final EntitySteps entitySteps = new EntitySteps();

    @Test
    public void testCreateEntity() {
        String randomTitle = entitySteps.generateRandomTitle();
        Boolean isVerified = entitySteps.generateRandomVerified();
        Integer id = entitySteps.createEntity(specificationRequest, randomTitle, isVerified, config.createUrl());

        Assertions.assertNotNull(id);
    }
}
