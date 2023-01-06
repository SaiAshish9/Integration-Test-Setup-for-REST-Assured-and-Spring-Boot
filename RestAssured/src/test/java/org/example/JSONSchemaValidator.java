package org.example;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

public class JSONSchemaValidator {

    @Test
    public void testGet() {
        baseURI = "https://reqres.in/api";
        JsonSchemaValidator match = matchesJsonSchemaInClasspath("schema.json");
        given().
                get("/users?page=2").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("schema.json")).
//                move inside target/classes
        statusCode(200);
        RequestSpecification req = RestAssured.given();
        Response response = req.get("/users?page=1");
        MatcherAssert.assertThat(response.asString(), match);
    }
}
