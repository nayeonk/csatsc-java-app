export default class UniversalPickupCode {
    constructor() {
        this.retrieveAllUniversalPickupCodes().catch(e => console.error(e));
        document.getElementById("button-add-upc")
            .addEventListener("click", this.onAddPressed);
    }

    sortListItems = (listItems) => {
        [].slice.call(listItems).sort((a, b) => {
            const textA = a.getAttribute('id').toLowerCase();
            const textB = b.getAttribute('id').toLowerCase();
            return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
        })
            .forEach((el) => {
                el.parentNode.appendChild(el)
            });
    };

    retrieveAllUniversalPickupCodes = async () => {
        const url = "/SummerCamp/api/universalpickupcode";
        const requestInit = {
            method: "GET"
        };

        const response = await fetch(url, requestInit);
        if (!response.ok) {
            if (response.status === 403) {
                alert("You do not have permission to do that");
            } else {
                alert("An unknown error occurred.");
            }
        }
        let data = await (await fetch(url)).json();
        for (let [key, value] of Object.entries(data)) {
            if (value === '') {
                continue;
            }
            this.createNewListElement(value);
        }
    };

    addCloseToListItem = (element) => {
        const span = document.createElement("SPAN");
        const txt = document.createTextNode("\u00D7");
        span.className = "close";
        span.appendChild(txt);
        element.appendChild(span);

        return span;
    };

    addFunctionToDeleteOnClick = (element) => {
        element.onclick = (event) => {
            let div = event.target.parentElement;
            this.deleteUniversalPickupCode(div.id).catch(e => console.error(e));
            div.style.display = "none";
        }
    };

    validateNewUPC = (inputValue) => {
        if (inputValue === null || inputValue === '') {
            return false;
        }
        return inputValue.length === 6;
    };

    // Create a new list item when clicking on the "Add" button
    onAddPressed = () => {
        const inputValue = document.getElementById("upc-text-input").value;
        if (!this.validateNewUPC(inputValue)) {
            alert("Invalid UPC! Must be exactly 6 characters long.");
            return;
        }

        this.createNewListElement(inputValue);
        this.createNewUniversalPickupCode(inputValue).catch(e => console.error(e));
    };

    // Assume that the input value is valid here
    createNewListElement = (inputValue) => {
        let li = document.createElement("li");
        li.id = inputValue;
        li.className = "list-group-item";

        let textNode = document.createTextNode(inputValue);
        li.appendChild(textNode);

        let upcList = document.getElementById("upc-list");
        let upcListItems = upcList.children;

        upcList.appendChild(li);
        document.getElementById("upc-text-input").value = "";

        let span = this.addCloseToListItem(li);
        this.addFunctionToDeleteOnClick(span);

        this.sortListItems(upcListItems);
    };

    deleteUniversalPickupCode = async (universalPickupCode) => {
        const url = "/SummerCamp/api/universalpickupcode?universalPickupCode=" + universalPickupCode;
        const requestInit = {
            method: "DELETE"
        };

        const response = await fetch(url, requestInit);
        if (!response.ok) {
            if (response.status === 403) {
                alert("You do not have permission to do that");
            } else {
                alert("An unknown error occurred.");
            }
        }
    };

    createNewUniversalPickupCode = async (universalPickupCode) => {
        const url = "/SummerCamp/api/universalpickupcode?universalPickupCode=" + universalPickupCode;
        const requestInit = {
            method: "POST",
            body: JSON.stringify({universalPickupCode})
        };

        const response = await fetch(url, requestInit);
        if (!response.ok) {
            if (response.status === 403) {
                alert("You do not have permission to do that");
            } else {
                alert("An unknown error occurred.");
            }
        }
    };
}