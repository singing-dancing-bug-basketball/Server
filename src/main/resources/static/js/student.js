var student_id,name,password;
function initData(student_id,name,password){
    this.student_id=student_id;
    this.name=name;
    this.password=password;
}
function editStudent(){
    $(".edit").attr("disabled",false);
    $(".edit").attr("style","border:1px solid #000000");
    $(".submit").show();
    $(".cancel").show();
    $("#edit").hide();
    $("#remove").hide();
    $(".delete").show();
    $(".add").show();
    $(".cancel").show();
    $(".pass").show();

}

function cancelEdit(){
    $(".edit").attr("disabled",true);
    $(".edit").attr("style","border:hidden;background:0%");
    $(".submit").hide();
    $(".cancel").hide();
    $("#edit").show();
    $("#remove").show();
    $(".delete").hide();
    $(".add").hide();
    $(".cancel").hide();
    $("#name")[0].value=name;
    $("#password")[0].value="";
    $(".pass").hide();
}

function removeStudent(){
    var f = confirm("是否确定删除该学生信息");
    if (f) {
        $.ajax({
            type: "DELETE",
            url: "/teacher/student/",
            contentType: "application/json;charset=UTF-8;",
            data: JSON.stringify({student_id:student_id}),
            success: function (data) {
                if (data.status === 200) {
                    history.back();
                    self.location=document.referrer;
                }
            }
        })
    }
}

function submitStudent(){
    name=$("#name")[0].value;
    password=$("#password")[0].value;

    var data=JSON.stringify({student_id:student_id,name:name,password:password});
    console.log(data);
    $.ajax({
        type: "PUT",
        url: "/teacher/student/",
        contentType: "application/json;charset=UTF-8;",
        data: data,
        success: function (data) {
            if (data.status === 200) {
                self.location.reload();
            }
        }
    })
}
function back() {
    history.back();
    self.location=document.referrer;
}