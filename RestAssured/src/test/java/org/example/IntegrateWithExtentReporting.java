package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.library.DataHandler;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class IntegrateWithExtentReporting extends BTest {

    DataHandler db = new DataHandler();
    String erPath = System.getProperty("user.dir") + "/test-output/ExtentReports/test.html";

    @Test
    public void getUsers_ExtentReporting() throws IOException {
        baseURI = "https://reqres.in/api";
        Response resp = null;
        resp = given().
                get("/users?page=2");
        System.out.println(resp.asString());
        System.out.println(resp.statusCode());
        RequestSpecification req = RestAssured.given();
        ExtentReport.getTest().log(Status.INFO, String.valueOf(resp.statusCode()));
        ExtentReport.getTest().pass("pass");
    }

}
