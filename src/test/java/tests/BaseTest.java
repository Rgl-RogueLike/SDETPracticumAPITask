package tests;

import config.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

public abstract class BaseTest {
    protected static final Configuration config = ConfigFactory.create(Configuration.class, System.getenv());
    protected static final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(config.baseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
}
