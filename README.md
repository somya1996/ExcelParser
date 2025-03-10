# Excel-JSON Converter

## Overview

The **Excel-JSON Converter** is a full-stack web application that allows users to convert Excel files to JSON format and vice versa. The application supports multiple sheets in an Excel file, encapsulating each sheet as a separate object in a single JSON output. Additionally, it generates a JSON schema based on the structure of the uploaded Excel file.

## Features

- **Convert Excel to JSON**: Upload an Excel file, and the application will convert it to a JSON format, including a schema representation of the data.
- **Convert JSON to Excel**: Upload a JSON file, and the application will convert it to a specified Excel format.
- **Multi-Sheet Support**: Handles multiple sheets in an Excel file, encapsulating each sheet as a separate object in the resulting JSON.
- **JSON Schema Generation**: Automatically generates a JSON schema based on the structure of the uploaded Excel file.

## Technologies Used

- **Frontend**: 
  - HTML
  - CSS
  - JavaScript
  - Libraries: 
    - [xlsx](https://github.com/SheetJS/sheetjs) for handling Excel files
    - [Axios](https://axios-http.com/) for making HTTP requests

- **Backend**: 
  - Java
  - Framework: Spring Boot
  - Libraries: 
    - [Apache POI](https://poi.apache.org/) for handling Excel files
    - [Jackson](https://github.com/FasterXML/jackson) for JSON processing

- **Database**: (Optional, if you want to store uploaded files or logs)
  - H2 Database (for development)
  - MySQL/PostgreSQL (for production)

## Installation

### Prerequisites

- Java 11 or higher
- Node.js and npm (for any additional frontend tooling)
- Maven (for backend)
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/excel-json-converter.git
   cd excel-json-converter/backend

2. Build the backend:
   
       mvn clean install
   
3.Run the Spring Boot application:  

    mvn spring-boot:run

### Frontend Setup
  Navigate to the frontend directory:
  
      cd ../frontend
      
Open the index.html file in your web browser. No additional setup is required for the frontend.  

### Usage
  - Convert Excel to JSON:
      Navigate to the "Excel to JSON" section.
      Upload your Excel file.
      Click on "Convert" to see the JSON output and schema.
  - Convert JSON to Excel:
      Navigate to the "JSON to Excel" section.
      Paste your JSON data or upload a JSON file.
      Click on "Convert" to download the Excel file.

### API Endpoints
    POST /api/upload-excel: Upload an Excel file to convert it to JSON.
    POST /api/upload-json: Upload a JSON file to convert it to Excel.

### Contributing
  Contributions are welcome! Please follow these steps:
