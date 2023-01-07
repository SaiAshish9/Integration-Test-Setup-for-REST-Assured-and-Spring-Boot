package org.example.library;

import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataHandler {

    public Object ReadData_Excel(String dataPath, int row, int col) throws IOException {
        File excelFile = new File(dataPath);
        FileInputStream file = new FileInputStream(excelFile);
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        Sheet sheet = workbook.getSheetAt(0);
        Row r1 = sheet.createRow(1);
        r1.createCell(1);
        r1.createCell(2);
        Object value = "";
        if (sheet.getRow(row).getCell(col).getCellType() == Cell.CELL_TYPE_NUMERIC) {
            value = sheet.getRow(row).getCell(col).getNumericCellValue();
            System.out.println(value);
        } else if (sheet.getRow(row).getCell(col).getCellType() == Cell.CELL_TYPE_STRING) {
            value = sheet.getRow(row).getCell(col).getStringCellValue();
            System.out.println(value);
        }
        file.close();
        return value;
    }

    public void WriteData_Excel(String dataPath, Object writethis, int row, int col, int type) throws IOException {
        File excelFile = new File(dataPath);
        FileInputStream file = new FileInputStream(excelFile);
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet();
        Sheet sheet = workbook.getSheetAt(0);
        Row r1 = sheet.createRow(1);
        r1.createCell(1);
        r1.createCell(2);
        sheet.getRow(row).createCell(col).setCellType(type);
        sheet.getRow(row).createCell(col).setCellValue(writethis.toString());
        file.close();
    }

}
