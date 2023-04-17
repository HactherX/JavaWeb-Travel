package cn.hacther.travel.service.impl;

import cn.hacther.travel.dao.RouteDao;
import cn.hacther.travel.dao.impl.RouteDaoImpl;
import cn.hacther.travel.domain.PageBean;
import cn.hacther.travel.domain.Route;
import cn.hacther.travel.domain.RouteImg;
import cn.hacther.travel.domain.Seller;
import cn.hacther.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private final RouteDao dao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = dao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        //设置数据集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = dao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);

        return pb;
    }

    @Override
    public Route detailQuery(int rid) {
        Route route = dao.findInfo(rid);
        List<RouteImg> imgs = dao.findImgs(rid);
        Seller seller = dao.findSeller(route.getSid());

        route.setRouteImgList(imgs);
        route.setSeller(seller);

        return route;
    }
}
