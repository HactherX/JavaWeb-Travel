$(function () {
    goImg();

    let rid = getParameter("rid");

    $.post("route/detailQuery",{"rid":rid},function (data) {
        $("#NavigationBar").html(data.rname);
        $("#GoodsTitle").html(data.rname);
        $("#price").html("¥" + data.price.toFixed(2));
        $("#seller_info").empty();
        $("<p>经营商家  ："+ data.seller.sname +"</p>").appendTo($("#seller_info"));
        $("<p>咨询电话  ："+ data.seller.consphone +"</p>").appendTo($("#seller_info"));
        $("<p>地址  ："+ data.seller.address +"</p>").appendTo($("#seller_info"));

        let img = '<img alt="" class="big_img" src="'+ data.routeImgList[0].bigPic +'">'
        $("#l_img").html(img);

        let a_last = '<a class="up_img up_img_disable"></a>';
        let a_next = '<a class="down_img down_img_disable" style="margin-bottom: 0;"></a>';
        let as = "";
        as += a_last;
        for (let i = 0; i < data.routeImgList.length; i++) {
            let a = "";
            if(i === 0){
                a = '<a title="" class="little_img cur_img" data-bigpic="'+ data.routeImgList[i].bigPic +'">\n' +
                    '                        <img src="'+ data.routeImgList[i].smallPic +'">\n' +
                    '                    </a>';
            }else if(i > 3){
                a = '<a title="" class="little_img" data-bigpic="'+ data.routeImgList[i].bigPic +'" style="display:none;">\n' +
                    '                        <img src="'+ data.routeImgList[i].smallPic +'">\n' +
                    '                    </a>';
            }else {
                a = '<a title="" class="little_img" data-bigpic="'+ data.routeImgList[i].bigPic +'">\n' +
                    '                        <img src="'+ data.routeImgList[i].smallPic +'">\n' +
                    '                    </a>';
            }
            as += a;
        }
        as += a_next;

        $("#s_imgs").html(as);

        goImg();
        //自动播放
        let timer = setInterval("auto_play("+ data.routeImgList.length +")", 3000);
    },"json");

    //判断用户是否登录并检查其是否收藏当前旅游线路
    $.post("user/loginStatus",{},function (data) {
        if(data.flag){
            isFavorite(rid,data.data.uid);
        }else {
            favoriteQuery(rid);
            $("#favoriteText").html("点击收藏")
            $("#favoriteButton").click(function () {
                alert("登录后才能收藏哦！");
            });
        }
    },"json");
});

//焦点图效果
//点击图片切换图片
function goImg() {
    $('.little_img').on('mousemove', function() {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click',function(){
        var num = $('.little_img').length;
        if((nextindex + 1) <= num){
            $('.little_img:eq('+picindex+')').hide();
            $('.little_img:eq('+nextindex+')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click',function(){
        var num = $('.little_img').length;
        if(picindex > 0){
            $('.little_img:eq('+(nextindex-1)+')').hide();
            $('.little_img:eq('+(picindex-1)+')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }

    });
}

//自动轮播方法
function auto_play(max) {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = max;
    if ((num - 1) < max) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}

//收藏数查询
function favoriteQuery(rid) {
    $.post("route/favoritesQuery",{"rid":rid},function (data) {
        $("#favorites").html("已被收藏"+ data +"次");
    },"json");
}

//收藏、取消收藏方法
function favoriteSwitch(favoriteStatus,rid,uid) {
    let favoriteButton = $("#favoriteButton");
    favoriteButton.click(function () {
        $.post("route/favoriteSwitch",{"rid":rid,"uid":uid,"flag":favoriteStatus});
        favoriteStatus = !favoriteStatus;
        if(favoriteStatus){
            $("#favoriteText").html("已收藏")
            favoriteButton.addClass("already");
            favoriteButton.attr("disabled","disabled");
        }else {
            $("#favoriteText").html("点击收藏")
            favoriteButton.removeClass("already");
            favoriteButton.removeAttr("disabled");
        }
        setTimeout("favoriteQuery("+rid+")",100);
    });
}

//收藏状态方法
function isFavorite(rid, uid) {
    let favoriteButton = $("#favoriteButton");
    favoriteQuery(rid);
    $.post("route/isFavorite",{"rid":rid, "uid":uid},function (data) {
        if(data){
            $("#favoriteText").html("已收藏")
            favoriteButton.addClass("already");
            favoriteButton.attr("disabled","disabled");
        }else {
            $("#favoriteText").html("点击收藏")
            favoriteButton.removeClass("already");
            favoriteButton.removeAttr("disabled");
        }
        favoriteSwitch(data,rid,uid);
    },"json");
}