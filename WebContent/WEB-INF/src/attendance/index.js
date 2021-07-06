import "core-js/stable";
import "whatwg-fetch";
import "regenerator-runtime/runtime";
import Attendance from "./Attendance";

document.addEventListener("DOMContentLoaded", () => {
    const attendance = new Attendance();
    attendance.initialize().catch(e => alert(e));
});