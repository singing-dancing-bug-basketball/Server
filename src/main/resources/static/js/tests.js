function initData(total_page, page) {
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
        if (i === page) {
            li.attr("class", "active");
        } else {
            href.attr("href", i);
        }
        $("#last").before(li);
    }
}
function addTest() {
    $("#add").show();
    $("#tests").hide();
}
function submitAdd() {
    var title = $("#add").find(".title")[0].value;
    var paper_id = $("#add").find(".paper_id")[0].value;
    var start_time=$("#add").find(".start_time")[0].value;
    var end_time=$("#add").find(".end_time")[0].value;
    if (title != "" && paper_id != "") {
        if(start_time.match(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/)!=null){
            var test=JSON.stringify({test_paper_id:paper_id,title:title,start_time:start_time,end_time:end_time});
            $.ajax({
                type: "POST",
                url: "../",
                contentType: "application/json;charset=UTF-8;",
                data: test,
                success: function (data) {
                    if(data.status===200){
                        alert("该测试ID为：" + data.test_id);
                        $("#add").find(".title")[0].value = "";
                        $("#add").find(".paper_id")[0].value = "";
                        $("#add").find(".start_time")[0].value = "";
                        $("#add").find(".end_time")[0].value = "";
                        questions=[];
                        self.location.reload();
                    }
                    
                }
            })
        }
        else{
            alert("日期格式错误");
        }
    }
    else {
        alert("标题及时长不能为空");
    }
}

function cancelAdd() {
    questions = [];
    $("#add").find(".title")[0].value = "";
    $("#add").find(".paper_id")[0].value = "";
    $("#add").find(".start_time")[0].value = "";
    $("#add").find(".end_time")[0].value = "";
    $("#add").hide();
    $("#tests").show();
}