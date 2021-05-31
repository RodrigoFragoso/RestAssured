package com.fragoso.isolated;

import com.fragoso.config.Configurations;
import com.fragoso.factory.UsuarioDataFactory;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import com.fragoso.pojo.Usuario;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

public class UsuarioTest {

    Faker faker = new Faker(new Locale("pt-BR"));
    String name = faker.name().firstName();

    private String idUsuario;
    @Before
    public void setup() throws IOException{
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
        Usuario usuario = UsuarioDataFactory.criarUsuario(name,name+"@testando.com");

        this.idUsuario = given()
            .contentType(ContentType.JSON)
            .when()
                .body(usuario)
                .post("/usuarios")
            .then()
                .extract()
                    .path("_id");
    }

    @Test
    public void testListarUsuarioCadastrado() throws IOException{
        Usuario usuario = UsuarioDataFactory.criarUsuario(name,name+"@testando.com");
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/usuarios/"+idUsuario)
        .then()
            .statusCode(200)
            .assertThat()
                .body("nome", equalTo(usuario.getNome()))
                .body("email", equalTo(usuario.getEmail()))
                .body("administrador", equalTo("false"))
                .body("_id", equalTo(idUsuario));
    }
}
