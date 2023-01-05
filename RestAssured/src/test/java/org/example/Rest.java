package org.example;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class Rest {

    @Test
    public void getTest() {
        baseURI = "https://reqres.in/api";
        given().
                get("/users?page=2").
                then().
                statusCode(200).
                body("data[1].id", equalTo(8)).
                body("data.first_name", hasItems("Michael", "Lindsay","Tobias")).
                log().all();
    }

    @Test
    public void testPost() {
        JSONObject request = new JSONObject();
        request.put("name", "Raghav");
        request.put("job", "Teacher");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";
        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                post("/users").
                then().
                statusCode(201)
                .log().all();
    }

    @Test
    public void testPut() {
        JSONObject request = new JSONObject();
        request.put("name", "Raghav");
        request.put("job", "Teacher");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";
        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                put("/users/2").
                then().
                statusCode(200)
                .log().all();
    }

    @Test
    public void testPatch() {
        JSONObject request = new JSONObject();
        request.put("name", "Raghav");
        request.put("job", "Teacher");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in";
        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                patch("/api/users/2").
                then().
                statusCode(200)
                .log().all();
    }

    @Test
    public void testDelete() {
        baseURI = "https://reqres.in";
        when().
                delete("/api/users/2").
                then().
                statusCode(204)
                .log().all();
    }

    @Test
    public void post() {
        JSONObject request = new JSONObject();
        request.put("name", "Raghav");
        request.put("job", "Teacher");
        baseURI = "http://localhost:3000/";
        given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/users")
                .then().statusCode(201);
    }

    @Test
    public void put() {
        JSONObject request = new JSONObject();
        request.put("firstName", "Albert");
        request.put("lastName", "Einstein");
        baseURI = "http://localhost:3000/";
        given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/users/5")
                .then().statusCode(200);

    }

    @Test
    public void patch() {
        JSONObject request = new JSONObject();
        request.put("lastName", "Parker");
        baseURI = "http://localhost:3000/";
        given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .patch("/users/5")
                .then().statusCode(200);

    }

    @Test
    public void delete() {
        baseURI = "http://localhost:3000/";
        when().delete("/users/4").then().statusCode(200);
    }

//    json-server --watch db.json

}

