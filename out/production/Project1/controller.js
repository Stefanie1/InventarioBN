var xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        var myArr = JSON.parse(this.responseText);
        displayHotelList(myArr);
    }
};
xmlhttp.open("GET", "/hotelInfo", true);
xmlhttp.send();

function addUser() {
    var user = getUser();
    if (user != "") {
        var node = document.getElementById("user");
        if (node.hasChildNodes()) {
            node.removeChild(node.lastChild);
        }
        var out = '<li><a><span class="glyphicon glyphicon-user"></span>'+user+'</a></li>';
        out += '<li><a href="/login?logout"><span class="glyphicon glyphicon-user"></span> Logout</a></li>';
        node.innerHTML = out;
    }
}

function displayHotelList(arr) {
    addUser();
    var out = "";
    for (var i = 0; i < arr.length; i++) {
        var id = arr[i].hotelId;
        out += '<div id=' + id + ' class="hotelDiv">';
        out += '<span class="firstLabel">' + arr[i].name + '</span>';
        out += '<span class="secondLabel badge">rating: ' + arr[i].rating + '</span>';
        out += '<p>' + arr[i].addr + '</p>';
        out += '<p>' + arr[i].city + '</p>';
        out += '<div id="action' + id + '" class="secondLabel">';
        out += '<div id="action' + id + '" class="secondLabel">\n' +
            '  <button id=' + id + ' class="dropbtn" onclick="getReviews(this.id)">Reviews</button>\n' +
            '  <div class="dropdown">\n' +
            '    <button class="dropbtn" onclick="dropDown()">Attractions\n' +
            '      <i class="fa fa-caret-down"></i>\n' +
            '    </button>\n' +
            '    <div class="dropdown-content" id="myDropdown">\n' +
            '<p>Select Radius</p>' +
            '      <span id=' + id + ' onclick="getAttractions(this.id, 2)">2</span>\n' +
            '      <span id=' + id + ' onclick="getAttractions(this.id, 5)">5</span>\n' +
            '      <span id=' + id + ' onclick="getAttractions(this.id, 7)">7</span>\n' +
            '      <span id=' + id + ' onclick="getAttractions(this.id, 10)">10</span>\n' +
            '    </div>\n' +
            '  </div> \n';
        out += '</div>';
        out += '</div>';
        out += '<p >' + arr[i].state + '</p>';
        out += '</div><hr>';
    }
    document.getElementById("main").innerHTML = out;
}

function getAttractions(hotelId, radius) {
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var myArr = JSON.parse(this.responseText);
            displayAttractions(myArr);
        }
    };
    xmlhttp.open("GET", '/attractions?hotelId=' + hotelId + '&radius=' + radius, true);
    xmlhttp.send();
}

function displayAttractions(arr) {
    addUser();
    var out = "";
    for (var i = 0; i < arr.results.length; i++) {
        var place = arr.results[i];
        out += '<div id=' + place.place_id + ' class="hotelDiv">';
        out += '<span class="firstLabel">' + place.name + '</span>';
        out += '<span class="secondLabel badge">rating: ' + place.rating + '</span>';
        out += '<p>' + place.formatted_address + '</p>';
        out += '</div><hr>';
    }
    document.getElementById("main").innerHTML = out;
}

function getReviews(id) {
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var myArr = JSON.parse(this.responseText);
            displayReviewList(myArr);
        }
    };
    xmlhttp.open("GET", '/reviews?hotelId=' + id + '&num=10', true);
    xmlhttp.send();
}

function displayReviewList(arr) {
    addUser();
    var user = getUser();
    var out = "";
    for (var i = 0; i < arr.reviews.length; i++) {
        var id = arr.reviews[i].reviewId;
        out += '<div id=div' + id + ' class="reviewDiv">';
        out += '<span>Title</span><p id=title' + id + '>' + arr.reviews[i].title + '</p>';
        out += '<span id=rating' + id + ' class="secondLabel badge" >rating: ' + arr.reviews[i].rating + '</span>';
        out += '<span>Text</span><p id=text' + id + '>' + arr.reviews[i].reviewText + '</p>';
        out += '<span>Is Recomended</span><p id=isRecom' + id + '>' + arr.reviews[i].isRecom + '</p>';
        if (user == arr.reviews[i].user) {
            document.getElementById("newReview").innerHTML = '<button id=' + arr.hotelId + ' class="dropbtn" onclick="addReviewForm(this.id)">Add new Review</button>';
            out += '<div class="secondLabel">';
            out += '<p id="action' + id + '" class="secondLabel">';
            out += '<button id=' + id + ' class="dropbtn" onclick="editReview(this.id)"> modify </button>';
            out += '<button id=' + id + ' class="dropbtn" onclick="deleteReview(this.id)"> delete </button>';
            out += '</p>';
            out += '</div>'
        }
        out += '<span>Date</span><p>' + arr.reviews[i].date + '</p>';
        out += '</div><hr>';
    }
    document.getElementById("main").innerHTML = out;
}

