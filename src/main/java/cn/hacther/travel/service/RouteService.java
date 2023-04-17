package cn.hacther.travel.service;

import cn.hacther.travel.domain.PageBean;
import cn.hacther.travel.domain.Route;
import cn.hacther.travel.domain.RouteImg;
import cn.hacther.travel.domain.Seller;

/**
 * 线路Service
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询商品信息并完整封装Route对象
     * @param rid
     * @return
     */
    public Route detailQuery(int rid);
}
