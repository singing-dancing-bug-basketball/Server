$(function () {
    //var total_page=[[${total_page}]];
    //var page=[[${page}]];
    //var questions=[[${questions}]]
    var questions = [{"question_id": 1, "answer": 2}];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        $("#" + question.question_id).children(".answer").find(".checked_" + question.answer).attr("checked", "true");
    }
});

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
        send("",JSON.stringify({"question_id": question_id}),"DELETE",function () {
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
    var selectionJson={};
    for(var i=0;i<selections.length;i++){
        var index=i+1;
        selectionJson[index]=selections[i].value;
    }
    var question={"question_id":question_id,"stem":stem,"selections":selectionJson,answer:answer};
    send("",JSON.stringify(question),"PUT",function () {
        alert("成功修改题目");
        father.find(".content").attr("readonly", true);
        father.find(".selections").attr("readonly", true);
        father.find(".check").attr("disabled", true);
        father.find(".edit").show();
        father.find(".delete").show();
        father.find(".submit").hide();
    });
}

function send(url,data,method,callback){
    $.ajax({
        type: method,
        url: url,
        contentType: "application/json;charset=UTF-8;",
        data: method,
        success: function (data) {
            if (data.status === 200) {
                callback();
            }
        }
    })
}
