function initData(total_page, page) {
    var begin;
    var end;
    $("#before").attr("href", 1);
    $("#after").attr("href", total_page);
    if (total_page <= 5) {
        begin = 1;
        end = total_page;
    } else {
        if (page <= 3) {
            begin = 1;
            end = 5;
        } else if (page >= total_page - 2) {
            begin = total_page - 4;
            end = total_page;
        } else {
            begin = page - 2;
            end = page + 2;
        }
    }
    for (var i = begin; i <= end; i++) {
        var href = $("<a></a>").text(i);
        href.attr("id", "page_" + i);
        var li = $("<li></li>").html(href);
        if (i === page) {
            li.attr("class", "active");
        } else {
            href.attr("href", i);
        }
        $("#last").before(li);
    }
}

function addStudents() {
    $("#add").show();
    $("#students").hide();
}

function addStudent() {
    var student_id = $("#add").find(".student_id")[0].value;
    var name = $("#add").find(".name")[0].value;
    var password = $("#add").find(".password")[0].value;
    if (student_id != "" && name != "" && password != "") {
        var student = { student_id: student_id, name: name, password: password };
        $.ajax({
            type: "POST",
            url: "../",
            contentType: "application/json;charset=UTF-8;",
            data: JSON.stringify(student),
            success: function (data) {
                alert("成功添加学生"+name);
                $("#add").find(".student_id")[0].value = "";
                $("#add").find(".name")[0].value = "";
                $("#add").find(".password")[0].value = "";
            }
        });
    }
}

function cancelAdd() {
    students = [];
    $("#add").find(".student_id")[0].value = "";
    $("#add").find(".name")[0].value = "";
    $("#add").find(".password")[0].value = "";
    $("#add").hide();
    $("#students").show();
    self.location.reload();
}