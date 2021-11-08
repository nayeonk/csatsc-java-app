// CHECKBOXES FOR EACH TOPIC
main();

// Set up onclicks
function main() {
	handleClassCheckboxes();
	handleCollapseExpandTopics();
	handleFilterEntryCheckboxes();
	handleFilterSegmentCheckboxes();
}

// WHERE: In each topic (Python, Java, etc.)
// WHEN: User clicks on an entry of each topic (e.g. a class like Python Level 1)
// WHAT: Toggles the checkbox on and off. Moves the entry into selected box if it's not there yet and removes it otherwise.
function handleClassCheckboxes() {
	var checkboxes = document.getElementsByClassName("class-entry");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].onclick = function(e) {

			// If applied, user is not allowed to do anything
			if (!this.classList.contains("applied")) {

				// Update the hidden value
				let hiddenInputValue = this.children[0].children[0].value;
				if (hiddenInputValue[0] == '0') {
					hiddenInputValue = "1" + hiddenInputValue.substr(1);
				} else if (hiddenInputValue[0] == '1') {
					hiddenInputValue = "0" + hiddenInputValue.substr(1);
				}
				this.children[0].children[0].value = hiddenInputValue;
				this.children[0].children[1].classList.toggle("active-checkbox");

				const isMobile = this.classList.contains("mobile");
				const clicked_id = this.dataset.campID;

				// If it's not checked, move it to the selected_box
				if (!this.classList.contains("class-selected")) {
					let mobile_contents = null;
					let desktop_contents = null;

					if (!isMobile) {
						desktop_contents = this.cloneNode(true);
					}
					else {
						mobile_contents = this.cloneNode(true);
					}

					// Append to Desktop
					if (desktop_contents != null) {
						var selected_contents = document.querySelector("#selected-contents");
						const topic = desktop_contents.dataset.topic;
						desktop_contents.children[1].innerHTML = topic + " " + desktop_contents.children[1].innerHTML;
						selected_contents.appendChild(desktop_contents);
					}

					if (mobile_contents != null) {
						// Append to Mobile
						selected_contents = document.querySelector("#mobile-selected-contents");
						const topic = mobile_contents.dataset.topic;
						mobile_contents.children[1].children[0].innerHTML = "Camp Name";
						mobile_contents.children[1].children[1].innerHTML = topic + " " + mobile_contents.children[1].children[1].innerHTML;
						selected_contents.appendChild(mobile_contents);
					}

				}
				// If it is checked, remove it from the selected_box
				else {
					if (!isMobile) {
						// Loop through entries in selected_box and remove the corresponding entry
						const selected_contents_children = document.querySelector("#selected-contents").children;
						const clicked_id = this.children[0].children[0].value.substring(2);
						for (let j = 0; j < selected_contents_children.length; j++) {
							const entry = selected_contents_children[j];
							const entry_id = entry.children[0].children[0].value.substring(2);
							if (entry_id === clicked_id) {
								entry.remove();
								break;
							}
						}
					}
					else {
						const selected_contents_children = document.querySelector("#mobile-selected-contents").children;
						console.log(selected_contents_children);
						const clicked_id = this.children[0].children[0].value.substring(2);
						for (let j = 0; j < selected_contents_children.length; j++) {
							const entry = selected_contents_children[j];
							const entry_id = entry.children[0].children[0].value.substring(2);
							if (entry_id === clicked_id) {
								entry.remove();
								break;
							}
						}
					}
				}
				this.classList.toggle("class-selected");
			}

			const w = parseInt(window.innerWidth);
			// Stops handleCollapseExpandTopics() for mobile
			if(w <= 576) {
				e.stopPropagation();
			}
		}
	}
}

// WHERE: Mobile Filter Modal
// WHEN: User clicks on a filter
// WHAT: Toggles filter checkbox
function handleFilterEntryCheckboxes() {
	var checkboxes = document.getElementsByClassName("filter-segment-entry");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].onclick = function(e) {
			this.children[0].classList.toggle("active-checkbox");
			console.log(this);
			const filter_type = this.parentNode.parentNode.children[0].children[1].innerHTML;
			let content = null;

			let corresponding_desktop_filter = null;
			if (filter_type == "Topic") {
				corresponding_desktop_filter = document.querySelector(".filter").children[0].children[1].children;
				content = this.dataset.topic.trim();
			}
			else if (filter_type == "Level") {
				corresponding_desktop_filter = document.querySelector(".filter").children[1].children[1].children;
				content = this.dataset.level.trim();
			}

			for (let j = 0; j < corresponding_desktop_filter.length; j++) {
				let entry = corresponding_desktop_filter[j].children[0].children[0];
				console.log(entry);
				if (filter_type == "Topic") {
					if (entry.children[1].dataset.topic.trim() == content) {
						entry.children[0].checked = !entry.children[0].checked;
						break;
					}
				}
				else if (filter_type == "Level") {
					if (entry.children[1].dataset.level.trim() == content) {
						entry.children[0].checked = !entry.children[0].checked;
						break;
					}
				}
			}


			const w = parseInt(window.innerWidth);
			if(w <= 576) {
				e.stopPropagation();
			}
		}
	}
}

