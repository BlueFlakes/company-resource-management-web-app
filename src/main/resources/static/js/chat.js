var lastMessageId = 0;
refresh();
window.setInterval(function(){
    refresh();
}, 1000);

function send() {
    var user = document.getElementById('name').value;
    var message = document.getElementById('message').value;
    var xmlhttp = new XMLHttpRequest();
    var url = "/chat";
    var params = "chat-user=" + user + '&chat-message=' + message;
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
    var params = "from=" + lastMessageId;
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