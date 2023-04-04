function deleteStudents() {

    const checkedCheckboxes = document.querySelectorAll('input[name=studentId]:checked');

    if (checkedCheckboxes.length == 0) {
        alert("Пожалуйста, выберите хотя бы одного студента");
        return;
    }

    // 1 3 5

    let ids = "";

    for (let i = 0; i < checkedCheckboxes.length; i++) {
        ids = ids + checkedCheckboxes[i].value + " ";
    }

    document.getElementById("idsForDelete").value = ids;
    document.getElementById("deleteForm").submit();
}

function modifyStudent() {

    const checkedCheckboxes = document.querySelectorAll('input[name=studentId]:checked');

    if (checkedCheckboxes.length == 0) {
        alert("Пожалуйста, выберите одного студента");
        return;
    }

    if (checkedCheckboxes.length > 1) {
        alert("Пожалуйста, выберите только одного студента");
        return;
    }

    // 1 3 5

    let id = checkedCheckboxes[0].value;

    document.getElementById("idForModify").value = id;
    document.getElementById("modifyForm").submit();
}