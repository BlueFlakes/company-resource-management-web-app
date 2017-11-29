document.getElementById("#mentor-update-quest-choose-quest").addEventListener("change", fillFields);

function fillFields() {
    var quest_id = document.getElementById("#mentor-update-quest-choose-quest").value;

    var http = new XMLHttpRequest();
    var url = "/mentor/get_quest";
    var params = "quest_id=" + quest_id;
    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function() {
        if(http.readyState == 4 && http.status == 200) {
            var quest = JSON.parse(this.responseText);
            injectQuestData(quest);
        }
    }
    http.send(params);
}

function injectQuestData(quest) {
    document.getElementById("#mentor-update-quest-new-name").value = quest.name;
    document.getElementById("#mentor-update-quest-new-description").value = quest.description;
    document.getElementById("#mentor-update-quest-new-value").value = quest.value;
    document.getElementById("#mentor-update-quest-new-quest-category").value = quest.categoryId;
}