package cn.hacther.travel.dao.impl;

import cn.hacther.travel.dao.FavoriteDao;
import cn.hacther.travel.domain.Favorite;
import cn.hacther.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean isFavorite(int rid, int uid) {
        String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?";
        try{
            template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public int favoritesQuery(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }

    @Override
    public void favoriteOn(int rid, int uid) {
        String sql = "INSERT INTO tab_favorite VALUES(?,?,?)";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        template.update(sql, rid, sdf.format(date), uid);
    }

    @Override
    public void favoriteOff(int rid, int uid) {
        String sql = "DELETE FROM tab_favorite WHERE rid = ? AND uid = ?";
        template.update(sql,rid,uid);

    }
}
