function search() {
    var name = document.getElementById("name").value;
    if(name !=""){
        $.ajax({
            type:"GET",
            url:"/teacher/search/searchName",
            contentType: "application/json;charset=UTF-8;",
            data:JSON.stringify({"name":name}),
            success:function (data) {
                console.log(data);
            }
        })
    }
}
