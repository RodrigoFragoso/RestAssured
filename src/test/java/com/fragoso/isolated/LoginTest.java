package com.fragoso.isolated;
import com.fragoso.config.Configurations;
import com.fragoso.factory.LoginDataFactory;
import com.fragoso.pojo.Login;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Test;

public class LoginTest {
    private String token;
    @Test
    public void setUp(){
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();

        Login getToken = LoginDataFactory.identity();
        this.token = given()
                .contentType(ContentType.JSON)
                .body(getToken)
            .when()
                .post("/login")
            .then()
                .log()
                    .all()
                .assertThat()
                    .statusCode(200)
                    .body("data.message", equalToIgnoringCase("Login realizado com sucesso"))
                .extract()
                    .path("data.authorization");
    }
}
