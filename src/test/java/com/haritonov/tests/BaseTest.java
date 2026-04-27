package com.haritonov.tests;

import com.haritonov.config.Configuration;
import com.haritonov.steps.EntitySteps;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseTest {
    protected static final Configuration config = ConfigFactory.create(Configuration.class, System.getenv());
    protected static final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(config.baseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    protected static final EntitySteps entitySteps = new EntitySteps();
    protected static final List<Integer> createdIds = Collections.synchronizedList(new ArrayList<>());

    @AfterAll
    public static void cleanUp() {
        for (Integer id : createdIds) {
            try {
                entitySteps.deleteEntityBeforeTests(requestSpecification, config.deleteUrl(), id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
