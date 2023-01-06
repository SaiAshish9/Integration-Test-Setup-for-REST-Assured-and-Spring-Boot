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
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;

public class PostData_Excel {

    @Test()
    public void postData() throws IOException {
        String dataPath = System.getProperty("user.dir");
        dataPath += "/src/main/report.xlsx";

        File excelFile = new File(dataPath);
        FileInputStream file = new FileInputStream(excelFile);
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        Sheet sheet = workbook.getSheetAt(0);
        Row r1 = sheet.createRow(1);
        r1.createCell(1);
        r1.createCell(2);
        String name = sheet.getRow(1).getCell(1).getStringCellValue();
        String job = sheet.getRow(1).getCell(2).getStringCellValue();
        System.out.println("job : " + job + " " + name);

        baseURI = "https://reqres.in/api";
        RequestSpecification req = RestAssured.given();

        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("job", job);

        req.body(body.toString());

        Response response = req.get("/users");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(1, response.jsonPath().getInt("page"));

        if(response.statusCode() == 201){
            sheet.getRow(1).createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            sheet.getRow(1).createCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
            sheet.getRow(1).createCell(5).setCellType(Cell.CELL_TYPE_STRING);
            sheet.getRow(1).getCell(3).setCellValue(response.asString());
            sheet.getRow(1).getCell(4).setCellValue(response.statusCode());
            sheet.getRow(1).getCell(5).setCellValue("PASSED");
        }else {
            sheet.getRow(1).createCell(3).setCellType(Cell.CELL_TYPE_STRING);
            sheet.getRow(1).createCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
            sheet.getRow(1).createCell(5).setCellType(Cell.CELL_TYPE_STRING);
            sheet.getRow(1).getCell(3).setCellValue(response.asString());
            sheet.getRow(1).getCell(4).setCellValue(response.statusCode());
            sheet.getRow(1).getCell(5).setCellValue("FAILED");
        }

        FileOutputStream outputStream = new FileOutputStream(excelFile);
        workbook.write(outputStream);
        outputStream.close();
        file.close();

    }

}
