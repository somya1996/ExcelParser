package com.example.ExcelParser.controller;

import com.example.ExcelParser.services.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342") // Change this to your frontend URL
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload-excel")
    public ResponseEntity<byte[]> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Convert Excel to JSON
            Map<String, List<Map<String, Object>>> jsonData = excelService.convertExcelToJson(file);
            // Convert the JSON data to a string
            String jsonString = new ObjectMapper().writeValueAsString(jsonData);
            // Prepare the response as a downloadable JSON file
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=data.json");
            headers.add("Content-Type", "application/json"); // Set Content-Type
            return new ResponseEntity<>(jsonString.getBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            // Log the error (consider using a logging framework)
            System.err.println("Error processing Excel file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error processing Excel file: " + e.getMessage().getBytes()).getBytes());
        }
    }
    /*public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, List<Map<String, Object>>> jsonData = excelService.convertExcelToJson(file);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }*/
// some changess
    @PostMapping("/upload-json")
    public ResponseEntity<byte[]> uploadJson(@RequestBody String json) {
        try {
            byte[] excelFile = excelService.convertJsonToExcel(json);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=output.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Set Content-Type
            return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Log the error (consider using a logging framework)
            System.err.println("Error processing JSON data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error processing JSON data: " + e.getMessage().getBytes()).getBytes());
        }
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody String json) throws IOException {
        byte[] excelBytes = excelService.convertJsonToExcel(json);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelBytes);
    }
}