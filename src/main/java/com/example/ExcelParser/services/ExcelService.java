package com.example.ExcelParser.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelService {

    public Map<String, List<Map<String, Object>>> convertExcelToJson(MultipartFile file) throws IOException {
        Map<String, List<Map<String, Object>>> jsonData = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<Map<String, Object>> sheetData = new ArrayList<>();
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();

            // Read headers
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim()); // Trim to avoid leading/trailing spaces
            }

            // Read data rows
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                Map<String, Object> rowData = new HashMap<>();

                // Ensure that we are not accessing cells out of bounds
                for (int k = 0; k < headers.size(); k++) {
                    Cell cell = row.getCell(k);
                    if (cell != null) {
                        rowData.put(headers.get(k), getCellValue(cell)); // Use helper method to get cell value
                    } else {
                        rowData.put(headers.get(k), null); // Handle null cells
                    }
                }
                sheetData.add(rowData);
            }
            jsonData.put(sheet.getSheetName(), sheetData);
        }
        workbook.close();
        return jsonData;
    }

    public byte[] convertJsonToExcel(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> data = objectMapper.readValue(json, List.class);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        Row headerRow = sheet.createRow(0);
        Set<String> headers = data.get(0).keySet();
        int headerIndex = 0;

        // Create header row
        for (String header : headers) {
            Cell cell = headerRow.createCell(headerIndex++);
            cell.setCellValue(header);
        }

        // Create data rows
        int rowIndex = 1;
        for (Map<String, Object> rowData : data) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (String header : headers) {
                Cell cell = row.createCell(cellIndex++);
                Object value = rowData.get(header);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }
        // Write to byte array output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    // Helper method to get cell value as a string
    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return "";
        }
    }
}