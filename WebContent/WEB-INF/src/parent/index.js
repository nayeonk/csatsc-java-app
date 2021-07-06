import "core-js/stable";
import "whatwg-fetch";
import "regenerator-runtime/runtime";
import * as ParentControlPanel from "./ParentControlPanel";

document.addEventListener("DOMContentLoaded", () => {
    ParentControlPanel.initializeFormEventHandlers();
    ParentControlPanel.initializeGlobalEventHandlers();
});
