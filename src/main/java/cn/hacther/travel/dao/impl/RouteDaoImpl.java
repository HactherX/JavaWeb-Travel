package cn.hacther.travel.dao.impl;

import cn.hacther.travel.dao.RouteDao;
import cn.hacther.travel.domain.Route;
import cn.hacther.travel.domain.RouteImg;
import cn.hacther.travel.domain.Seller;
import cn.hacther.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        List params = new ArrayList();  //条件们

        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1 = 1";
        StringBuilder sb = new StringBuilder(sql);

        if(cid > 0){
            sb.append(" AND cid = ?");
            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !rname.equals("null")){
            sb.append(" AND rname LIKE ?");
            params.add("%"+ rname +"%");
        }

        sql = sb.toString();

        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());

        return totalCount;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        List params = new ArrayList();

        String sql = "SELECT * FROM tab_route WHERE 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        if(cid > 0){
            sb.append(" AND cid = ?");
            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !rname.equals("null")){
            sb.append(" AND rname LIKE ?");
            params.add("%"+ rname +"%");
        }

        params.add(start);
        params.add(pageSize);

        sb.append(" LIMIT ? , ?");

        sql = sb.toString();

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());

        return list;
    }

    @Override
    public List<RouteImg> findImgs(int rid) {
        String sql= "SELECT * FROM tab_route_img WHERE rid = ?";

        List<RouteImg> imgs = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);

        return imgs;
    }

    @Override
    public Seller findSeller(int sid) {
        String sql= "SELECT * FROM tab_seller WHERE sid = ?";

        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);

        return seller;
    }

    @Override
    public Route findInfo(int rid) {
        String sql= "SELECT * FROM tab_route WHERE rid = ?";

        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);

        return route;
    }


}