function addReviewForm(id) {
    var out = "";
    var action = "action" + id;
    var elem = document.getElementById('newReview');
    elem.removeChild(elem.lastChild);
    out += '<p>Title</p><textarea rows="1" cols="150" id="titleInput"></textarea>';
    out += '<p>Text</p><textarea rows="10" cols="150" id="textInput"></textarea>';
    out += '<p>Rating</p><select id="ratingtInput"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select>';
    out += '<p>Is Recomended</p><select id="isRecomInput"><option value="True">True</option><option value="False">False</option></select>';
    out += '<p>Title</p><p><button id=' + id + ' class="dropbtn" onclick="updateReview(this.id,document.getElementById(\'titleInput\').value,document.getElementById(\'textInput\').value,document.getElementById(\'ratingtInput\').value,document.getElementById(\'isRecomInput\').value, getReviews, \'create\')">UPDATE</button></p>';
    document.getElementById('newReview').innerHTML = out;
}

function editReview(id) {
    var text = "text" + id;
    var title = "title" + id;
    var rating = "rating" + id;
    var isRecom = "isRecom" + id;
    var div = "div" + id;
    var out = "";
    var action = "action" + id;
    var elem = document.getElementById(action);
    elem.parentNode.removeChild(elem);
    out += '<p>Title</p><textarea rows="1" cols="150" id="titleInput">' + document.getElementById(title).innerHTML + '</textarea>';
    out += '<p>Text</p><textarea rows="10" cols="150" id="textInput">' + document.getElementById(text).innerHTML + '</textarea>';
    out += '<p>Rating</p><select id="ratingtInput"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select>';
    out += '<p>Is Recomended</p><select id="isRecomInput"><option value="True">True</option><option value="False">False</option></select>';
    out += '<p>Title</p><p><button id=' + id + ' class="dropbtn" onclick="updateReview(this.id,document.getElementById(\'titleInput\').value,document.getElementById(\'textInput\').value,document.getElementById(\'ratingtInput\').value,document.getElementById(\'isRecomInput\').value, updateReviewTag, \'update\')">UPDATE</button></p>';
    // out += '<p>Rating</p><textarea rows="1" cols="150" id="ratingtInput">' + document.getElementById(rating).innerHTML + '</textarea>';
    // out += '<p>Is Recomended</p><select rows="1" cols="150" id="isRecomInput">' + document.getElementById(isRecom).innerHTML + '</select>';
    document.getElementById(div).innerHTML = out;
}

function deleteReview(id) {
    var params = "action=delete&id=" + id;
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var response = this.responseText;
            alert('Modification\n success  ' + response);
            deleteReviewTag(id);
        }
    };
    xmlhttp.open("POST", '/reviews', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(params);
}ttf

function deleteReviewTag(id) {
    var div = "div" + id;
    var elem = document.getElementById(div);
    elem.parentNode.removeChild(elem);
}


function updateReviewTag(id, title, text, rating, isRecom) {
    var elem = document.getElementById(id);
    var p = elem.parentNode;
    var node = p.parentNode;
    while (node.hasChildNodes()) {
        node.removeChild(node.lastChild);
    }
    var out = "";
    out += '<div class="secondLabel">';
    out += '</div>';
    out += '<p id=title' + id + '>' + title + '</p>';
    out += '<span id=rating' + id + ' class="secondLabel badge" >rating: ' + rating + '</span>';
    out += '<p id=text' + id + '>' + text + '</p>';
    out += '<span>Is Recomended</span><p id=isRecom' + id + '>' + isRecom + '</p>';
    out += '<p id="action' + id + '" class="secondLabel">';
    out += '<button id=' + id + ' class="dropbtn" onclick="editReview(this.id)"> modify </button>';
    out += '<button id=' + id + ' class="dropbtn" onclick="deleteReview(this.id)"> delete </button>';
    out += '</p>';
    out += '<p>' + Date(Date.now()).toString() + '</p>';
    node.innerHTML = out;
}

function updateReview(id, title, text, rating, isRecom, success, action) {
    var params = "action=" + action + "&id=" + id + "&title=" + title + "&text=" + text + "&rating=" + rating + "&isRecom=" + isRecom;
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var response = JSON.parse(this.responseText);
            alert('Modification\n success  ' + response);
            success(id, title, text, rating, isRecom);
        }
    };
    xmlhttp.open("POST", '/reviews', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(params);
}

function getUser() {
    if (document.cookie != "") {
        var c = document.cookie.split(';');
        c[1] = c[1].substring(1);
        return c[1].substring(5, c[1].length);
    }
    return "";
}

function dropDown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}
