export default class Attendance {
    constructor() {
        // Get controls and inputs
        this.dateControl = document.getElementById("date");
        this.levelControl = document.getElementById("level");
        this.tableBody = document.querySelector("#attendance-students tbody");
        this.topicControl = document.getElementById("topic");

        // Set up event listeners
        this.dateControl.addEventListener("change", this._onDateChange);
        this.levelControl.addEventListener("change", this._onFilterChange);
        this.topicControl.addEventListener("change", this._onFilterChange);

        // State
        this.data = null;
        this.campMap = {};
    }

    /**
     * Handles Check-In State Toggles
     *
     * @param studentId
     * @param attendance Attendance object for the current student (if not checked-in yet, null)
     */

    static handleCheckInState = (studentId, attendance) => {
        const checkInButton = document.getElementById(`checkIn-${studentId}`)
        const checkInDescription = document.getElementById(`checkInDescription-${studentId}`)

        if (attendance && attendance.checkedInBy && attendance.checkedOutBy) {
            // If student has been checked in and checked out (disable)
            checkInButton.textContent = "Undo"
            checkInDescription.textContent = ` ${attendance.checkedInTime.time.hour}:${attendance.checkedInTime.time.minute}`
            checkInButton.classList.remove("btn-success")
            checkInButton.classList.add("btn-warning")

            checkInButton.disabled = true

        } else if (attendance && attendance.checkedInBy && !attendance.checkedOutBy) {
            // If student has been checked in but not checked out (can undo check in)
            checkInButton.textContent = "Undo"
            checkInDescription.textContent = ` ${attendance.checkedInTime.time.hour}:${attendance.checkedInTime.time.minute}`
            checkInButton.classList.remove("btn-success")
            checkInButton.classList.add("btn-warning")
            checkInButton.disabled = false

        } else {
            // If student has not been checked in
            checkInButton.textContent = "Check In"
            checkInDescription.textContent = ``
            checkInButton.classList.remove("btn-warning")
            checkInButton.classList.add("btn-success")
            checkInButton.disabled = false

        }
    }

    /**
     * Handles Check-Out State Toggles
     *
     * @param studentId
     * @param attendance Attendance object for the current student (if not checked-in yet, null)
     */

    static handleCheckOutState = (studentId, attendance) => {
        const checkOutButton = document.getElementById(`checkout-${studentId}`)
        const checkOutUseParentNameCheckboxWrapper = document.getElementById(`checkoutCheckboxWrapper-${studentId}`)
        const checkOutDescription = document.getElementById(`checkOutDescription-${studentId}`)
        const checkOutUseParentNameCheckbox = document.getElementById(`useParentNameAsCode-${studentId}`)
        const checkOutCodeText = document.getElementById(`checkOutCode-${studentId}`)


        if (attendance && attendance.checkedInBy && attendance.checkedOutBy) {
            // If student has been checked in and checked out (can undo)

            checkOutButton.textContent = "Undo"
            checkOutButton.classList.add("btn-warning")
            checkOutUseParentNameCheckboxWrapper.classList.add("hidden")
            checkOutCodeText.classList.add("hidden")
            checkOutButton.disabled = false;

            checkOutDescription.textContent = ` ${attendance.checkedOutTime.time.hour}:${attendance.checkedOutTime.time.minute}`

        } else if (attendance && attendance.checkedInBy && !attendance.checkedOutBy) {
            // If student has been checked in (and admin wants to check out)
            checkOutButton.textContent = "Check Out"
            checkOutButton.classList.add("btn-success")
            checkOutButton.classList.remove("btn-warning")
            checkOutUseParentNameCheckboxWrapper.classList.remove("hidden")
            checkOutCodeText.classList.remove("hidden")
            checkOutDescription.textContent = ``

            checkOutUseParentNameCheckbox.disabled = false
            checkOutButton.disabled = false;
            checkOutCodeText.disabled = false;
        } else {
            // Student has not checked in (disable)
            checkOutButton.textContent = "Check Out"
            checkOutButton.classList.add("btn-success")
            checkOutButton.classList.remove("btn-warning")
            checkOutUseParentNameCheckboxWrapper.classList.remove("hidden")
            checkOutCodeText.classList.remove("hidden")
            checkOutDescription.textContent = ``

            checkOutUseParentNameCheckbox.disabled = true;
            checkOutButton.disabled = true;
            checkOutCodeText.disabled = true;
        }
    }

