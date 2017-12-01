document.getElementById("#edit-mentor-new-password").addEventListener("mouseover", showPassword);
document.getElementById("#edit-mentor-new-password").addEventListener("mouseleave", hidePassword);

function showPassword() {
    document.getElementById("#edit-mentor-new-password").setAttribute("type", "input");
}

function hidePassword() {
    document.getElementById("#edit-mentor-new-password").setAttribute("type", "password");
}