document.getElementById("#user-creation-password").addEventListener("mouseover", showPassword);
document.getElementById("#user-creation-password").addEventListener("mouseleave", hidePassword);

function showPassword() {
    document.getElementById("#user-creation-password").setAttribute("type", "input");
}

function hidePassword() {
    document.getElementById("#user-creation-password").setAttribute("type", "password");
}