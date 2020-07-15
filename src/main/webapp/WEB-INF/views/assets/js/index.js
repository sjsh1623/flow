function addTodoItem() {
}

function deleteTodoItem(e, item) {
    e.preventDefault();
    $(item).parent().fadeOut('fast', function () {
        $(item).parent().parent().remove();
    });
}


function completeTodoItem() {
    $(this).parent().toggleClass("strike");
}


$(function () {

    $("#add-todo-item").on('click', function (e) {
        e.preventDefault();
        addTodoItem()
    });

    $("#card").on('click', '.deleteX', function (e) {
        var item = this;
        deleteTodoItem(e, item)
    })
});


function preExistCheck() {
    var cnt = 0; // 예외처리를 위한 변수
    var checkArray = new Array();

    $('input:checkbox[name="preexist"]').each(function () {
        if (this.checked) {
            checkArray[cnt] = this.value;
            cnt++;
        }
    });
    var preexist = JSON.stringify(checkArray);
    var preexistTrimL = preexist.replace('[', '');
    var result = preexistTrimL.replace(']', '');
    return result;
}

function additionalCheck() {
    var element = $("#new-todo-item").val();
    if(element == "") {
        return;
    }
    $("#card").append("<div class=\"con\"><div class=\"uk-card uk-card-default uk-card-small uk-card-body displayGenreDel\"><button class=\"uk-close deleteX\" type=\"button\" uk-close></button>" + element + "<input type=\"hidden\" name=\"additional\" value=\"" + element + "\"></div></div>");

    $("#new-todo-item").val("");
    //배열 생성
    var addArray = new Array();

    var len = $('input[name="additional"]').length;
    //배열에 값 주입
    for (var i = 0; i < len; i++) {
        addArray[i] = $('input[name="additional"]').eq(i).val();
    }
    var additional = JSON.stringify(addArray);
    var additionalTrimL = additional.replace('[', '');
    var result = additionalTrimL.replace(']', '');

    return result;
}