package net.bddtrader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenCreatingANewClient {

    @Before
    public void set_base_url(){
        RestAssured.baseURI="http://localhost:9000/api";
    }

    @Test
    public void create_new_client_with_client_class(){
        String firstName = "Jim" ;
        String lastName="Halpert" ;
        String email = "jim@halpert.com";

      //  use java object builder to create new clientl
        Client aNewClient = Client.withFirstName(firstName)
                        .andLastName(lastName).andEmail(email);


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(aNewClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id",not(equalTo(0)))
                .and().body("email", equalTo(email))
                .and().body("firstName", equalTo(firstName))
                .and().body("lastName", equalTo(lastName));
    }

    @Test
    public void create_new_client_with_json_data(){

            String newClient = "{\n" +
                "  \"email\": \"ww4@hotmail.com\",\n" +
                "  \"firstName\": \"Test4\",\n" +
                "  \"lastName\": \"TestLast4\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id",not(equalTo(0)))
                .and().body("email", equalTo("ww4@hotmail.com"))
                .and().body("firstName", equalTo("Test4"))
                .and().body("lastName", equalTo("TestLast4"));
    }

    @Test
    public void create_new_client_with_map(){

        String firstName = "Kevin" ;
        String lastName="Malone" ;
        String email = " kevin@malone.com";

        //use map to create data, only add fields that interested in .
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("email", email);
        clientData.put("firstName", firstName);
        clientData.put("lastName",lastName);


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(clientData)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id",not(equalTo(0)))
                .and().body("email", equalTo(email))
                .and().body("firstName", equalTo(firstName))
                .and().body("lastName", equalTo(lastName));
    }
}
