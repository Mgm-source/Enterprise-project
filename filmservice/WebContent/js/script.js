// Waiting for the DOM to be loaded before getting started.
document.addEventListener("DOMContentLoaded", startWebpage);

// Store the current page number 
var currentPage = 0;

// Timer 
var delayTimer;

// Urls for the different forms 
const FORM_URL = "?form";
const SEARCH_URL = "?search";
const CARD_URL = "?card";

// Url for the main servlet 
const BASE_URL = "Films";

// Url for different servlets
const INSERT_URL = "Insert";
const UPDATE_URL = "Update";
const DELETE_URL = "Delete";

function startWebpage() {

	console.log("Page loaded.");
	// When these links are clicked there callback functions are called. Each one has a modal.
	$("#insertFilm").click(insertModal);

	$("#deleteFilm").click(deleteModal);

	$("#updateFilm").click(updateModal);

	$("#otherformat").click(otherformat);

	// Cannot use the normal "click" because the button did not exist when the page was first loaded; 
	$(".row").on("click", "button.btn-info", cardModal);

	// Triggers datalist to show up on the search bar
	$("#searchTitle").keyup(searchResults);
	
	// Retrieves all the films from server (get request)
	getBody();

	// triggers the page to be changed
	$("#next").click(nextPage);
	$("#prev").click(prevPage);

}
// Sends a request gets 15 films put the result on the div with the class row
// When this function is executed the row div gets cleared (child nodes removed).
// Then a get request ( requesting json ) is sent. If successful the json get put on the DOM
// otherwise an error message will sure up
function getBody() {
	
	let node = ".row";
	clearNode(node);

	// $.getJSON(BASE_URL, , (data) => { jsonToHTML(data, node) });
	
	$.ajax({
		url: BASE_URL,
		data: { page: page },
		method: "get",
		dataType: "json",
		success: (data) => { jsonToHTML(data, node); },
		statusCode: { 404: missingError, 500: serverError }
	})
}

// Gets jsp page with the form 
// Then the form gets picked up by the modal and creates a insert modal that has two buttons; add film (inserts film)  and Exit (closes modal) 
function insertModal() {

	$.get(BASE_URL + FORM_URL, (htmlData)=>{ 
		
	let node = ".form";
	let form = createFragment(htmlData);
	
	clearNode(node);
	$(node).append(form);

	dialog = createModal(node).dialog({
		title: "Insert Film",
		buttons: {
			"Insert film": addFilm,
			Exit: () => { dialog.dialog("close"); }
			}
		})
	});

}

// Gets jsp page with the search form 
// Then the form gets picked up by the modal and creates a delete modal that can search for a film (by id) when a film is found the you can delete or not 
// when the delete button gets click on the searchOffsetName function finds the id from an element that is then returned and acts as the id
function deleteModal() {

	$.get(BASE_URL + SEARCH_URL, (htmlData) => {
	
	let node = ".form";
	let form = createFragment(htmlData);
	
	clearNode(node);
	$(node).append(form);

	// when the search button is clicked the film gets shown
	$("#search").click(idSearch);

	dialog = createModal(node).dialog({
		title: "Delete Film",
		buttons: [{
			text: "Delete film", click: (event) => {
				let id = searchOffsetName(event, ".btn-info");
				deleteFilm(id);
				enableNode("#userID");
			},
			disabled: true, class: "modelBtn"
		}, {
			text: "Exit", click: () => { dialog.dialog("close"); }
		}]
	})

 });

}

// Gets jsp page with the form 
// Then the form gets picked up by the modal and creates a update modal that can search for a film (by id) when a film is found the you can update or not 
// when the update button gets click on the searchOffsetName function finds the id from an element that is then returned and acts as the id
function updateModal() {

	$.get(BASE_URL + SEARCH_URL, (htmlData) => {
		
	let node = ".form";
	let form = createFragment(htmlData);
	
	clearNode(node);
	$(node).append(form);

	// when the search button is clicked the film gets shown
	$("#search").click(idSearchNEdit);

	dialog = createModal(node).dialog({
		title: "Update Film",
		buttons: [{
			text: "Update film", click: (event) => {
				let id = searchOffsetValue(event, "#userID")
				updateFilm(id);
				enableNode("#userID");
			},
			disabled: true, class: "modelBtn"
		}, {
			text: "Exit", click: () => { dialog.dialog("close"); }
		}]
	})

 });

}

