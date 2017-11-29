document.getElementById("#mentor-update-artifact-choose-artifact").addEventListener("change", fillFields);

function fillFields() {
    var artifact_id = document.getElementById("#mentor-update-artifact-choose-artifact").value;

    var http = new XMLHttpRequest();
    var url = "/mentor/get_artifact";
    var params = "artifact_id=" + artifact_id;
    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function() {
        if(http.readyState == 4 && http.status == 200) {
            var artifact = JSON.parse(this.responseText);
            injectArtifactData(artifact);
        }
    }
    http.send(params);
}

function injectArtifactData(artifact) {
    document.getElementById("#mentor-update-artifact-new-name").value = artifact.name;
    document.getElementById("#mentor-update-artifact-new-description").value = artifact.description;
    document.getElementById("#mentor-update-artifact-new-price").value = artifact.price;
    document.getElementById("#mentor-update-artifact-new-artifact-category").value = artifact.categoryId;
}