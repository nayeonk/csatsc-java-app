var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("add-campers");

// Get the <span> element that closes the modal
// var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  	modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  	if (event.target == modal) {
    	modal.style.display = "none";
  	}
}

var submit_btn = document.getElementsByClassName("submit-btn")[0];
submit_btn.onclick = function() {
	modal.style.display = "none";
}