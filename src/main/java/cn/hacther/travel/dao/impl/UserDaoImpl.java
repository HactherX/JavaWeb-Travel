package cn.hacther.travel.dao.impl;

import cn.hacther.travel.dao.UserDao;
import cn.hacther.travel.domain.User;
import cn.hacther.travel.util.JDBCUtils;
import cn.hacther.travel.util.Md5Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean findByUserName(String username) {
        String sql = "SELECT * FROM tab_user WHERE username = ?";

        try{
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        }catch (DataAccessException e){
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    public User findByCode(String code) {
        String sql = "SELECT * FROM tab_user WHERE code = ?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
            return user;
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int save(User user) throws Exception {
        String sql = "INSERT INTO tab_user VALUES(?,?,?,?,?,?,?,?,?,?)";
        return template.update(sql, null, user.getUsername(), Md5Util.encodeByMd5(user.getPassword()), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());

    }

    @Override
    public int updateStatus(User user) {
        String sql = "UPDATE tab_user SET status = 'Y' WHERE code = ?";
        return template.update(sql,user.getCode());
    }

    @Override
    public User findByKey(String userName, String passWord) {
        String sql = "SELECT * FROM tab_user WHERE username = ? AND password = ?";
        try{
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName, Md5Util.encodeByMd5(passWord));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