// Gets jsp page with the form 
// Then the form gets picked up by the modal and creates a card modal that can update or delete a film that is clicked on
// When the update button gets click on the searchOffsetName function finds the id from an element that is then returned and acts as the id
// The callback (event parameter) is used to get the surrounding html
function cardModal(event) {
	
	let htmlCard = event.target.offsetParent;
	let id = searchOffsetName(event, ".btn-info");
	
	$.get(BASE_URL + FORM_URL, (htmlData) => {
		
	// The offset parent is retrieved and acts data 
	let node = ".form";
	let form = createFragment(htmlData);

	clearNode(node);
	$(node).append(form);

	dialog = createModal(node).dialog({
		title: "Update Film",
		buttons: [{
			text: "Update film", click: () => { updateFilm(id); },
			class: "modelBtn"
		}, {
			text: "Delete film", click: () => { deleteFilm(id); },
			class: "modelBtn"
		}, {
			text: "Exit", click: () => { dialog.dialog("close"); }
		}]
	})
	// When the modal shows up the inputs are filled in by the html in the page 
	placeFilmHTML(htmlCard);
 });

}

// increments the page by one
function nextPage() {

	currentPage = currentPage + 1;
	getBody();
}

// decrement the page by one
function prevPage() {

	if (!currentPage == 0) {
		currentPage = currentPage - 1;
		getBody();
	}

}

function page() {
	// returns the current page
	let page = currentPage
	return page
}

// Added a timer that waits a second till it sends a request 
// Timer is not needed wanted to request the amount of request done in a keypress
// Checks if the entry exist if not the button is disabled
function searchResults(ev) {

	clearTimeout(delayTimer);
	delayTimer = setTimeout(() => {
		let title = ev.target.value;
		
		// Sends a request get for a title films by title put the result on the div with the class datalist
		// when ever a key up event happens the element value is received and the get is triggered
		$.ajax({
			url: BASE_URL,
			data: { title: title },
			dataType: "json",
			success:  jsonDataList,
			statusCode: {
				500: serverError, 404: ()=> {
					missingError();
					disableNode("#otherformat");
					$("#otherformat").removeClass("btn-outline-success").addClass("btn-outline-danger");
				}
			}
			})
			
			
	}, 250);

}

// Sends a post request with the values from the form (dialog-form) if sucessfull alert (green) will show in the div if another alert (red) will show 
// vaildates the inputs and changes the case (To uppercase) of some inputs
function addFilm() {

	let title = $("#userTitle");
	let director = $("#userDirector");
	let review = $("#userReview");
	let stars = $("#userStars");
	let year = $("#userYear");

	// Add all the selected elements into an array or the declared variables up top
	let allFields = $([]).add(title).add(director).add(review).add(stars).add(year);

	// vaildates inputs by highlighting the incorrect ones
	// if vaildation passed post request sent 
	if (VaildateUserFilm(allFields)) {
		title = title.val().toUpperCase();
		director = director.val().toUpperCase();
		review = review.val();
		stars = stars.val().toUpperCase();
		year = year.val();

		// json object
		let data = { title: title, director: director, review: review, stars: stars, year: year }

		$.ajax({
			url: INSERT_URL,
			method: "post",
			data: data,
			success: ()=> {successInsert(data)},
			statusCode: {
				500: serverError, 404: () => { failedInsert(data) }
			}
		})

	}

}

// Vaildates the search input value 
// Gets the film by id and diplays it on the modal box
// If film doesn't exist/ not found an alert will show up in the modal box 
function idSearch(ev) {

	let input = "#userID";

	if (searchVaildate(input)) {

		// Finds the value of a element using the offset parent and travesing through to find the specified element 
		let id = searchOffsetValue(ev, input);

		// If the vaildatation passes the buttons with the class modelBtn are enabled
		enableNode(".modelBtn");
		$.ajax({
			url: BASE_URL,
			data:  { id: id },
			method: "get",
			dataType: "json",
			success: (data) => {
				disableNode("#search");
				jsonToHTML(data, "#content");
			},
			statusCode: { 404: contentError, 500: serverError }
		});
	}

}

// Vaildates the search input value 
// Gets the film by id and diplays it on the modal box
// If film doesn't exist/ not found an alert will show up in the modal box 
function idSearchNEdit(ev) {

	let input = "#userID";

	if (searchVaildate(input)) {

		// Finds the value of a element using the offset parent and travesing through to find the specified element 
		let id = searchOffsetValue(ev, input);

		// If the vaildatation passes the buttons with the class modelBtn are enabled
		enableNode(".modelBtn");
		$.ajax({
			url: BASE_URL,
			data: {id:id},
			method: "get",
			dataType: "json",
			success: (data) => {
				disableNode("#search");
				$.get(BASE_URL + FORM_URL, (htmlData)=> {
					
					let node = ".form";
					let form = createFragment(htmlData);
					
					$(node).append(form);
					placeFilmJson(data);
					
					});
			},
			statusCode: { 404: contentError, 500: serverError }
		});

	}

}

