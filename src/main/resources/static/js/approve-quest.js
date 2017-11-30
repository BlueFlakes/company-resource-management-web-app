getMinMaxById();
document.getElementById("#mentor-approve-assignment-choose-assignment").addEventListener("change", getMinMaxById);
document.getElementById("#mentor-approve-assignment-value").addEventListener("change", setValueLabel);
document.getElementById("#mentor-approve-assignment-value-label").addEventListener("change", setLabelValue);

function getMinMaxById() {
    var quest_id = document.getElementById("#mentor-approve-assignment-choose-assignment").value;

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
    var value = document.getElementById("#mentor-approve-assignment-value");
    var label = document.getElementById("#mentor-approve-assignment-value-label");

    if(quest.maxValue == 0) {
        label.disabled = true;
        label.setAttribute("min", quest.value);
        label.setAttribute("max", quest.value);
        label.value = quest.value;

        value.style.display = 'none';
        value.setAttribute("min", quest.value);
        value.setAttribute("max", quest.value);
        value.value = quest.value;
    } else {
        label.disabled = false;
        label.setAttribute("min", quest.value);
        label.setAttribute("max", quest.maxValue);
        label.value = quest.value;

        value.style.display = 'block';
        value.value = quest.value;
        value.setAttribute("min", quest.value);
        value.setAttribute("max", quest.maxValue);
        setValueLabel();
    }
}

function setValueLabel() {
    var label = document.getElementById("#mentor-approve-assignment-value-label");
    var value = document.getElementById("#mentor-approve-assignment-value");

    label.value = value.value;
}

function setLabelValue() {
    var label = document.getElementById("#mentor-approve-assignment-value-label");
    var value = document.getElementById("#mentor-approve-assignment-value");

    value.value = label.value;
}