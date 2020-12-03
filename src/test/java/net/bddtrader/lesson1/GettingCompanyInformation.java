package net.bddtrader.lesson1;

import io.restassured.RestAssured;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GettingCompanyInformation {

    @Test
    public void should_return_name_and_exchange() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api/";
        RestAssured.get("stock/{symbol}/company", "aapl")
                .then().body("companyName", equalTo("Apple, Inc."))
                .and().body("exchange", equalTo("NASDAQ"));
    }

    @Test
    public void should_return_name_and_sector_from_local() {
        RestAssured.baseURI = "http://localhost:9000/api/";
        RestAssured.given().pathParam("symbol", "aapl")
                .get("stock/{symbol}/company")
                .then().body("companyName", equalTo("Apple, Inc."))
                .and().body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_news_for_requested_company() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api/";
        RestAssured.given().queryParam("symbols", "fb")
                .when().get("/news")
                .then().statusCode(200).and()
                .body("related", everyItem(containsString("FB")));

    }

    @Test
    public void should_return_news_for_apple(){
        RestAssured.baseURI="https://bddtrader.herokuapp.com/api/";
        RestAssured.given().queryParam("symbols","AAPL")
                .when().get("/news")
                .then().body("related",everyItem(containsString("AAPL")));

    }


}
