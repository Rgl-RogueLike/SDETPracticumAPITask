package com.haritonov.steps;

import com.github.javafaker.Faker;
import com.haritonov.dto.AdditionRequest;
import com.haritonov.dto.EntityRequest;
import com.haritonov.dto.EntityResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EntitySteps {

    private final Faker faker = new Faker();

    public String generateRandomTitle() {
        return faker.book().title();
    }

    public Boolean generateRandomVerified() {
        return faker.random().nextBoolean();
    }

    private List<Integer> generateRandomImportantNumbers(int lowerSize, int upperSize,
                                                         int lowerNumber, int upperNumber) {

        int size = faker.number().numberBetween(lowerSize, upperSize);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(faker.number().numberBetween(lowerNumber, upperNumber));
        }
        return numbers;
    }

    public Integer createEntity(RequestSpecification specificationRequest, String createUrl,
                                int lowerSize, int upperSize,
                                int lowerNumber, int upperNumber) {

        EntityRequest request = EntityRequest.builder()
                .title(generateRandomTitle())
                .verified(generateRandomVerified())
                .importantNumbers(generateRandomImportantNumbers(lowerSize, upperSize, lowerNumber, upperNumber))
                .addition(AdditionRequest.builder()
                        .additionalInfo(faker.lorem().sentence())
                        .additionalNumber(faker.number().numberBetween(lowerNumber, upperNumber))
                        .build())
                .build();

        String responseString = given()
                .spec(specificationRequest)
                .body(request)
                .when()
                .post(createUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().asString();

        return Integer.valueOf(responseString);
    }

    public EntityResponse getEntity(RequestSpecification requestSpecification, Integer id, String getUrl) {
        return given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .get(getUrl + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(EntityResponse.class);

    }
}
