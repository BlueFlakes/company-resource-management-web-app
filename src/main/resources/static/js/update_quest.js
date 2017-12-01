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
    document.getElementById("#mentor-update-quest-new-maxvalue").value = quest.maxValue;
    document.getElementById("#mentor-update-quest-new-quest-category").value = quest.categoryId;
    if(quest.maxValue == 0) {
        disableMaxValue();
    } else {
        enableMaxValue();
    }

}

document.getElementById("#is-ranged-true").onclick = enableMaxValue;
document.getElementById("#is-ranged-false").onclick = disableMaxValue;

function checkMaxValue() {
    var value = parseInt(document.getElementById("#mentor-update-quest-new-value").value);
    document.getElementById("#mentor-update-quest-new-maxvalue").setAttribute("min", value + 1);
}

function enableMaxValue() {
    document.getElementById("#max-value-label").style.display = 'block';
    document.getElementById("#mentor-update-quest-new-maxvalue").style.display = 'block';
    checkMaxValue();
    document.getElementById("#mentor-update-quest-new-maxvalue").removeAttribute("max");
    document.getElementById("#mentor-update-quest-new-maxvalue").addEventListener("change", checkMaxValue);
    document.getElementById("#mentor-update-quest-new-value").addEventListener("change", checkMaxValue);
    var value = parseInt(document.getElementById("#mentor-update-quest-new-value").value);

    document.getElementById("#is-ranged-true").checked = true;
    document.getElementById("#is-ranged-false").checked = false;
}

function disableMaxValue() {
    document.getElementById("#max-value-label").style.display = 'none';
    document.getElementById("#mentor-update-quest-new-maxvalue").style.display = 'none';
    document.getElementById("#mentor-update-quest-new-maxvalue").value = 0;
    document.getElementById("#mentor-update-quest-new-maxvalue").setAttribute("min", 0);
    document.getElementById("#mentor-update-quest-new-maxvalue").setAttribute("max", 0);
    document.getElementById("#mentor-update-quest-new-maxvalue").removeEventListener("change", checkMaxValue);
    document.getElementById("#mentor-update-quest-new-value").removeEventListener("change", checkMaxValue);

    document.getElementById("#is-ranged-true").checked = false;
    document.getElementById("#is-ranged-false").checked = true;
}