// Sends a delete request with the id for the film delete
// If film doesn't exist/ not found an alert will show up in the modal box 
function deleteFilm(id) {

	let data = { id: id }

	$.ajax({
		url: DELETE_URL,
		data: data,
		success: () => { successDelete(id); },
		statusCode: {
			500: serverError, 404: () => { failedDelete(id) }
		}
	});

}

// Sends a put request with the values from the form (dialog-form) if sucessfull alert (green) will show in the div if another alert (red) will show 
// vaildates the inputs and changes the case (To uppercase) of some inputs
function updateFilm(id) {

	let title = $("#userTitle");
	let director = $("#userDirector");
	let review = $("#userReview");
	let stars = $("#userStars");
	let year = $("#userYear");

	// Add all the selected elements into an array or the declared variables up top
	let allFields = $([]).add(title).add(director).add(review).add(stars).add(year);

	// vaildates inputs by highlighting the incorrect ones
	// if vaildation passed put request sent 
	if (VaildateUserFilm(allFields)) {
		title = title.val().toUpperCase();
		director = director.val().toUpperCase();
		review = review.val();
		stars = stars.val().toUpperCase();
		year = year.val();
		
		// json object
		let data = { title: title, director: director, review: review, stars: stars, year: year, id: id }

		$.ajax({
			url: UPDATE_URL,
			method: "post",
			data: data,
			success: () => { successUpdate(data) },
			statusCode: {
				500: serverError, 404: () => { failedUpdate(data) }
			}
		})

	}

}

// Vaildates the value of elements in a array with the matching id
// 5 element values are needed to be vaild for a return to be true
function VaildateUserFilm(nodeList) {

	nodeList = $(nodeList);

	let boolArr = [];
	let vaildNum = 5;

	// Removes the red outline
	nodeList.removeClass("ui-state-error");

	// Goes through nodelist (jquery object) and when a id matches the node value gets vaildated 
	// If it passes true gets put in the bool array otherwise... false. 
	$.each(nodeList, (_key, value) => {
		value = $(value);
		if (value[0].id === "userTitle") { boolArr.push(checklen(value, "title", 100, 1)); }
		if (value[0].id === "userDirector") { boolArr.push(checklen(value, "director", 100, 1)); }
		if (value[0].id === "userReview") { boolArr.push(checklen(value, "review", 1000, 1)); }
		if (value[0].id === "userStars") { boolArr.push(checklen(value, "stars", 100, 1)); }
		if (value[0].id === "userYear") { boolArr.push(checklen(value, "year", 999, 4)); }

	})

	// True counts as one so when one of the items in the array is false the condition below will fail (return false)
	return (vaildNum == boolArr.reduce((prev, curr) => { return prev + curr })) ? true : false;
	
}

// Disables the insert button
// Displays a alert showing the new title you just added
// Updates the body content after update
function successInsert(data) {
	disableNode(".modelBtn");

	successAlert("Added Film", ".form", data.title);
	getBody();
}

// Displays a alert showing the new title that failed to be added
function failedInsert(data) {

	failedAlert("Failed to add new film", ".form", data.title);
}

// Removes the form 
// Disables the update button that you have to search for another film or the same
// Displays a alert showing the title you just update
// Updates the body content after update
function successUpdate(data) {

	disableNode(".modelBtn");
	enableNode("#search");

	successAlert("Updated film", ".form", data.title);

	clearNode("#dialog-form");
	getBody();


}

// Displays a alert showing the new title that failed to be updated
function failedUpdate(data) {

	failedAlert("Failed to update film", ".form", data.title);
}

// Removes the film on the modal box 
// Disables the dellete button that you have to search for another film or the same
// Displays a alert showing the id of the film you just deleted
// Updates the body content after update
function successDelete(id) {

	disableNode(".modelBtn");
	enableNode("#search");
	
	successAlert("Deleted film", ".form", id);

	clearNode("#content");
	clearNode("#dialog-form");

	getBody();
}

// Displays a alert showing the film that failed to be deleted
function failedDelete(id) {

	failedAlert("failed to Delete film", ".form", id);
}

// Displays a error message when search for id fails
// Enables the search bar to look for another film
function contentError() {

	let node = $("#content");

	node.prepend("<p class='ui-state-error m-2'> ID not found </p>");

	setTimeout(() => {
		// Error message goes away
		$("p.ui-state-error").remove();
	}, 2000);

	disableNode(".modelBtn");
	enableNode("#userID");
}

