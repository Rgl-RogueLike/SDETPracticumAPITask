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

/**
 * Класс-обертка для шагов взаимодействия с API.
 * Содержит методы для создания, получения, обновления и удаления сущностей.
 * Использует библиотеку Faker для генерации тестовых данных.
 */
public class EntitySteps {

    private final Faker faker = new Faker();

    /**
     * Генерирует случайный заголовок.
     * @return Строка с заголовком.
     */
    public String generateRandomTitle() {
        return faker.book().title();
    }

    /**
     * Генерирует случайное булево значение.
     * @return true или false.
     */
    public Boolean generateRandomVerified() {
        return faker.random().nextBoolean();
    }

    /**
     * Генерирует список случайных чисел заданной длины и диапазона.
     * @param lowerSize минимальное количество чисел.
     * @param upperSize максимальное количество чисел.
     * @param lowerNumber минимальное значение числа.
     * @param upperNumber максимальное значение числа.
     * @return Список целых чисел.
     */
    private List<Integer> generateRandomImportantNumbers(int lowerSize, int upperSize,
                                                         int lowerNumber, int upperNumber) {

        int size = faker.number().numberBetween(lowerSize, upperSize);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(faker.number().numberBetween(lowerNumber, upperNumber));
        }
        return numbers;
    }

    /**
     * Создает сущность через API POST /api/create.
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param createUrl URL эндпоинта создания.
     * @param lowerSize мин размер для генерации чисел.
     * @param upperSize макс размер для генерации чисел.
     * @param lowerNumber мин значение для генерации чисел.
     * @param upperNumber макс значение для генерации чисел.
     * @return ID созданной сущности.
     */
    @Step("Create entity via API")
    public Integer createEntity(RequestSpecification requestSpecification, String createUrl,
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
                .spec(requestSpecification)
                .body(request)
                .when()
                .post(createUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().asString();

        return Integer.valueOf(responseString);
    }

    /**
     * Получает сущность по ID через API GET /api/get/{id}.
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param getUrl базовый URL эндпоинта получения.
     * @param id ID сущности.
     * @return Объект EntityResponse, содержащий данные сущности.
     */
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

    /**
     * Получает список всех сущностей через API GET /api/getAll.
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param getAllUrl URL эндпоинта получения всех.
     * @return Объект EntityFilterResponse, содержащий список сущностей.
     */
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

    /**
     * Обновляет сущность по ID через API PATCH /api/patch/{id}.
     * Чтобы title и additionalInfo случайно не совпали с предыдущим значением в конец добавляется Updated.
     * Диапазон чисел также смещен (от макс + мин до макс + макс)
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param patchUrl базовый URL эндпоинта обновления.
     * @param id ID сущности.
     * @param newVerified новое значение статуса верификации.
     * @param lowerSize мин размер для генерации.
     * @param upperSize макс размер для генерации.
     * @param lowerNumber мин значение для генерации.
     * @param upperNumber макс значение для генерации.
     */
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

    /**
     * Удаляет сущность по ID через API DELETE /api/delete/{id}.
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param deleteUrl базовый URL эндпоинта удаления.
     * @param id ID сущности.
     */
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

    /**
     * Пытается получить удаленную сущность (для проверки, что она не существует).
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param id ID сущности.
     * @return Ответ сервера.
     */
    @Step("Try to get deleted entity with ID: {id}")
    public Response tryGetDeletedEntity(RequestSpecification requestSpecification, String getUrl, Integer id) {
        return given()
                .spec(requestSpecification)
                .pathParam("id", id)
                .when()
                .get(getUrl + "{id}");
    }

    /**
     * Удаляет сущность по ID через API DELETE /api/delete/{id}.
     * Используется после завершения всех тестов.
     * Не имеет аннотации @Step, чтобы не ломать Allure в @AfterAll.
     *
     * @param requestSpecification спецификация HTTP запроса.
     * @param deleteUrl базовый URL эндпоинта удаления.
     * @param id ID сущности.
     */
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
