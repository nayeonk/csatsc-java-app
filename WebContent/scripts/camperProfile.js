function toggleOther(toggle) {
	if (toggle == 0) {
		document.getElementById('other-text').value = "";
		document.getElementById('other-text').disabled = true;
	}
	else if (toggle == 1) {
		document.getElementById('other-text').disabled = false;
	}
}