// Displays a error message when a request fails generic (error message)
function missingError() {

	let node = $(".errors-film");

	node.prepend("<p class='ui-state-error m-2'> No Films </p>");

	setTimeout(() => {
		// Error message goes away
		$("p.ui-state-error").remove();
	}, 1000);

}

// Displays a error message when there's a server problem
function serverError() {

	let node = $(".errors-film")

	node.prepend("<p class='ui-state-error m-2'> Server Error! Try Site later.</p>");

}

// Places html data to the to input boxes or displays an error message if not found
// Used on the Card modal
function placeFilmHTML(data) {

	$("#userTitle").val($(data).find(".card-header").text());
	$("#userDirector").val($(data).find(".card-title").text());
	$("#userReview").val($(data).find(".card-text").text());
	$("#userStars").val($(data).find(".list-group-item-primary").text());
	$("#userYear").val($(data).find(".list-group-item-success").text());

}

// Places json data to the to input boxes or displays an error message if not found
// Used on the Edit modal
function placeFilmJson(data) {
	
	$("#userTitle").val(data[0].title);
	$("#userDirector").val(data[0].director);
	$("#userReview").val(data[0].review);
	$("#userStars").val(data[0].stars);
	$("#userYear").val(data[0].year);

}

// Places json data on to the DOM
// The json first put into a card div and then put on the target
// This is splicing the json so i get 15 results on the DOM 
function jsonToHTML(data, node) {
	
	node = $(node);
	placerJson(data, node);

}

// Each result creates datalist with the result of user input using get request data
// Does a for each loop for every data item and append it to the div for the search results 
// Remove the old datalist every user input
// Enables the button to be clicked
function jsonDataList(data) {
	
		let frag = new DocumentFragment();

		let datalist = document.createElement("datalist");
		$(datalist).attr({ id: "film-titles" });

		$.each(data, (_key, value) => {

			let option = document.createElement("option");

			$(option).val(value.title)

			datalist.appendChild(option);
			frag.append(datalist);

		});
		
		$("datalist#film-titles").remove();
		$(".datalist").append(frag);
		
		enableNode("#otherformat");
		$("#otherformat").removeClass("btn-outline-danger").addClass("btn-outline-success");

}

// When you input data into the search bar two seperate get requests one get back xml and the other csv data from your input
// The formats are then put on the DOM
// If a request fails a alert will show up in the modal box
// Remove the old results when called
function otherformat(event) {

	let row = $(".row");
	let title = searchOffsetValue(event, "#searchTitle").toUpperCase();

	$(".col-5").remove();
		
	// Sends a get request with an id and expects XML to be returned 
	$.get({

		url: BASE_URL + "?format=xml",
		data: {title:title},
		method: "get",
		success: (data) => {

			let col1 = document.createElement("div");
			$(col1).attr("class", "col-5 bg-secondary");
			col1.innerText = "XML GENERATED";

			let xml = $.parseXML(data);

			placerXML("film", xml, col1);
			row.prepend(col1);
		},
		statusCode: { 404: missingError, 500: serverError }

	});

	// Sends a get request with an id and expects CSV to be returned 
	$.get({

		url: BASE_URL + "?format=text",
		data: {title:title},
		method: "get",
		success: (data) => {

			let col2 = document.createElement("div");
			$(col2).attr("class", "col-5 bg-warning");
			col2.innerText = "CSV GENERATED";

			let csv = $.csv.toObjects(data);
			placerJson(csv, col2);
			row.prepend(col2);
		},
		statusCode: { 404: missingError, 500: serverError }

	});

}

/*  Deprecated Code Refactored stuff 

function idSearch(ev) {
	let input = "#" + searchOffsetName(ev, "#userID");
	if (searchVaildate(input)) {
		let id = searchOffsetValue(ev, input);
		$(".modelBtn").prop("disabled", false);
		$.getJSON(BASE_URL, , (data) => {
			jsonToHTML(data, "#content");
		});

	}

}

// gets the film by id and diplays it on the modal box 
function idSearchNEdit(ev) {
	let input = "#" + searchOffsetName(ev, "#userID");
	if (searchVaildate(input)) {
		let id = searchOffsetValue(ev, input);
		$(".modelBtn").prop("disabled", false);
		$("#content").load(BASE_URL + FORM_URL, () => {
			$.getJSON(BASE_URL, { id: id }, placeFilmJson);
		});

	}

}

	$.getJSON(BASE_URL, , (data) => { jsonToHTML(data, node) });

*/