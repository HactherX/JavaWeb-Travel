$(function () {
    let rname = getParameter("rname");
    if(rname != null){
        rname = window.decodeURIComponent(rname);
    }
    let currentPage = getParameter("currentPage");
    let cid = getParameter("cid");
    if(currentPage == null){
        currentPage = 1;
    }

    load(Number.parseInt(cid),Number.parseInt(currentPage),rname);
})

function load(cid, currentPage, rname) {
    //发送ajax请求
    $.post("route/pageQuery",{"cid":cid,"currentPage":currentPage,"rname":rname},function (data){
        //解析pageBean数据，将其展示到页面上
        //分页工具条数据展示
        //展示总页码和总记录数
        $("#totalPage").html(data.totalPage);
        $("#totalCount").html(data.totalCount);
        //展示分页页码
        let lis = "";
        let next = Number.parseInt(currentPage) + 1;
        if(next >= data.totalPage){
            next = data.totalPage;
        }
        let last = Number.parseInt(currentPage) - 1;
        if(last <= 0){
            last = 1;
        }
        let firstPage = '<li onclick="load('+ cid +',1,'+ "'" + rname + "'"  +')"><a href="javascript:void(0)">首页</a></li>';
        let lastPage = '<li class="threeword" onclick="load('+ cid +','+ last +','+ "'" + rname + "'"  +')"><a href="javascript:void(0)">上一页</a></li>';
        let endPage = '<li class="threeword" onclick="load('+ cid +','+ data.totalPage +','+ "'" + rname + "'"  +')"><a href="javascript:void(0)">末页</a></li>';
        let nextPage = '<li class="threeword" onclick="load('+ cid +','+ next +','+ "'" + rname + "'"  +')"><a href="javascript:void(0)">下一页</a></li>';
        lis += firstPage;
        lis += lastPage;

        let begin;
        let end;

        if(data.totalPage < 10){
            begin = 1;
            end = data.totalPage;
        }else {
            if(currentPage >= 7){
                begin = currentPage - 5;
                if(currentPage + 4 > data.totalPage){
                    end = data.totalPage;
                }else {
                    end = currentPage + 4;
                }
            }else {
                begin = currentPage - (currentPage - 1);
                end = currentPage + (10 - currentPage);
            }
        }

        for (let i = begin; i <= end; i++) {
            let li;
            if(i === currentPage){
                li = '<li class="curPage" onclick="load('+ cid +','+ i +','+ "'" + rname + "'"  +')"><a href="javascript:void(0)">'+ i +'</a></li>';
            }else {
                li = '<li onclick="load('+ cid +','+ i +','+ "'" + rname + "'"  +')"><a href="javascript:void(0)">'+ i +'</a></li>';
            }

            lis += li;
        }

        lis += nextPage;
        lis += endPage;
        $("#pageNum").html(lis);
        //列表展示数据
        let route_lis = "";
        for (let i = 0; i < data.list.length; i++) {
            let route_li = '<li>\n' +
                '                            <div class="img"><img width="300px" src="'+ data.list[i].rimage +'" alt=""></div>\n' +
                '                            <div class="text1">\n' +
                '                                <p>'+ data.list[i].rname +'</p>\n' +
                '                                <br/>\n' +
                '                                <p>'+ data.list[i].routeIntroduce.split("，")[0] +'</p>\n' +
                '                            </div>\n' +
                '                            <div class="price">\n' +
                '                                <p class="price_num">\n' +
                '                                    <span>&yen;</span>\n' +
                '                                    <span>'+ data.list[i].price +'</span>\n' +
                '                                    <span>起</span>\n' +
                '                                </p>\n' +
                '                                <p><a href="route_detail.html?rid='+ data.list[i].rid +'">查看详情</a></p>\n' +
                '                            </div>\n' +
                '                        </li>';
            route_lis += route_li;
        }
        $("#pageInfo").html(route_lis);

        window.scrollTo(0,0);

    },"json");
}