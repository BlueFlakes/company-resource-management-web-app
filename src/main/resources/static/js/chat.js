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
            var myArr = JSON.parse(this.responseText);
            myFunction(myArr);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function myFunction(arr) {
    var out = "";
    var i;
    for(i = 0 ; i < arr.length; i++) {
        out += '<tr>'
        out += '<td>' + arr[i].user +' </td>';
        out += '<td>' + arr[i].message +' </td>';
        out += '</td>';
    }
    document.getElementById("chat").innerHTML = out;
}