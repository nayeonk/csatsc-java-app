(function() {
	window.addTextBoxForOther = function() {
		if(document.getElementById("camp-topic").value == "other") {
			document.getElementById("other-camp-topic").style.display = 'block';
		}
		
		else {
			document.getElementById("other-camp-topic").style.display = "none";
		}
	};
	
	window.addTextBoxForOtherLevel = function() {
		if(document.getElementById("camp-level").value == "other") {
			document.getElementById("other-camp-level").style.display = 'block';
		}
		
		else {
			document.getElementById("other-camp-level").style.display = "none";
		}
	};
	
	window.closeConfirmation = function(servlet, redirect) {
		var value = confirm('Are you sure you want to close?');
		console.log(servlet);
		
		if(value) {
			$.post(servlet, function() {
				console.log('success');
				window.location.replace(redirect);
			});
		}
		
		else {
			return false;
		}
		
	};	
	
	window.openConfirmation = function(servlet, redirect) {
		var value = confirm('Are you sure you want to open?');
		console.log(servlet);
		
		if(value) {
			$.post(servlet, function() {
				console.log('success');
				window.location.replace(redirect);
			});
		}
		
		else {
			return false;
		}
		
	};		
	
	window.deleteConfirmation = function(servlet, redirect) {
		var value = confirm('Are you sure you want to delete?');
		console.log(servlet);
		
		if(value) {
			$.post(servlet, function() {
				console.log('success');
				window.location.replace(redirect);
			});
		}
		
		else {
			return false;
		}
		
	};	
	
	window.setTwoNumberDecimal = function(element) {
	    element.value = parseFloat(element.value).toFixed(2);
	};
})();
