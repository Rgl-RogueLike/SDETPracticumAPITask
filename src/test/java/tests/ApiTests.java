package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import steps.EntitySteps;

public class ApiTests extends BaseTest{

    private static final EntitySteps entitySteps = new EntitySteps();

    @Test
    public void testCreateEntity() {
        Integer id = entitySteps.createEntity(specificationRequest, config.createUrl(),
                config.lowerLimitSizeImportantNumbers(), config.upperLimitSizeImportantNumbers(),
                config.lowerNumber(), config.upperNumber());

        Assertions.assertNotNull(id);
    }
}