// WHERE: Mobile Filter Modal
// WHEN: User clicks on a category (Topic, Level, etc.)
// WHAT: Toggles everything in that category on or off collectively
function handleFilterSegmentCheckboxes() {
	let checkboxes = document.getElementsByClassName("filter-segment-header");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].onclick = function(e) {
			let checked = this.children[0].classList.contains("active-checkbox");
			this.children[0].classList.toggle("active-checkbox");

			let entries = this.parentNode.children[1].children;

			// Set everything in the filter to the same state
			for (let i = 0; i < entries.length; i++) {
				let entry = entries[i];

				if (!checked) {
					entry.children[0].classList.add("active-checkbox");
				}
				else {
					entry.children[0].classList.remove("active-checkbox");
				}

			}
			var w = parseInt(window.innerWidth);

			if(w <= 576) {
				e.stopPropagation();
			}
		}
	}
}

// WHERE: Every topic (Python, Java, etc.)
// WHEN: User presses the expand or collapse arrow
// WHAT: Shows/hides contents of the topic
function handleCollapseExpandTopics() {
	// EXPAND AND COLLAPSE TOPIC
	var icons = document.getElementsByClassName("material-icons");
	for (let i = 0; i < icons.length; i++) {
		let icon = icons[i];

		if (icon.innerHTML == "expand_less" || icon.innerHTML == "expand_more") {
			icon.style.cursor = "pointer";
		}

		icon.onclick = function(e) {
			let desktopTopicContents = this.parentNode.parentNode.children[1];
			let mobileTopicContents = this.parentNode.parentNode.children[2];
			let topicContainer = this.parentNode.parentNode;
			iconType = this.innerHTML;
			if (iconType == "expand_less") {
				this.innerHTML = "expand_more";
				desktopTopicContents.classList.add("d-sm-block");
				mobileTopicContents.className = "topic-contents d-block d-sm-none";
				topicContainer.style.backgroundColor = "#F1F1F1";
			}
			else if (iconType == "expand_more") {
				this.innerHTML = "expand_less";
				desktopTopicContents.classList.remove("d-sm-block");
				mobileTopicContents.className = "d-none";
				topicContainer.style.backgroundColor = "#C4C4C4";

			}
		}
	}
}



var topics = document.getElementsByClassName("topic");
for (let i = 0; i < topics.length; i++) {
	let topic = topics[i];
	topic.onclick = function(e) {
		var w = parseInt(window.innerWidth);
		if(w <= 576) {
			let iconType = topic.children[0].children[1].innerHTML;
			let desktopContents = topic.children[1];
			let mobileContents = topic.children[2];
			if (iconType == "expand_more") {
				desktopContents.classList.remove("d-sm-block");
				mobileContents.className = "d-none";
				topic.style.backgroundColor = "#C4C4C4";
				topic.children[0].children[1].innerHTML = "expand_less";
			} else if (iconType == "expand_less") {
				desktopContents.classList.add("d-sm-block");
				mobileContents.className = "topic-contents d-block d-sm-none";
				topic.style.backgroundColor = "#F1F1F1";
				topic.children[0].children[1].innerHTML = "expand_more";
			}
		}
	}
}

// WHERE:
// WHEN: Filters need to be updated (the three buttons on desktop, submit on mobile)
// WHAT:
function handleApplyFilters() {
	resetFilters();

	const filters = document.querySelector(".filter").children;

	// Look at selected filters for topics
	//const topics = filters[0].children[1].children;
	const topics = filters[0].children;
	for (let i = 0; i < topics.length; i++) {
		const topic = topics[i].children[0].children[0].children[1].dataset.topic;
		const checked = topics[i].children[0].children[0].children[0].checked;
		// If that filter is not checked, hide it
		if(!checked){
			let topics = document.querySelectorAll(".topic");
			for (let j = 0; j < topics.length; j++) {
				// Don't hide if in selected-box
				//console.log(topics[j].dataset.topic.trim())
				if (!topics[j].classList.contains("selected-box")) {
					if (topics[j].dataset.topic.trim() == topic.trim()) {
						topics[j].style.display = "none";
					}
				}
			}
		}
	}

	// Look at selected filters for levels
	const levels = filters[1].children;
	for (let i = 0; i < levels.length; i++) {
		const level = levels[i].children[0].children[0].children[1].innerHTML.trim();
		const checked = levels[i].children[0].children[0].children[0].checked;

		// If that filter is not checked, hide it
		if (!checked) {
			let class_entries = document.querySelectorAll(".class-entry");
			for (let j = 0; j < class_entries.length; j++) {

				// Don't hide if in selected-box
				if (!class_entries[j].parentNode.parentNode.parentNode.classList.contains("selected-box")) {
					if (class_entries[j].dataset.level.trim() == level.trim()) {
						class_entries[j].style.display = "none";
					}
				}
			}
		}
	}
}

