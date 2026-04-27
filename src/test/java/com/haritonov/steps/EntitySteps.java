package com.haritonov.steps;

import com.github.javafaker.Faker;
import com.haritonov.dto.AdditionRequest;
import com.haritonov.dto.EntityFilterResponse;
import com.haritonov.dto.EntityRequest;
import com.haritonov.dto.EntityResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
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

    @Step("Create entity via API")
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

    @Step("Get entity by ID: {id}")
    public EntityResponse getEntity(RequestSpecification requestSpecification, String getUrl, Integer id) {
        return given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .get(getUrl + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(EntityResponse.class);

    }

    @Step("Get all entities")
    public EntityFilterResponse getAllEntities(RequestSpecification requestSpecification, String getAllUrl) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(getAllUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(EntityFilterResponse.class);
    }

    @Step("Update entity ID: {id}")
    public void updateEntity(RequestSpecification requestSpecification, String patchUrl,
                             Integer id, Boolean newVerified,
                             int lowerSize, int upperSize,
                             int lowerNumber, int upperNumber) {

        EntityRequest request = EntityRequest.builder()
                .title(generateRandomTitle() + " Updated")
                .verified(newVerified)
                .importantNumbers(generateRandomImportantNumbers(lowerSize, upperSize,
                        upperNumber + lowerNumber,
                        upperNumber + upperNumber))
                .addition(AdditionRequest.builder()
                        .additionalInfo(faker.lorem().sentence() + " Updated")
                        .additionalNumber(faker.number().numberBetween(upperNumber + lowerNumber,
                                upperNumber + upperNumber))
                        .build())
                .build();

        given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .body(request)
                .when()
                .patch(patchUrl + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Step("Delete entity ID: {id}")
    public void deleteEntity(RequestSpecification requestSpecification, String deleteUrl, Integer id) {
        given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .delete(deleteUrl + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public Response tryGetDeletedEntity(RequestSpecification requestSpecification, String getUrl, Integer id) {
        return given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .get(getUrl + "{id}");
    }

    public void deleteEntityBeforeTests(RequestSpecification requestSpecification, String deleteUrl, Integer id) {
        given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .delete(deleteUrl + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