    /**
     * Generates the check back in button for a given row
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param parentNode {HTMLElement} parent node that will hold this
     * @returns {HTMLButtonElement}
     */
    static
    generateCheckBackInButton = (campId, studentId, date, parentNode) => {
        const checkBackIn = document.createElement("button");
        checkBackIn.className = "btn btn-block btn-danger";
        checkBackIn.innerText = "Check back in";
        checkBackIn.onclick = () => {
            Attendance.onCheckBackIn(campId, studentId, date, parentNode);
        };
        return checkBackIn;
    };

    /**
     * Generates the input field and button for the the student's check out code
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param disabled {boolean}
     * @param parentNode {HTMLElement} parent node that will hold this
     * @returns {HTMLDivElement}
     */
    static
    generateCheckOutInput = (campId, studentId, date, disabled, parentNode) => {
        const wrapper = document.createElement("div");
        const checkOutInput = document.createElement("input");
        checkOutInput.id = `checkout-${studentId}`;
        checkOutInput.class = "form-control";
        checkOutInput.disabled = disabled;
        wrapper.appendChild(checkOutInput);

        const button = document.createElement("button");
        button.className = "btn btn-success";
        button.innerText = "✔";
        button.onclick = () => {
            Attendance.onPickup(campId, studentId, date, checkOutInput.value, parentNode);
        };
        wrapper.appendChild(button);

        wrapper.className = "checkout";
        return wrapper;
    };

    /**
     * Makes 2 requests to the backend to check back in a checked out student.
     *      1. Resets the attendance status (by deleting the entry)
     *      2. Checks the student in
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param parentNode {HTMLElement}
     * @returns {Promise<void>}
     */
    static
    onCheckBackIn = async (campId, studentId, date, parentNode) => {
        const url = "/SummerCamp/api/attendance";
        const body = JSON.stringify({
            campId,
            studentId,
            date
        });

        // Delete / reset the attendance status
        const deleteResponse = await fetch(url, {
            body,
            method: "DELETE"
        });
        if (!deleteResponse.ok) {
            alert("Something went wrong");
            return;
        }

        // Check student in
        const checkInResponse = await fetch(url, {
            body,
            method: "POST"
        });
        if (checkInResponse.ok) {
            const relatedCheckin = document.getElementById(`checkin-${studentId}`);
            relatedCheckin.disabled = false;

            parentNode.innerHTML = "";
            parentNode.appendChild(
                Attendance.generateCheckOutInput(campId, studentId, date, false, parentNode)
            );
        }
    };

    /**
     * Makes a request to the backend to update the student's attendance
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param nodeReference {HTMLButtonElement}
     * @param item {Object}
     * @returns {Promise<void>}
     */
    static
    onCheckIn = async (campId, studentId, date, nodeReference, item) => {
        const url = "/SummerCamp/api/attendance";
        const isCheckIn = true;
        let requestInit = {
            body: JSON.stringify({
                campId,
                studentId,
                date,
                isCheckIn
            })
        };

        console.log(item.attendance)

        if (!item.attendance || (item.attendance && !item.attendance.checkedInBy)) {
            // If need to check in
            requestInit.method = "POST";
        } else {
            // If undo check in

            if (confirm("Unchecking this box does NOT serve as checking a student out. It is for in case you accidentally checked in the wrong student. If you are sure of this action, you may continue, otherwise hit cancel and use a check out code to the right.")) {
                requestInit.method = "DELETE";
            } else {
                // Undo the check action
                // ^ - Changed to button
                // nodeReference.checked = !nodeReference.checked;
                return;
            }
        }

        const response = await fetch(url, requestInit);
        if (response.ok) {
            const responseText = await response.text();

            // If checking-in
            if (responseText) {
                const newStudentAttendance = JSON.parse(responseText);
                item.attendance = newStudentAttendance
            }
        } else {
            if (response.status === 403) {
                alert("You do not have permission to do that");
            } else {
                alert("An unknown error occurred.");
            }

            // Undo the check action since it was not successful on the backend
        }
    };

