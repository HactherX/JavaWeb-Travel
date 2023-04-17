package cn.hacther.travel.dao;

import cn.hacther.travel.domain.User;

public interface UserDao {
    public boolean findByUserName(String username);

    public User findByCode(String code);

    public int save(User user) throws Exception;

    public int updateStatus(User user);

    public User findByKey(String userName,String passWord);
}
