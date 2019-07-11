var test_title,test_paper_title,duration,start_time,end_time,test_paper_id;
var test_id;
function initData(test_id,test_title,test_paper_title,duration,start_time,end_time,test_paper_id){
    this.test_title=test_title;
    this.test_paper_title=test_paper_title;
    this.duration=duration;
    this.start_time=start_time;
    this.end_time=end_time;
    this.test_paper_id=test_paper_id;
    this.test_id=test_id;
}
function editTest(){
    $(".edit").attr("disabled",false);
    $(".submit").show();
    $(".cancel").show();
    $("#edit").hide();
    $("#remove").hide();
    $(".delete").show();
    $(".editScore").show();
    $(".add").show();
    $(".cancel").show();

}

function cancelEdit(){
    $(".edit").attr("disabled",true);
    $(".submit").hide();
    $(".cancel").hide();
    $("#edit").show();
    $("#remove").show();
    $(".delete").hide();
    $(".editScore").hide();
    $(".add").hide();
    $(".cancel").hide();
    $("#title")[0].value=test_title
    $("#test_paper_id")[0].value=test_paper_id;
    $("#duration")[0].value=duration;
    $("#start_time")[0].value=start_time;
    $("#end_time")[0].value=end_time;
}

function removeTest(){
    var f = confirm("是否确定删除该测试");
    if (f) {
        $.ajax({
            type: "DELETE",
            url: "/teacher/test",
            contentType: "application/json;charset=UTF-8;",
            data: test_id,
            success: function (data) {
                if (data.status === 200) {
                    history.back();
                    self.location=document.referrer;
                }
            }
        })
    }
}

function submitEdit(){
    title=$("#title")[0].value;
    test_paper_id=$("#test_paper_id")[0].value;
    duration=parseInt($("#duration")[0].value);
    start_time=$("#start_time")[0].value;
    end_time=$("#end_time")[0].value;

    var data=JSON.stringify({test_id:test_id,test_paper_id:test_paper_id,title:title,start_time:start_time,end_time:end_time});
    console.log(data);
    $.ajax({
        type: "PUT",
        url: "/teacher/test/",
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