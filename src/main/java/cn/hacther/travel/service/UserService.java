package cn.hacther.travel.service;

import cn.hacther.travel.domain.User;

public interface UserService {
    public int register(User user) throws Exception;

    public boolean active(String code);

    public User login(String userName,String passWord);
}
