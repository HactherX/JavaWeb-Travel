package cn.hacther.travel.dao;

import cn.hacther.travel.domain.Route;
import cn.hacther.travel.domain.RouteImg;
import cn.hacther.travel.domain.Seller;

import java.util.List;

/**
 * 线路Dao
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     */
    public List<Route> findByPage(int cid, int start,int pageSize, String rname);

    /**
     * 根据rid查询图片详情数据
     * @param rid
     * @return
     */
    public List<RouteImg> findImgs(int rid);

    /**
     * 根据rid查询商家详细信息
     * @param sid
     * @return
     */
    public Seller findSeller(int sid);

    /**
     * 根据rid查询商品信息
     * @param rid
     * @return
     */
    public Route findInfo(int rid);

}
