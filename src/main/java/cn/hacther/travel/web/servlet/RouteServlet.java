package cn.hacther.travel.web.servlet;

import cn.hacther.travel.domain.PageBean;
import cn.hacther.travel.domain.Route;
import cn.hacther.travel.service.FavoriteService;
import cn.hacther.travel.service.RouteService;
import cn.hacther.travel.service.impl.FavouriteServiceImpl;
import cn.hacther.travel.service.impl.RouteServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RouteServlet", value = "/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();
    private FavoriteService service_x = new FavouriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        String rname = request.getParameter("rname");

        int cid = 0;                //类别id
        //处理参数 cidStr
        if(cidStr != null && cidStr.length() > 0){
            cid = Integer.parseInt(cidStr);
        }

        int pageSize = 0;           //每页显示条数，如果不传递，默认每页显示5条记录
        //处理参数 pageSizeStr
        if(pageSizeStr != null && pageSizeStr.length() > 0 && Integer.parseInt(pageSizeStr) > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        int currentPage = 0;        //当前页码，如果不传递，默认每页显示5条记录
        //处理参数 currentPageStr
        if(currentPageStr != null && currentPageStr.length() > 0 && Integer.parseInt(currentPageStr) > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        //调用service查询pageBean对象
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);

        //将pageBean对象序列化为json，返回
        writeValue(pb,response);
    }

    /**
     * 商品详细信息查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void detailQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");

        int rid = 0;
        if(ridStr != null && ridStr.length() > 0 && !ridStr.equals("null")){
            rid = Integer.parseInt(ridStr);
        }

        Route route = service.detailQuery(rid);

        writeValue(route,response);
    }

    /**
     * 旅游线路收藏查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        String uidStr = request.getParameter("uid");

        boolean flag = service_x.isFavorite(Integer.parseInt(ridStr), Integer.parseInt(uidStr));

        writeValue(flag,response);
    }

    /**
     * 收藏数查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoritesQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");

        int count = service_x.favoritesQuery(Integer.parseInt(ridStr));

        writeValue(count,response);
    }

    /**
     * 收藏、取消收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteSwitch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        String uidStr = request.getParameter("uid");
        String flagStr = request.getParameter("flag");

        service_x.favoriteSwitch(Integer.parseInt(ridStr),Integer.parseInt(uidStr),Boolean.parseBoolean(flagStr));
    }
}
