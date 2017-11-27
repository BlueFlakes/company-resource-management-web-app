var lastMessageId = 0;
getAvailableRooms();
refresh();
document.getElementById("rooms").addEventListener("change", changeRoom);
document.getElementById("chat-input").addEventListener("submit", send);
window.setInterval(function(){
    refresh();
}, 1000);

function send(evt) {
    evt.preventDefault();
    var user = document.getElementById('name').value;
    var message = document.getElementById('message').value;
    var room = document.getElementById("rooms").value;
    var xmlhttp = new XMLHttpRequest();
    var url = "/chat";
    var params = "chat-user=" + user + '&chat-message=' + message + "&room=" + room;
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4) {

        }
    }
    document.getElementById('message').value = '';
    xmlhttp.send(params);
    return false;
}

function refresh() {
    var xmlhttp = new XMLHttpRequest();
    var url = "/chat";

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var newMessages = JSON.parse(this.responseText);
            appendTable(newMessages);
            if(newMessages.length != 0) {
                scrollDown();
            }
        }
    };
    var room = document.getElementById("rooms").value;
    var params = "from=" + lastMessageId + "&room=" + room;
    xmlhttp.open("POST", url, true);
    xmlhttp.send(params);
}

function appendTable(arr) {

    for(var i = 0; i < arr.length; i++) {
        var tr = document.createElement('tr');

        updateLastMessageId(arr[i]["id"]);
        createUser(tr, arr[i]["user"]);
        createMessage(tr, arr[i]["message"]);
    
        document.getElementById("chat").appendChild(tr);
    }
}

function updateLastMessageId(id) {
    var currentId = parseInt(id);
    if(currentId > window.lastMessageId) {
        window.lastMessageId = currentId;
    }
}

function createUser(tr, user) {
    var td = document.createElement('td');
    td.setAttribute("class", "user");
    td.innerHTML = user;
    tr.appendChild(td);
}

function createMessage(tr, message) {
    var td = document.createElement('td');
    td.setAttribute("class", "message");
    td.innerHTML = message;
    tr.appendChild(td);
}

function scrollDown() {
    var chatWindow = document.getElementById("chat");
    chatWindow.scrollTop = chatWindow.scrollHeight;
}

function getAvailableRooms() {
    var xmlhttp = new XMLHttpRequest();
    var url = "/chat";

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var rooms = JSON.parse(this.responseText);
            var roomsList = document.getElementById("rooms");
            for(var i = 0; i < rooms.length; i++) {
                var room = document.createElement("option");
                room.setAttribute("value", rooms[i]);
                room.innerHTML = rooms[i];
                roomsList.appendChild(room);
            }
        }
    };

    var params = "get_rooms=";
    xmlhttp.open("POST", url, true);
    xmlhttp.send(params);

}

function changeRoom() {
    var room = document.getElementById("rooms").value;
    document.getElementById("message").disabled = (room == "System messages");
    document.getElementById("message").focus();
    window.lastMessageId = 0;
    document.getElementById("chat").innerHTML = "";
    refresh();
}