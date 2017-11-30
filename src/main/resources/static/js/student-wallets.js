function show(id) {
    var artifacts_id = id + '-student-artifacts';
    var show_id = id + '-show';
    var hide_id = id + '-hide';
    document.getElementById(artifacts_id).style.display = 'block';
    document.getElementById(show_id).style.display = 'none';
    document.getElementById(hide_id).style.display = 'block';
}

function hide(id) {
    var artifacts_id = id + '-student-artifacts';
    var show_id = id + '-show';
    var hide_id = id + '-hide';
    document.getElementById(artifacts_id).style.display = 'none';
    document.getElementById(show_id).style.display = 'block';
    document.getElementById(hide_id).style.display = 'none';
}