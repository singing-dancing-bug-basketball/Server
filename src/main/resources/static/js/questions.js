function initData(total_page,page,questions){
    var questions = [{ "question_id": 1, "answer": 2 }];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        $("#" + question.question_id).children(".answer").find(".checked_" + question.answer).attr("checked", "true");
    }
    var total_page = 10;
    var page = 5;
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
        if (i == page) {
            li.attr("class", "active");
        } else {
            href.attr("href", i);
        }
        $("#last").before(li);
    }
}

function editQuestion(question_id) {
    var fatherNode = $("#" + question_id);
    fatherNode.find(".content").attr("readonly", false);
    fatherNode.find(".selections").attr("readonly", false);
    fatherNode.find(".check").attr("disabled", false);
    fatherNode.find(".edit").hide();
    fatherNode.find(".delete").hide();
    fatherNode.find(".submit").show();
}

function deleteQuestion(question_id) {
    var f = confirm("是否确定删除该问题");
    if (f) {
        send("../", JSON.stringify({ "question_id": question_id }), "DELETE", function () {
            self.location.reload();
        });
    }
}

function submitQuestion(question_id) {
    var father = $("#" + question_id);
    var stem = father.find(".content")[0].value;
    var pattern = /\d/;
    var answer = Number(father.find("input[name='answer']:checked")[0].className.match(pattern)[0]);
    var selections = father.find(".selections");
    var selectionJson = {};
    for (var i = 0; i < selections.length; i++) {
        var index = i + 1;
        selectionJson[index] = selections[i].value;
    }
    var question = { "question_id": question_id, "stem": stem, "selections": selectionJson, answer: answer };
    send("../", JSON.stringify(question), "PUT", function () {
        alert("成功修改题目");
        father.find(".content").attr("readonly", true);
        father.find(".selections").attr("readonly", true);
        father.find(".check").attr("disabled", true);
        father.find(".edit").show();
        father.find(".delete").show();
        father.find(".submit").hide();
    });
}

function send(url, data, method, callback) {
    $.ajax({
        type: method,
        url: url,
        contentType: "application/json;charset=UTF-8;",
        data: data,
        success: function (data) {
            if (data.status === 200) {
                callback();
            }
        }
    })
}

function addQuestion() {
    document.getElementById("add").style.display = "block";
}

function submitAdd() {
    var father = $("#add");
    var stem = father.find(".content")[0].value;
    var pattern = /\d/;
    var answer = Number(father.find("input[name='answer']:checked")[0].className.match(pattern)[0]);
    var selections = father.find(".selections");
    var selectionJson = {};
    for (var i = 0; i < selections.length; i++) {
        var index = i + 1;
        selectionJson[index] = selections[i].value;
    }
    var question = { "stem": stem, "selections": selectionJson, answer: answer };
    send("../", JSON.stringify(question), "POST", function () {
        document.getElementById("add").style.display = "none";
        self.location.reload();
    })
}

function cancelAdd() {
    document.getElementById("add").style.display = "none";
}