function handleMobileApplyFilters() {
	resetFilters();

	//
	const filters = document.querySelectorAll(".filter-segment");

	// MOBILE FILTERING FOR TOPICS
	const topics = filters[0].children[1].children;
	for (let i = 0; i < topics.length; i++) {
		const topic = topics[i].dataset.topic;
		const checked = topics[i].children[0].classList.contains("active-checkbox");
		if (!checked) {
			let topics = document.querySelectorAll(".topic");
			for (let j = 0; j < topics.length; j++) {
				if (!topics[j].classList.contains("selected-box")) {
					if (topics[j].dataset.topic.trim() == topic.trim()) {
						topics[j].style.display = "none";
					}
				}
			}
		}
	}

	// MOBILE FILTERING FOR LEVELS
	const levels = filters[1].children[1].children;
	for (let i = 0; i < levels.length; i++) {
		const level = levels[i].dataset.level;
		const checked = levels[i].children[0].classList.contains("active-checkbox");
		if (!checked) {
			let class_entries = document.querySelectorAll(".class-entry");
			for (let j = 0; j < class_entries.length; j++) {
				if (!class_entries[j].parentNode.parentNode.classList.contains("selected-box")) {
					if (class_entries[j].dataset.level.trim() == level.trim()) {
						class_entries[j].style.display = "none";
					}
				}

			}
		}
	}

	document.querySelector("#close-filter").click();

}

function resetFilters() {
	let topics = document.querySelectorAll(".topic");
	for (let i = 0 ; i < topics.length; i++) {
		topics[i].style.display = "block";
	}


	let class_entry = document.querySelectorAll(".class-entry");
	for (let i = 0 ; i < class_entry.length; i++) {
		class_entry[i].style.display = "flex";
	}
}

function handleApplyAllFilters() {
	let checkboxes = document.querySelectorAll("input[type=checkbox]");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = true;
	}

	let mobile_checkboxes = document.querySelectorAll(".filter-entry-checkbox");
	for (let i = 0; i < mobile_checkboxes.length; i++) {
		mobile_checkboxes[i].classList.add("active-checkbox")
	}

	let mobile_filters = document.querySelectorAll(".filter-checkbox");
	for (let i = 0; i < mobile_filters.length; i++) {
		mobile_filters[i].classList.add("active-checkbox")
	}
	handleApplyFilters();
}

function clearFilters() {
	let checkboxes = document.querySelectorAll("input[type=checkbox]");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = false;
	}

	let mobile_checkboxes = document.querySelectorAll(".filter-entry-checkbox");
	for (let i = 0; i < mobile_checkboxes.length; i++) {
		mobile_checkboxes[i].classList.remove("active-checkbox")
	}

	let mobile_filters = document.querySelectorAll(".filter-checkbox");
	for (let i = 0; i < mobile_filters.length; i++) {
		mobile_filters[i].classList.remove("active-checkbox")
	}

	handleApplyFilters();
	handleMobileApplyFilters();
}

function handleConfirmationModal() {
	event.preventDefault();
	const confirmation_modal = document.querySelector("#confirmation-modal-body");
	confirmation_modal.innerHTML = "";

	var w = parseInt(window.innerWidth);
	console.log(w);
	if (w > 576) {
		const selected_contents = document.querySelector("#selected-contents");
		const selected_contents_header = selected_contents.parentNode.children[0];
		confirmation_modal.appendChild(selected_contents_header.cloneNode(true));
		for (let i = 0; i < selected_contents.children.length; i++) {
			let cloned_camp = selected_contents.children[i].cloneNode(true);
			// remove input tag
			cloned_camp.children[0].children[0].remove();
			confirmation_modal.appendChild(cloned_camp);
		}
	}

	else {
		console.log("mobile");
		const selected_contents = document.querySelector("#mobile-selected-contents");
		for (let i = 0; i < selected_contents.children.length; i++) {
			const copy = selected_contents.children[i].cloneNode(true);
			console.log(copy.children[0])
			copy.children[0].remove();
			confirmation_modal.appendChild(copy);
		}
	}



}

