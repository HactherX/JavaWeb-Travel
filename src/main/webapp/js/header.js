$(function () {
    $.post("user/loginStatus",{},function (data) {
        if(data.flag){
            $(".login").css("visibility","visible");
            $(".login_out").css("visibility","hidden");
            $("#msg").text(data.data.username);
        }else {
            $(".login_out").css("visibility","visible");
            $(".login").css("visibility","hidden");
        }
    },"json");

    $.post("category/findAll",{},function (data) {
        let lis = '<li class="nav-active"><a href="index.html">首页</a></li>';

        for (let i = 0; i < data.length; i++) {
            lis += '<li><a href="route_list.html?cid='+ data[i].cid +'">' + data[i].cname + '</a></li>';
        }

        lis += '<li><a href="favoriterank.html">收藏排行榜</a></li>';

        $("#category").html(lis);
    },"json");

    //给搜索按钮绑定单击事件，获取搜索输入框内容
    $("#search-button").click(function () {
        let rname = $("#search-input").val();
        let cid = getParameter("cid");
        if(cid == null){
            cid = 0;
        }
        location.href="http://localhost:8080/travel/route_list.html?cid=" + cid + "&rname="+rname;
    });
});