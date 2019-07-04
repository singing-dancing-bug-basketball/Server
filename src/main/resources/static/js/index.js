function search() {
    var name = document.getElementById("name").value;
    if(name !=""){
        $.ajax({
            type:"GET",
            url:"http://localhost/index/studentName",
            contentType: "application/json;charset=UTF-8;",
            data:JSON.stringify({"name":name}),
            success:function (data) {
                console.log(data);
            }
        })
    }
}
