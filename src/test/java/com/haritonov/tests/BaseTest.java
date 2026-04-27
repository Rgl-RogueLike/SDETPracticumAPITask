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

/**
 * Абстрактный базовый класс для всех API тестов.
 * <p>
 * Содержит общие настройки для тестирования:
 *  Конфигурацию окружения и эндпоинтов ({@link Configuration}).
 *  Спецификацию HTTP запросов RestAssured ({@link RequestSpecification}).
 *  Экземпляр класса шагов ({@link EntitySteps}).
 *  Логику очистки тестовых данных ({@code cleanUp()}).
 */
public abstract class BaseTest {

    /**
     * Экземпляр конфигурации, загруженный из {@code config.properties}.
     * Содержит базовый URL и пути к эндпоинтам.
     */
    protected static final Configuration config = ConfigFactory.create(Configuration.class, System.getenv());

    /**
     * Спецификация запросов RestAssured с предустановленными настройками.
     */
    protected static final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(config.baseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    /**
     * Экземпляр класса шагов для взаимодействия с API.
     * Используется во всех тестах для выполнения CRUD операций.
     */
    protected static final EntitySteps entitySteps = new EntitySteps();

    /**
     * Потокобезопасный список для хранения ID созданных сущностей.
     * Используется для последующей очистки данных в методе {@link #cleanUp()}.
     */
    protected static final List<Integer> createdIds = Collections.synchronizedList(new ArrayList<>());

    /**
     * Метод очистки тестовых данных, выполняемый после всех тестов.
     * <p>
     * Проходит по списку {@link #createdIds} и удаляет каждую сущность через API.
     * Если удаление падает с ошибкой, исключение логируется, но выполнение продолжается.
     * </p>
     */
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
