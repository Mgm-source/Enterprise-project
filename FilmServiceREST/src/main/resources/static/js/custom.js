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

// Takes in json data and html (provided by the get or load) and then creates a dom frag with the data that frag gets appened to the html (node)
function placerJson(data, node) {

	// on each film place it in a card
	$.each(data, (_key, value) => {

		let frag = createCard();

		// changing the plain javascript fragment to a jquery object 
		let card = $(frag);

		card.find(".card-header").text(value.title)
		card.find(".card-title").text(value.director)
		card.find(".card-text").text(value.review)
		card.find(".list-group-item-primary").text(value.stars);
		card.find(".list-group-item-success").text(value.year);
		card.find(".btn-info").attr("name", value.pkid);

		node.append(card[0]);

	});

}

// Takes in XML data and html (provided by the get or load) and then creates a dom frag with the data that frag gets appened to the html (node)
function placerXML(element, data, node) {

	// on each film place it in a card
	$(element, data).each(function () {

		let frag = createCard();

		// changing the plain javascript fragment to a jquery object 
		let card = $(frag);

		let title = $(this).find("title");
		let director = $(this).find("director");
		let review = $(this).find("review");
		let stars = $(this).find("stars");
		let id = $(this).find("pkid");
		let year = $(this).find("year");

		card.find(".card-header").text(title[0].textContent);
		card.find(".card-title").text(director[0].textContent);
		card.find(".card-text").text(review[0].textContent);
		card.find(".list-group-item-primary").text(stars[0].textContent);
		card.find(".list-group-item-success").text(year[0].textContent);
		card.find(".btn-info").attr("name", id[0].textContent);

		node.append(card[0]);

	})

}

// Create card frag
function createCard() {

	let frag = new DocumentFragment();

	let parentDiv = document.createElement("div");
	$(parentDiv).attr({ class: "card bg-light m-1", style: "max-width: 18rem;" });

	let headerDiv = document.createElement("div");
	$(headerDiv).attr("class", "card-header");

	let bodyDiv = document.createElement("div");
	$(bodyDiv).attr("class", "card-body");
	$(bodyDiv).text("Director");

	let titleH5 = document.createElement("h5");
	$(titleH5).attr("class", "card-title");

	let textP = document.createElement("p");
	$(textP).attr("class", "card-text");

	let ulGroup = document.createElement("ul");
	$(ulGroup).attr("class", "list-group list-group-flush");

	let starslist = document.createElement("li");
	$(starslist).attr("class", "list-group-item list-group-item-primary");

	let yearlist = document.createElement("li");
	$(yearlist).attr("class", "list-group-item list-group-item-success");

	let idBttn = document.createElement("button");
	$(idBttn).attr({ type: "button", class: "btn btn-info" });
	$(idBttn).text("Update");
	
	let filmImage = document.createElement("img");
	$(filmImage).attr("class", "card-img");
	$(filmImage).attr("src", "http://localhost:8080/tomcat.svg")

	parentDiv.appendChild(headerDiv);
	bodyDiv.appendChild(titleH5);
	bodyDiv.appendChild(textP);
	bodyDiv.appendChild(filmImage);
	parentDiv.appendChild(bodyDiv);
	ulGroup.appendChild(starslist);
	ulGroup.appendChild(yearlist);
	parentDiv.appendChild(ulGroup);
	parentDiv.appendChild(idBttn);
	frag.appendChild(parentDiv);

	return frag;
}

// Create user input form frag
function createUserInput() {

	let frag = new DocumentFragment();

	let parentDiv = document.createElement("div");
	$(parentDiv).attr("id", "dialog-form");

	let formDiv = document.createElement("form");

	let groupDiv = document.createElement("div");
	$(groupDiv).attr("class", "form-group");

	let titleInput = document.createElement("input");
	$(titleInput).attr({ class: "form-control", id: "userTitle", placeholder: "Enter title", type: "text" });

	let directorInput = document.createElement("input");
	$(directorInput).attr({ class: "form-control", id: "userDirector", placeholder: "Enter Director", type: "text" });

	let yearInput = document.createElement("input");
	$(yearInput).attr({ class: "form-control", id: "userYear", placeholder: "Enter year", type: "number" });

	let starsInput = document.createElement("input");
	$(starsInput).attr({ class: "form-control", id: "userStars", placeholder: "Enter star/s", type: "text" });

	let reviewInput = document.createElement("textarea");
	$(reviewInput).attr({ class: "form-control", id: "userReview", placeholder: "Enter review", row: "10" });
	
	let imageInput = document.createElement("input");
	$(imageInput).attr({class : "form-control", id:"imageInput", accept : "image/*", type : "file"});

	let vaildP = document.createElement("p");
	$(vaildP).attr("class", "validLen");

	parentDiv.appendChild(vaildP);

	groupDiv.appendChild(titleInput);
	groupDiv.appendChild(directorInput);
	groupDiv.appendChild(yearInput);
	groupDiv.appendChild(starsInput);
	groupDiv.appendChild(reviewInput);
	groupDiv.appendChild(imageInput);
	formDiv.append(groupDiv);


	parentDiv.appendChild(formDiv);
	frag.appendChild(parentDiv);

	return frag;
}

// Creates user search frag
function createUserSearch() {

	let frag = new DocumentFragment();

	let parentDiv = document.createElement("div");
	$(parentDiv).attr("class", "input-group mb-3");

	let groupDiv = document.createElement("div");
	$(groupDiv).attr("class", "input-group-prepend");

	let srcBtn = document.createElement("button");
	$(srcBtn).attr({ type: "button", class: "btn btn-primary ", id: "search" });
	$(srcBtn).text("Search");

	let srcInput = document.createElement("input");
	$(srcInput).attr({ class: "form-control", id: "userID", placeholder: "Enter ID", type: "number", name: "userID" });

	let contentDiv = document.createElement("div");
	$(contentDiv).attr("id", "content");

	groupDiv.appendChild(srcBtn);
	parentDiv.appendChild(groupDiv);
	parentDiv.appendChild(srcInput);

	frag.append(parentDiv);
	frag.append(contentDiv);

	return frag;
}