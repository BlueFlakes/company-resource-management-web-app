disableMaxValue();
document.getElementById("#is-ranged-true").onclick = enableMaxValue;
document.getElementById("#is-ranged-false").onclick = disableMaxValue;

function checkMaxValue() {
    var value = parseInt(document.getElementById("#mentor-add-quest-value").value);
    document.getElementById("#mentor-add-quest-maxvalue").setAttribute("min", value + 1);
}

function enableMaxValue() {
    document.getElementById("#max-value-label").style.display = 'block';
    document.getElementById("#mentor-add-quest-maxvalue").style.display = 'block';
    checkMaxValue();
    document.getElementById("#mentor-add-quest-maxvalue").addEventListener("change", checkMaxValue);
    document.getElementById("#mentor-add-quest-value").addEventListener("change", checkMaxValue);
    var value = parseInt(document.getElementById("#mentor-add-quest-value").value);
    document.getElementById("#mentor-add-quest-maxvalue").value = value + 1;
}

function disableMaxValue() {
    document.getElementById("#max-value-label").style.display = 'none';
    document.getElementById("#mentor-add-quest-maxvalue").style.display = 'none';
    document.getElementById("#mentor-add-quest-maxvalue").value = 0;
    document.getElementById("#mentor-add-quest-maxvalue").removeEventListener("change", checkMaxValue);
    document.getElementById("#mentor-add-quest-value").removeEventListener("change", checkMaxValue);
}