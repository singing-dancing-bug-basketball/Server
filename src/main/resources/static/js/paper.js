var delete_questions=[];
var title;
var duration;
var test_papere_id=1;
var questions=[];
function initData(title,duration,test_paper_id){
    this.title=title;
    this.duration=duration;
    this.test_paper_id=test_paper_id;
}
function editPaper(){
    $(".edit").attr("disabled",false);
    $(".submit").show();
    $(".cancel").show();
    $("#edit").hide();
    $(".delete").show();
    $(".editScore").show();
    $(".add").show();

}

function deleteQuestion(question_id){
    delete_questions.push(JSON.stringify({question_id:question_id}));
    $("#"+question_id).hide();
}

function editScore(question_id){
    $("#"+question_id).find(".score").attr("disabled",false);
    $("#"+question_id).find(".editScore").hide();
    $("#"+question_id).find(".submitScore").show();
    $("#"+question_id).find(".cancelScore").show();
}

function submitScore(question_id){
    var score=$("#"+question_id).find(".score")[0].value;
    $("#"+question_id).find(".score").attr("disabled",true);
    $("#"+question_id).find(".editScore").show();
    $("#"+question_id).find(".submitScore").hide();
    $("#"+question_id).find(".cancelScore").hide();
    questions.push(JSON.stringify({question_id:question_id,score:parseInt(score)}));
}

function submitEdit(){
    title=$("#title")[0].value;
    duration=parseInt($("#duration")[0].value);
    var data=JSON.stringify({test_papere_id:test_papere_id,title:title,duration:duration,questions:questions,delete_questions:delete_questions});
    console.log(data)
    $.ajax({
        type: "PUT",
        url: "/teacher/test_paper",
        contentType: "application/json;charset=UTF-8;",
        data: data,
        success: function (data) {
            if (data.status === 200) {
                self.location.reload();
            }
        }
    })
}

function addQuestion(){
    $("#add").show();
}

function submitAdd(){
    var id=$("#add").find(".id")[0].value;
    var score=$("#add").find(".score")[0].value;
    questions.push(JSON.stringify({question_id:id,score:parseInt(score)}));
    $("#add").find(".id")[0].value=""
    $("#add").find(".score")[0].value=""
    $("#add").hide();
    $("#questions_add").append($("<tr><td>"+id+"</td>"+"<td>"+score+"</td></tr>"));
}

function cancelAdd(){
    $("#add").find(".id")[0].value=""
    $("#add").find(".score")[0].value=""
    $("#add").hide();
}
