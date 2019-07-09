$(function () {
    //var total_page=[[${total_page}]];
    //var page=[[${page}]];
    //var test_papers=[[${test_papers}]]
    var test_papers = [{"test_paper_id": 1, "title": "test","duration":20}];
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
});
