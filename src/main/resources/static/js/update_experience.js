document.getElementById("#manager-exp-level-choose-new-class").addEventListener("change", fillForm);

function fillForm() {
    var optionText = getDatafield("#manager-exp-level-choose-new-class");
    var levelValue = optionText.split(": ")[1];

    document.getElementById("#manager-new-value-for-exp-level").value = levelValue;
}

function getDatafield(elementId) {
    var elt = document.getElementById(elementId);

    if (elt.selectedIndex == -1)
        return null;

    return elt.options[elt.selectedIndex].innerHTML;
}