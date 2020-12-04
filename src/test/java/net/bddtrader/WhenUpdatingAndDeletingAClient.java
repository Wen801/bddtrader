package net.bddtrader;

import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenUpdatingAndDeletingAClient {

    @Before
    public void set_base_url(){
        RestAssured.baseURI="http://localhost:9000/api";
    }
//
//    @Before
//    public void create_a_client(){
//        String firstName = "Jim" ;
//        String lastName="Halpert" ;
//        String email = "jim@halpert.com";
//
//        //Creating a client
//        Client existingClient = Client.withFirstName(firstName)
//                .andLastName(lastName).andEmail(email);
//
//
//    }

    @Test
    public void when_deleting_a_client(){
        String firstName = "Jim" ;
        String lastName="Halpert" ;
        String email = "jim@halpert.com";

        //Creating a client
        Client existingClient = Client.withFirstName(firstName)
                .andLastName(lastName).andEmail(email);

        String id= aClientExists(existingClient);
        System.out.println("id is "+id);

        //When I delete the client
   //     RestAssured.given().pathParam("id",id).delete("/client/{id}");
        RestAssured.given().delete("/client/{id}",id);

        //Then the client should no longer exist.
        RestAssured.given()
                .get("/client/{id}",id)
                .then()
                .statusCode(404);
    }

    @Test
    public void should_be_able_to_update_a_client(){
        String firstName = "Jim" ;
        String lastName="Halpert" ;
        String email = "jim@halpert.com";

        //Creating a client
        Client existingClient = Client.withFirstName(firstName)
                .andLastName(lastName).andEmail(email);

//        Client updatedClient = Client.withFirstName(firstName)
//                .andLastName(lastName).andEmail("jim123@halpert.com");

        //use map when you only have few fields need to be updated.
        Map<String, Object> updatedClient = new HashMap<>();
        updatedClient.put("email", "jim12345@halpert.com");


        String id= aClientExists(existingClient);
        System.out.println("id is "+id);

        RestAssured.given().contentType(ContentType.JSON)
                .and().body(updatedClient)
                .when().put("/client/{id}",id)
                .then().statusCode(200)
                .and().body("email", equalTo("jim12345@halpert.com"));
    }


    private String aClientExists(Client existingClient) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(existingClient)
                .when()
                .post("/client").jsonPath().getString("id");
    }

}
