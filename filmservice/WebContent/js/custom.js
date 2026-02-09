// returns a modal box with with specified properties
// requires a jquery object with the element content
function createModal(node) {
	node = $(node);
	dialog = node.dialog({
		autoOpen: true,
		height: 400,
		width: 350,
		modal: true,
	})
	return dialog;
}

//  returns a value of a node/element 
//  requires you to input a target (element) that gets used to traverse through the offset parent 
function searchOffsetValue(event, target) {

	let node = $(event.target.offsetParent).find(target);
	let id = node[0].value;
	return id
}

//  returns a value of a node/element 
//  requires you to input a target (element) that gets used to traverse through the offset parent 
function searchOffsetName(event, target) {

	let node = $(event.target.offsetParent).find(target);
	let id = node[0].name;
	return id
}

// Enables a node (element) to be interacted with
function enableNode(node) {
	node = $(node);
	node.prop("disabled", false);
}

// Disables a node (element). Can not be interacted with
function disableNode(node) {
	node = $(node);
	node.prop("disabled", true);
}

// Removes all child nodes of the taget 
function clearNode(node) {
	$(node).empty();
}

// Creates a div that provides contextual feedback (green == good)
// Requires the message, the location and the name or id 
function successAlert(text, target, title) {
	// changing the variable to a jquery object 
	target = $(target);

	let div = document.createElement("div");
	let h4 = document.createElement("h4");

	// adding attributes to the elements
	$(div).attr({ class: "alert alert-success", role: "alert" });
	$(h4).attr("class", "alert-heading");

	let msg = document.createTextNode(text + " " + title);

	h4.appendChild(msg);
	div.appendChild(h4);

	// appends above everything else in the target
	target.prepend(div);
	target.find(":input").val("");

}

// Creates a div that provides contextual feedback (red == bad)
// Requires the message, the location and the name or id 
function failedAlert(text, target, id) {
	// changing the variable to a jquery object 
	target = $(target);

	let div = document.createElement("div");
	let h4 = document.createElement("h4");

	$(div).attr({ class: "alert alert-danger", role: "alert" });
	$(h4).attr("class", "alert-heading");

	let msg = document.createTextNode(text + " " + id);

	h4.appendChild(msg);
	div.appendChild(h4);

	target.prepend(div);

}

// Vaildates the length of a node value (characters)
// vaildates by highlighting the node (element ?? input red)
// Returns false if the node is not within the range
function checklen(node, name, max, min) {

	if (node.val().length > max || node.val().length < min) {
		node.addClass("ui-state-error");
		invaildLen("Length of " + name + " out of range."
			+ " Max characters " + max + " and Min characters " + min + ".");
		return false;
	}
	else {
		return true;
	}
}
// Makes a specified (HTML Element) yellow for 500 milliseconds 
// (using jquery selector to grab the html element paragraph in this case)
function invaildLen(msg) {

	let vaildateLength = $(".validLen");
	vaildateLength.text(msg).addClass("ui-state-highlight");
	setTimeout(() => {
		// highligh goes away
		vaildateLength.removeClass("ui-state-highlight");
	}, 1000);
}

// vaildates by highlighting the node (element ?? input red)
// returns false if the value is not with in range.
function searchVaildate(node) {
	// changing it to a jquery object 
	node = $(node);
	let vaild = true;

	node.removeClass("ui-state-error");
	disableNode(node);
	// max length for int in java apparent is 10 digits 
	vaild = vaild && checklen(node, "ID", 10, 1);
	if (vaild == false) { enableNode(node); }
	return vaild
}

// Uses the createCard function to create a frag that is then used to put and diplay content
// Takes in json data and html (provided by the get or load) and then creates a dom frag with the data that frag gets appened to the html (node)
function placerJson(json, node) {

		$.get(BASE_URL + CARD_URL, (data)=>{ 
			
			// on each film place it in a card
			$.each(json, (_key, value) => {
				
			let frag = createFragment(data);
			// changing the plain javascript fragment to a jquery object 
			let card = $(frag);
			
			card.find(".card-header").text(value.title)
			card.find(".card-title").text(value.director)
			card.find(".card-text").text(value.review)
			card.find(".list-group-item-primary").text(value.stars);
			card.find(".list-group-item-success").text(value.year);
			card.find(".btn-info").attr("name", value.id);
			
			node.append(card[0]);
			
		});
	});


}

// Uses the createCard function to create a frag that is then used to put and diplay content
// Takes in XML data and html (provided by the get or load) and then creates a dom frag with the data that frag gets appened to the html (node)
function placerXML(element, xml, node) {
	
		$.get(BASE_URL + CARD_URL, (data )=>{ 
			
	// on each film place it in a card
	$(element, xml).each(function () {

		let frag = createFragment(data);
		
		// changing the plain javascript fragment to a jquery object 
		let card = $(frag);

		let title = $(this).find("title");
		let director = $(this).find("director");
		let review = $(this).find("review");
		let stars = $(this).find("stars");
		let id = $(this).find("id");
		let year = $(this).find("year");
		
		card.find(".card-header").text(title[0].textContent);
		card.find(".card-title").text(director[0].textContent);
		card.find(".card-text").text(review[0].textContent);
		card.find(".list-group-item-primary").text(stars[0].textContent);
		card.find(".list-group-item-success").text(year[0].textContent);
		card.find(".btn-info").attr("name", id[0].textContent);

		node.append(card[0]);
		})
	});

}

// Creates document frag used on the load
function createFragment(data) {
	
	let frag = new DocumentFragment();
	let html = $.parseHTML(data)
	frag.append(html[0]);
	return frag;
}
