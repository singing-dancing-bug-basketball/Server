function login() {
    var user=document.getElementById("user").value;
    var password=document.getElementById("password").value;
    if(user==""||password==""){
        alert("账号和密码不能为空");
    }
    else{
        var data = JSON.stringify({"id":user,"password":password});
        $.ajax({
                type: "POST",
                url: "/teacher/login",
                contentType: "application/json;charset=UTF-8;",
                data: data,
                success:function (data) {
                    if(data.status===200)
                        window.location.href="/teacher/index";
                    else{
                        alert("账号或密码有误，请检查后重新输入");
                    }
                },
                err:function () {
                    alert("err")
                }
            }
        )
    }
}