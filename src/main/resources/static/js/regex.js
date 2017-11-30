var inputs = document.getElementsByTagName("input");
for(var i = 0; i < inputs.length; i++){
    if(inputs[i].type == 'text') {
        inputs[i].setAttribute("pattern", "^[0-9\\w]+[0-9\\w \\-]*$");
    }

    document.getElementById("message").setAttribute("pattern", "^[0-9\\w\\-\\?]+[0-9\\w \\-\\?]*$");
}