import "core-js/stable";
import "whatwg-fetch";
import "regenerator-runtime/runtime";
import UniversalPickupCode from "./UniversalPickupCode";

document.addEventListener("DOMContentLoaded", () => {
    new UniversalPickupCode();
});