package cn.hacther.travel.dao.impl;

import cn.hacther.travel.dao.CategoryDao;
import cn.hacther.travel.domain.Category;
import cn.hacther.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM tab_category";

        List<Category> category = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

        return category;
    }
}