    /**
     * Makes a request to the backend to check out the student's attendance
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param item {Object}
     * @returns {Promise<void>}
     */

    static
    onCheckOut = async (campId, studentId, date, item) => {
        const url = "/SummerCamp/api/attendance";
        const isCheckIn = false;

        if (item.attendance && item.attendance.checkedInBy && item.attendance.checkedOutBy) {
            // Student has been checked out (and admin wants to undo)

            const response = await fetch(url, {
                method: "DELETE",
                body: JSON.stringify({
                    campId,
                    studentId,
                    date,
                    isCheckIn
                })
            });

            if (response.ok) {
                console.log("OK")

                const responseText = await response.text();

                console.log(responseText)

                // Checkout
                if (responseText) {
                    const newStudentAttendance = JSON.parse(responseText);
                    item.attendance = newStudentAttendance

                    console.log(newStudentAttendance)
                }
            }

        } else if (item.attendance && item.attendance.checkedInBy && !item.attendance.checkedOutBy) {
            // If student has been checked in (and admin wants to check out)

            const pickupCode = document.getElementById(`checkOutCode-${studentId}`).value;

            const checkOutUseParentNameCheckbox = document.getElementById(`useParentNameAsCode-${studentId}`);
            const useParentNameAsCode = checkOutUseParentNameCheckbox.checked === true;

            const response = await fetch(url, {
                method: "PUT",
                body: JSON.stringify({
                    campId,
                    studentId,
                    date,
                    pickupCode,
                    useParentNameAsCode
                })
            });

            if (response.ok) {
                // Code was correct
                const responseText = await response.text();

                // Checkout
                if (responseText) {
                    const newStudentAttendance = JSON.parse(responseText);
                    item.attendance = newStudentAttendance

                    console.log(newStudentAttendance)
                }
            }
        }
    }

    /**
     * Makes a request to the backend to validate the checkout code.
     * If the code is valid, the text field changes to a checked box.
     * else the input is marked as error
     *
     * @param campId {number}
     * @param studentId {number}
     * @param date {string}
     * @param pickupCode {string}
     * @param containerNode {HTMLInputElement}
     * @returns {Promise<void>}
     */
    static
    onPickup = async (campId, studentId, date, pickupCode, containerNode) => {
        const url = "/SummerCamp/api/attendance";

        const response = await fetch(url, {
            method: "PUT",
            body: JSON.stringify({
                campId,
                studentId,
                date,
                pickupCode
            })
        });

        if (response.ok) {
            // Replace this HTMLElement with the check back in button
            containerNode.innerHTML = "";
            containerNode.appendChild(
                Attendance.generateCheckBackInButton(campId, studentId, date, containerNode)
            );

            // Disable the checkin checkbox
            const relatedCheckin = document.getElementById(`checkin-${studentId}`);
            relatedCheckin.disabled = true;
        } else {
            alert("Incorrect code");
        }
    };

    /**
     * Populates dropdown with <option> tags. Provides a default of "Any"
     * with value -1 or "No results" if the provided list is empty.
     *
     * @param listOptions used for value and innerText of each option
     * @param htmlControl HTML Element for <option> tags to be added to
     * @private
     */
    static
    populateDropdown = (listOptions, htmlControl) => {
        htmlControl.innerHTML = "";
        if (listOptions.length > 0) {
            const anyOption = document.createElement("option");
            anyOption.value = "-1";
            anyOption.innerText = "Any";
            htmlControl.appendChild(anyOption);
            listOptions.forEach(item => {
                const option = document.createElement("option");
                option.value = item;
                option.innerText = item;
                htmlControl.appendChild(option);
            });
        } else {
            const option = document.createElement("option");
            option.value = "-1";
            option.innerText = "No results";
            htmlControl.appendChild(option);
        }
    };

