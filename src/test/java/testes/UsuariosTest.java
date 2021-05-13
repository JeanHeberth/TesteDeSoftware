package testes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

public class UsuariosTest {

    @BeforeClass
    public static void setup(){
        baseURI = "http://restapi.wcaquino.me";
        basePath = "/users";
    }

    @Test
    public void buscarUsuario(){
        given().
                when().
                get("/").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void adicionarUsuario(){

        HashMap<String, String> usuario = new HashMap<>();
        usuario.put("name", "Jean Heberth");
        usuario.put("age", "33");


        given().contentType(ContentType.JSON).
                body(usuario)
                .when()
                .post("/").
                then().
                statusCode(HttpStatus.SC_CREATED);



    }

    @Test
    public void naoDeveAdicionarUsuarioApenasComIdade(){
        given().
                contentType(ContentType.JSON).
                body("{ \"age\": 50}").
                when().
                post("/").
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Name é um atributo obrigatório"));
    }


    @Test
    public void alterarUsuario(){

        HashMap<String, String> usuarioPut = new HashMap<>();
        usuarioPut.put("name", "Testando");
        usuarioPut.put("age", "90");
        given().
                contentType(ContentType.JSON).
                body(usuarioPut).
                when().
                put("/1").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deveRemoverUsuario(){
        given().
                pathParam("entidade", "users")
                .pathParam("userId", "1")
                .when()
                .delete("/{entidade}/{UserId}").
                then().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
