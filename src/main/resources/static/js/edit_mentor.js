document.getElementById("#manager-choose-mentor").addEventListener("change", fillFields);
document.getElementById("#edit-mentor-new-password").addEventListener("mouseover", showPassword);
document.getElementById("#edit-mentor-new-password").addEventListener("mouseleave", hidePassword);

function showPassword() {
    document.getElementById("#edit-mentor-new-password").setAttribute("type", "input");
}

function hidePassword() {
    document.getElementById("#edit-mentor-new-password").setAttribute("type", "password");
}

function fillFields() {
    var mentor_id = document.getElementById("#manager-choose-mentor").value;

    var http = new XMLHttpRequest();
    var url = "/manager/get_mentor";
    var params = "mentor_id=" + mentor_id;
    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function() {
        if(http.readyState == 4 && http.status == 200) {
            var mentor = JSON.parse(this.responseText);
            injectMentorData(mentor);
        }
    }
    http.send(params);
}

function injectMentorData(mentor) {
    document.getElementById("#edit-mentor-new-login").value = mentor.login;
    document.getElementById("#edit-mentor-new-password").value = mentor.password;
    document.getElementById("#edit-mentor-new-last-name").value = mentor.name;
    document.getElementById("#edit-mentor-new-email").value = mentor.email;
    document.getElementById("#manager-choose-class").value = mentor.class_id;
}