    initialize = async () => {
        // Set initial date as today with format YYYY-MM-DD
        const today = new Date();
        const year = today.getFullYear();
        const month = (today.getMonth() + 1).toString().padStart(2, "0");
        const date = today.getDate().toString().padStart(2, "0");
        this.dateControl.value = `${year}-${month}-${date}`;
        this.dateControl.dispatchEvent(new Event("change"));
    };

    /**
     * Makes network request to grab data based on current date.
     * Generates campMap
     *
     * @returns {Promise<void>}
     * @private
     */
    _fetchData = async () => {
        const date = this.dateControl.value;
        let url = `/SummerCamp/api/attendance?date=${date}`;

        this.data = await (await fetch(url)).json();
        this.data.campsOffered.map(camp => {
            this.campMap[camp.campOfferedId] = camp;
        });
    };

    /**
     * Sets topic and level to -1 to reset their filter
     * Fetches new data for the new date.
     *
     * @returns {Promise<void>}
     * @private
     */
    _onDateChange = async () => {
        this.levelControl.value = "-1";
        this.topicControl.value = "-1";
        await this._fetchData();
        this._populateDom();
    };

    /**
     * Updates student list based on applied filter
     *
     * @private
     */
    _onFilterChange = () => {
        this._populateStudents(
            this.data.studentAttendance
                .filter(item => {
                    const camp = this.campMap[item.campId];
                    if (this.topicControl.value !== "-1") {
                        return this.topicControl.value === camp.topic;
                    }
                    return true;
                })
                .filter(item => {
                    const camp = this.campMap[item.campId];
                    if (this.levelControl.value !== "-1") {
                        return this.levelControl.value === camp.level;
                    }
                    return true;
                })
        );
    };

    /**
     * Handles the initial render of all content
     *
     * @private
     */
    _populateDom = () => {
        this._populateStudents(this.data.studentAttendance);
        const topicSet = new Set();
        const topics = [];
        const levelSet = new Set();
        const levels = [];
        this.data.campsOffered.forEach(item => {
            if (!topicSet.has(item.topic)) {
                topics.push(item.topic);
            }
            topicSet.add(item.topic);

            if (!levelSet.has(item.level)) {
                levels.push(item.level);
            }
            levelSet.add(item.level);
        });
        Attendance.populateDropdown(topics, this.topicControl);
        Attendance.populateDropdown(levels, this.levelControl);
    };

