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
    document.getElementById("chat").innerHTML = '';

    for(var i = 0; i < arr.length; i++) {
        var tr = document.createElement('tr');

        var attributes = ["user", "message"];
        for(var attribute of attributes) {
            var td = document.createElement('td');
            td.innerHTML = arr[i][attribute];
            tr.appendChild(td);
        }
    
        document.getElementById("chat").appendChild(tr);
    }
}