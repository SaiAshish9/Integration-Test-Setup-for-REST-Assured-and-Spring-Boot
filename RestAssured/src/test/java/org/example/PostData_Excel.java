package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.library.DataHandler;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;

public class PostData_Excel {

    DataHandler db = new DataHandler();

    @Test()
    public void postData() throws IOException {
        String dataPath = System.getProperty("user.dir");
        dataPath += "/src/main/resources/report.xlsx";

        Object nameObj = db.ReadData_Excel(dataPath, 1, 1);
        String name = nameObj.toString();
        Object jobObj = db.ReadData_Excel(dataPath, 1, 2);
        String job = jobObj.toString();

        baseURI = "https://reqres.in/api";
        RequestSpecification req = RestAssured.given();

        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("job", job);

        req.body(body.toString());

        Response response = req.get("/users");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(1, response.jsonPath().getInt("page"));

        if (response.statusCode() == 201) {
            db.WriteData_Excel(dataPath, "PASSED", 1, 3, Cell.CELL_TYPE_STRING);
            db.WriteData_Excel(dataPath, response.statusCode(), 1, 4, Cell.CELL_TYPE_STRING);
            db.WriteData_Excel(dataPath, response.asString(), 1, 5, Cell.CELL_TYPE_STRING);
        } else {
            db.WriteData_Excel(dataPath, "FAILED", 1, 3, Cell.CELL_TYPE_STRING);
            db.WriteData_Excel(dataPath, response.statusCode(), 1, 4, Cell.CELL_TYPE_STRING);
            db.WriteData_Excel(dataPath, response.asString(), 1, 5, Cell.CELL_TYPE_STRING);
        }

    }

}