    /**
     * Populates the table with list of students with the attendance record
     *
     * @param studentAttendance list of students with attendance data
     * @private
     */
    _populateStudents = (studentAttendance) => {
        this.tableBody.innerHTML = "";

        studentAttendance.forEach(item => {
            console.log(item)

            // item.attendance is important so not extracted
            // const {campId, student, attendance} = item;
            const {campId, student} = item;


            const studentRow = document.createElement("tr");

            // Col 1 - First name
            const firstName = document.createElement("td");
            firstName.innerText = student.firstName;
            studentRow.appendChild(firstName);

            // Col 2 - Last name
            const lastName = document.createElement("td");
            lastName.innerText = student.lastName;
            studentRow.appendChild(lastName);

            // Col 3 - Camp Details
            const campDetails = document.createElement("td");

            const campTopic = document.createElement("strong");
            campTopic.innerText = `${this.campMap[campId].topic} ${this.campMap[campId].level}`

            const campDates = document.createElement("div");
            campDates.innerText = `${this.campMap[campId].dateStart} - ${this.campMap[campId].dateEnd}`

            campDetails.appendChild(campTopic)
            campDetails.appendChild(campDates)

            studentRow.appendChild(campDetails);

            // Col 4 - Checkbox for checkin
            const checkIn = document.createElement("td");
            const checkInButton = document.createElement("button");
            const checkInDescription = document.createElement("span");

            checkInButton.classList.add("btn")
            checkInButton.id = `checkIn-${student.studentID}`;
            checkInDescription.id = `checkInDescription-${student.studentID}`;

            checkIn.appendChild(checkInButton);
            checkIn.appendChild(checkInDescription);

            studentRow.appendChild(checkIn);

            // Col 5 - Text input for checkout or the check back in button
            const checkOut = document.createElement("td");

            const checkOutButton = document.createElement("button")
            const checkOutDescription = document.createElement("span");

            // Use Parent Name Checkbox
            const checkOutUseParentNameCheckboxWrapper = document.createElement("div")
            const checkOutUseParentNameCheckbox = document.createElement("input");
            const checkOutUseParentNameCheckboxLabel = document.createElement("label");

            // Code Text Box
            const checkOutTextBoxWrapper = document.createElement("div")
            const checkOutCodeText = document.createElement("input");

            checkOutDescription.id = `checkOutDescription-${student.studentID}`;

            // Format button
            checkOutButton.id = `checkout-${student.studentID}`
            checkOutButton.classList.add("btn")

            // Format Parent Name Checkbox
            checkOutUseParentNameCheckboxWrapper.id = `checkoutCheckboxWrapper-${student.studentID}`
            checkOutUseParentNameCheckboxWrapper.classList.add("form-check")
            checkOutUseParentNameCheckboxLabel.id = `checkoutCheckboxLabel-${student.studentID}`
            checkOutUseParentNameCheckbox.classList.add("form-check-input")
            checkOutUseParentNameCheckboxLabel.classList.add("form-check-label")

            checkOutUseParentNameCheckbox.id = `useParentNameAsCode-${student.studentID}`
            checkOutUseParentNameCheckbox.type = "checkbox"
            checkOutUseParentNameCheckboxLabel.htmlFor = `useParentNameAsCode-${student.studentID}`
            checkOutUseParentNameCheckboxLabel.textContent = " Use Parent's Name"

            checkOutUseParentNameCheckbox.onclick = function () {
                if (this.checked === true) {
                    checkOutCodeText.placeholder = "Parent's Name"
                } else {
                    checkOutCodeText.placeholder = "Pickup Code"
                }
            }

            // Format Code Text Box
            checkOutCodeText.id = `checkOutCode-${student.studentID}`
            checkOutCodeText.classList.add("form-control")
            checkOutCodeText.placeholder = "Pickup Code"

            checkOutUseParentNameCheckboxWrapper.appendChild(checkOutUseParentNameCheckbox)
            checkOutUseParentNameCheckboxWrapper.appendChild(checkOutUseParentNameCheckboxLabel)

            checkOutTextBoxWrapper.appendChild(checkOutCodeText);

            checkOut.appendChild(checkOutUseParentNameCheckboxWrapper);
            checkOut.appendChild(checkOutTextBoxWrapper);
            checkOut.appendChild(checkOutButton);
            checkOut.appendChild(checkOutDescription);

            studentRow.appendChild(checkOut);

            this.tableBody.appendChild(studentRow);

            // #!Important (leave at end)

            // Initial render
            Attendance.handleCheckInState(student.studentID, item.attendance)
            Attendance.handleCheckOutState(student.studentID, item.attendance)

            // Check in button click
            checkInButton.onclick = () => {

                // onCheckIn updates item.attendance
                Attendance.onCheckIn(campId, student.studentID, this.dateControl.value, checkInButton, item).then(() => {
                    Attendance.handleCheckInState(student.studentID, item.attendance)
                    Attendance.handleCheckOutState(student.studentID, item.attendance)
                });
            };

            checkOutButton.onclick = () => {
                Attendance.onCheckOut(campId, student.studentID, this.dateControl.value, item).then(() => {
                    Attendance.handleCheckOutState(student.studentID, item.attendance)
                    Attendance.handleCheckInState(student.studentID, item.attendance)
                })
            }
        });
    };
}