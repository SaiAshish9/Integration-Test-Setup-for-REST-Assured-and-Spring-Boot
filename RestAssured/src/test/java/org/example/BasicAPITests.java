package org.example;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Cell;
import org.example.library.DataHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BasicAPITests extends BTest {

    DataHandler db = new DataHandler();
    String dataPath = System.getProperty("user.dir") + "/src/main/resources/report.xlsx";

    @Test(enabled = false)
    public void getUsers() {
        baseURI = "https://reqres.in/api";
        Response resp = null;
        resp = given().
                get("/users?page=2");
        System.out.println(resp.asString());
        System.out.println(resp.statusCode());
        Extent.getTest().log(Status.INFO, String.valueOf(resp.statusCode()));
        Extent.getTest().pass("pass");
    }

    @Test(enabled = true, priority = 2)
    public void getSingleUser() {
        baseURI = "https://reqres.in/api";
        Response resp = null;
        resp = given().
                get("/users/2");
        System.out.println(resp.asString());
        System.out.println(resp.statusCode());
    }

    @Test(enabled = true, priority = 1)
    public void postUser() {
        baseURI = "https://reqres.in/api";
        Response resp = null;
        String bodyParams = "{\"name\":\"sai\"}";
        resp = given().
                body(bodyParams).
                post("/users");
        System.out.println(resp.asString());
        System.out.println(resp.statusCode());
    }

    @Test(enabled = true)
    public void getAuthenticatedUsers() throws IOException {
        baseURI = "https://reqres.in/api";
        RequestSpecification req = RestAssured.given();
        req.
                header("netflix_id", "32748732jfbhu82jsdfs").
                header("netflix_password", "jry8newkf84r4nfek").
                header("stream", "TV shows");
        req.auth().basic("username", "password");
        req.contentType("application/json");
        req.param("page", 2);
        Response response = req.get("/users");
        System.out.println(response.asString());
        System.out.println(response.statusCode());
        db.WriteData_Excel(dataPath,"PASSED", 1 , 3, Cell.CELL_TYPE_STRING );
        db.WriteData_Excel(dataPath,response.statusCode(), 1 , 4, Cell.CELL_TYPE_NUMERIC );
        db.WriteData_Excel(dataPath,response.asString(), 1 , 5, Cell.CELL_TYPE_STRING );
        //        MatcherAssert
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(2, response.jsonPath().getInt("page"));
        Reporter.log("testing...");
        int expected_res = 12;
        int actual_res = response.jsonPath().getInt("total");
        if (expected_res == actual_res) {
            Reporter.log("passed");
            Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
        } else {
            Reporter.log("failed");
            Assert.fail("failed");
            Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
        }
        int var = response.jsonPath().getInt("page");
        junit.framework.Assert.assertTrue("failed", (var == 2));
        ExtentReport.getTest().log(Status.INFO, String.valueOf(response.statusCode()));
        ExtentReport.getTest().pass("pass");
    }


}
