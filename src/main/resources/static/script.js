async function uploadExcel() {
    const fileInput = document.getElementById("excelFile");
    const file = fileInput.files[0];

    if (!file) {
        alert("Please select a file first!");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
        const response = await fetch("http://localhost:8080/api/upload-excel", {
            method: "POST",
            body: formData,
        });

        if (!response.ok) {
            throw new Error("Network response was not ok");
        }

        const jsonData = await response.json(); // Assuming the response is JSON
        console.log("Upload successful:", jsonData);
        alert("File uploaded successfully!");

        // Display the JSON output in a preformatted text area
        document.getElementById("jsonOutput").textContent = JSON.stringify(jsonData, null, 2);
    } catch (error) {
        console.error("Error uploading Excel file:", error);
        alert("Error uploading file. Check console for details.");
    }
}

async function uploadJson() {
    const jsonInput = document.getElementById('jsonInput').value;

    // Validate JSON input
    try {
        JSON.parse(jsonInput); // Check if the input is valid JSON
    } catch (e) {
        console.error('Invalid JSON input:', e);
        alert('Please enter valid JSON.');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/upload-json', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonInput
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'output.xlsx'; // This is the filename for the downloaded file
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(url); // Free up memory
    } catch (error) {
        console.error('Error uploading JSON:', error);
        alert('Error uploading JSON: ' + error.message);
    }
}