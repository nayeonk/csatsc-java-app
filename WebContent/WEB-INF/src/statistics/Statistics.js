import Chart from "chart.js";

export default class Statistics {
    constructor() {
        Statistics.createChartWithData();
        const exportButton = document.getElementById("export-to-csv");
        exportButton.addEventListener("click", () => {
            Statistics.exportStatistics()
        });
    }

    static createChartWithData() {
        const data = Statistics.generateKeysAndValuesFromMap();

        if (data.length === 0) {
            return;
        }

        let chartCanvas = document.getElementById("myChart");
        if (chartCanvas === null) {
            return;
        }

        const ctx = chartCanvas.getContext("2d");
        new Chart(ctx, {
            // The type of chart we want to create
            type: "bar",

            // The data for our dataset
            data: {
                labels: data[0],
                datasets: [{
                    label: "Summer Camp Data",
                    backgroundColor: "rgb(255, 99, 132)",
                    borderColor: "rgb(255, 99, 132)",
                    data: data[1]
                }]
            },

            // Configuration options go here
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            min: 0
                        }
                    }]
                }
            }
        });
    }

    static exportStatistics() {
        const data = Statistics.generateKeysAndValuesFromMap();
        Statistics.exportToCsv("statistics.csv", data);
    }

    static exportToCsv = (filename, rows) => {
        let processRow = (row) => {
            let finalVal = "";
            for (let j = 0; j < row.length; j++) {
                let innerValue = row[j] === null ? "" : row[j].toString();
                if (row[j] instanceof Date) {
                    innerValue = row[j].toLocaleString();
                }
                let result = innerValue.replace(/"/g, '""');
                if (result.search(/("|,|\n)/g) >= 0) {
                    result = '"' + result + '"';
                }
                if (j > 0) {
                    finalVal += ",";
                }
                finalVal += result;
            }
            return finalVal + "\n";
        };

        let csvFile = "";
        for (let i = 0; i < rows.length; i++) {
            csvFile += processRow(rows[i]);
        }

        let blob = new Blob([csvFile], {type: "text/csv;charset=utf-8;"});
        if (navigator.msSaveBlob) { // IE 10+
            navigator.msSaveBlob(blob, filename);
        } else {
            let link = document.createElement("a");
            if (link.download !== undefined) { // feature detection
                // Browsers that support HTML5 download attribute
                let url = URL.createObjectURL(blob);
                link.setAttribute("href", url);
                link.setAttribute("download", filename);
                link.style.visibility = "hidden";
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    };

    static generateKeysAndValuesFromMap() {

        let keys = [];
        let values = [];
        let data = [];

        let table = document.getElementById("data-table");
        if (table != null) {
            for (let i = 0, row; row = table.rows[i]; i++) {
                for (let j = 0, col; col = row.cells[j]; j++) {
                    if (col.className === "data-key") {
                        keys.push(col.innerText);
                    } else if (col.className === "data-value") {
                        values.push(col.innerText);
                    }
                }
            }
        }

        data.push(keys);
        data.push(values);
        return data;
    }
}