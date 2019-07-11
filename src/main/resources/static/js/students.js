var students = [];
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

function addPaper() {
    $("#add").show();
    $("#papers").hide();
}

function addQuestion() {
    var question_id = $("#add").find(".question_id")[0].value;
    var score = $("#add").find(".score")[0].value;
    if (question != "" && score != "") {
        var question = { question_id: question_id, score: parseInt(score) };
        questions.push(question);
        alert("成功添加题目");
        $("#questions_add").append($("<tr><td>" + question_id + "</td>" + "<td>" + score + "</td></tr>"));
        $("#add").find(".question_id")[0].value = "";
        $("#add").find(".score")[0].value = "";
    }
}

function submitAdd() {
    var title = $("#add").find(".title")[0].value;
    var duration = $("#add").find(".duration")[0].value;
    if (title != "" && duration != "") {
        var paper = JSON.stringify({ title: title, duration: parseInt(duration), questions: questions });
        console.log(paper);
        $.ajax({
            type: "POST",
            url: "../",
            contentType: "application/json;charset=UTF-8;",
            data: paper,
            success: function (data) {
                alert("新增试卷ID为" + data.test_paper_id);
                $("#add").find(".title")[0].value = "";
                $("#add").find(".duration")[0].value = "";
                $("#add").find(".question_id")[0].value = "";
                $("#add").find(".score")[0].value = "";
                questions=[];
                self.location.reload();
            }
        })
    }
    else {
        alert("标题及时长不能为空");
    }
}

function cancelAdd() {
    questions = [];
    $("#add").find(".title")[0].value = "";
    $("#add").find(".duration")[0].value = "";
    $("#add").find(".question_id")[0].value = "";
    $("#add").find(".score")[0].value = "";
    $("#add").hide();
    $("#papers").show();
}