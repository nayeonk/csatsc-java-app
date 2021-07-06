import Attendance from "../attendance/Attendance";

export default class AttendanceReport {
    constructor() {
        this.startDateControl = document.getElementById("start-date");
        this.endDateControl = document.getElementById("end-date");
        this.tableBody = document.querySelector("#attendance-table tbody");
        this.topicControl = document.getElementById("topic");
        this.levelControl = document.getElementById("level");
        this.buttonSearchRange = document.getElementById("button-search-range");

        this.buttonSearchRange.addEventListener("click", this._onSearchRangeClicked);
        this.levelControl.addEventListener("change", this._onFilterChange);
        this.topicControl.addEventListener("change", this._onFilterChange);
    }

    _onSearchRangeClicked = async () => {
        const startDate = this.startDateControl.value;
        const endDate = this.endDateControl.value;
        this.data = await this._getAttendanceReportData(startDate, endDate);

        this.levelControl.value = "-1";
        this.topicControl.value = "-1";

        this._populateTable(this.data);
        this._populateFilters(this.data);
    };

    _onFilterChange = () => {
        const filteredData =
            this.data
                .filter(item => {
                    const levelIsSelected = (this.levelControl.value !== "-1");
                    const levelsAreEqual = (this.levelControl.value === item.campLevelDescription);
                    const topicIsSelected = (this.topicControl.value !== "-1");
                    const topicsAreEqual = (this.topicControl.value === item.topic);

                    if (levelIsSelected && topicIsSelected) {
                        return levelsAreEqual && topicsAreEqual;
                    } else if (levelIsSelected) {
                        return levelsAreEqual;
                    } else if (topicIsSelected) {
                        return topicsAreEqual;
                    }

                    return true;
                });
        this._populateTable(filteredData);
    };

    _populateTable = (data) => {
        this.tableBody.innerHTML = "";

        data.forEach(item => {
            const tableRowElement = document.createElement("tr");
            this._populateRowWithItem(tableRowElement, item);
            this.tableBody.append(tableRowElement);
        });
    };

    _populateFilters = (data) => {
        const topicSet = new Set();
        const levelSet = new Set();

        data.forEach(item => {
            topicSet.add(item.topic);
            levelSet.add(item.campLevelDescription);
        });

        Attendance.populateDropdown(Array.from(topicSet), this.topicControl);
        Attendance.populateDropdown(Array.from(levelSet), this.levelControl);

        this.topicControl.disabled = false;
        this.levelControl.disabled = false;
    };

    _populateRowWithItem = (tableRowElement, item) => {
        const attendanceDate = this._generateDateFromObject(item.attendanceDate);
        const studentName = (item.studentFN || "") + " " + (item.studentLN || "");
        const UCodeUsed = (item.UCodeUsed != undefined && item.UCodeUsed) ? "Yes" : "No";

        const checkInByName = (item.checkInByFN || "") + " " + (item.checkInByLN || "");
        const checkInDate = this._generateDateFromObject(item.checkInTime.date);
        const checkInTime = this._generateTimeFromObject(item.checkInTime.time)

        const checkOutByName = (item.checkOutByFN || "") + " " + (item.checkOutByLN || "");
        const checkOutDate = this._generateDateFromObject(item.checkOutTime.date);
        const checkOutTime = this._generateTimeFromObject(item.checkOutTime.time);

        const cellValuesList = [
            attendanceDate,
            studentName,
            UCodeUsed,
            checkInByName,
            checkInDate,
            checkInTime,
            checkOutByName,
            checkOutDate,
            checkOutTime,
        ];

        cellValuesList.forEach((cellValue) => {
            this._addCellToRow(tableRowElement, cellValue);
        });
    };

    _generateDateFromObject = (dateObject) => {
        return dateObject.month + "/" + dateObject.day + "/" + dateObject.year;
    };

    _generateTimeFromObject = (timeObject) => {
        return timeObject.hour + ":" + timeObject.minute + ":" + timeObject.second;
    };

    _addCellToRow = (tableRowElement, item) => {
        const tableCell = document.createElement("td");
        tableCell.innerHTML = item;
        tableRowElement.append(tableCell);
    };

    _getAttendanceReportData = async (startDate, endDate) => {
        const url = "/SummerCamp/api/attendancereport?startDate=" + startDate + "&" + "endDate=" + endDate;
        const data = await (await fetch(url)).json();
        return data;
    }
}
