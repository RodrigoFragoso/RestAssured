package com.fragoso.isolated;

import com.fragoso.config.Configurations;
import com.fragoso.factory.UsuarioDataFactory;
import com.fragoso.pojo.Usuario;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class UsuarioAdmTest {

    Faker faker = new Faker(new Locale("pt-BR"));
    String name = faker.name().firstName();

    private String idUsuario;
    @Before
    public void setUp() throws IOException {
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
        Usuario usuarioAdm = UsuarioDataFactory.criarUsuarioAdm(name, name+"@testando.com");

        this.idUsuario = given()
            .contentType(ContentType.JSON)
            .when()
                .body(usuarioAdm)
                .post("/usuarios")
            .then()
                .assertThat()
                    .statusCode(201)
                    .body("message", equalToIgnoringCase("Cadastro realizado com sucesso"))
                .extract()
                    .path("_id");
    }

    @Test
    public void testListarUsuarioCadastrado() throws IOException{
        Usuario usuario = UsuarioDataFactory.criarUsuarioAdm(name,name+"@testando.com");
        given()
            .contentType(ContentType.JSON)
            .when()
                .get("/usuarios/"+idUsuario)
            .then()
                .statusCode(200)
                .assertThat()
                    .body("nome", equalTo(usuario.getNome()))
                    .body("email", equalTo(usuario.getEmail()))
                    .body("administrador", equalTo("true"))
                    .body("_id", equalTo(idUsuario));
    }

    @Test
    public void testTentarCadastrarUsuarioAdmSemEmail() throws IOException {
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
        Usuario usuarioAdm = UsuarioDataFactory.criarUsuarioAdm(name, name);

        given()
            .contentType(ContentType.JSON)
                .body(usuarioAdm)
                .post("/usuarios")
            .then()
                .log()
                    .all()
                .assertThat()
                    .statusCode(400)
                    .body("email", equalToIgnoringCase("email deve ser um email válido"));
    }
}
