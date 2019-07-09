//var title=[[${title}]];
//var duration=[[${duration}]];
//var test_paper_id=[[${test_paper_id}]];
//var questions=[[${questions}]];
var delete_questions=[];
function editPaper(){
    $(".edit").attr("disabled",false);
    $(".submit").show();
    $(".cancel").show();
    $("#edit").hide();
    $(".delete").show();
    $(".editScore").show();
}

function deleteQuestion(question_id){
    delete_questions.push(question_id);
    $("#"+question_id).hide();
}

function editScore(question_id){
    console.log(question_id)
    $("#"+question_id).find(".score").attr("disabled",false);
    $("#"+question_id).find(".editScore").hide();
    
}

