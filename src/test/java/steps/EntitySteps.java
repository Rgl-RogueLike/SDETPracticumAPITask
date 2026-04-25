package steps;

import com.github.javafaker.Faker;
import config.Configuration;
import dto.AdditionRequest;
import dto.EntityRequest;
import io.restassured.http.ContentType;
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

    private List<Integer> generateRandomImportantNumbers() {
        int size = faker.number().numberBetween(1, 5);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(faker.number().numberBetween(1, 100));
        }
        return numbers;
    }

    public Integer createEntity(RequestSpecification specificationRequest, String title, Boolean verified, String createUrl) {
        EntityRequest request = EntityRequest.builder()
                .title(title)
                .verified(verified)
                .importantNumbers(generateRandomImportantNumbers())
                .addition(AdditionRequest.builder()
                        .additionalInfo(faker.lorem().sentence())
                        .additionalNumber(faker.number().numberBetween(1, 1000))
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
}
