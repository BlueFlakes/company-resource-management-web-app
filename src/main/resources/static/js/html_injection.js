var insults = [
    "Do not even try",
    "Don\'t do it",
    "Just don\'t!",
    "You are so pitiful"
];

var insultIndex = 0;

document.onkeypress = function (e) {
    e = e || window.event;
    if(e.key == "<" || e.key == ">") {
        alert(window.insults[window.insultIndex]);
        if(window.insultIndex < window.insults.length - 1) {
            window.insultIndex += 1;
        } else {
            window.insultIndex = 0;
        }
    }
};