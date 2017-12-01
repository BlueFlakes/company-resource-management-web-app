var inputs = document.getElementsByTagName("input");
for(var i = 0; i < inputs.length; i++){
    if(inputs[i].type == 'text') {
        inputs[i].setAttribute("pattern", "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\-\\?]+[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ \\-]*$");
    }

    var chatMessage = document.getElementById("message");
    if(chatMessage != null) {
        chatMessage.setAttribute("pattern", "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\-\\?]+[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ \\-\\?]*$");
    }

}