package cn.hacther.travel.service.impl;

import cn.hacther.travel.dao.UserDao;
import cn.hacther.travel.dao.impl.UserDaoImpl;
import cn.hacther.travel.domain.User;
import cn.hacther.travel.service.UserService;
import cn.hacther.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    @Override
    public int register(User user) throws Exception {


        UserDao dao = new UserDaoImpl();
        if(dao.findByUserName(user.getUsername())){
            //设置激活状态以及设置激活码
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());

            return dao.save(user);
        }
        return 0;
    }

    @Override
    public boolean active(String code) {
        UserDao dao = new UserDaoImpl();
        User user = dao.findByCode(code);

        if(user != null){
            if(user.getStatus().equalsIgnoreCase("n")){
                int i = dao.updateStatus(user);
                if(i == 1){
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public User login(String userName, String passWord) {
        UserDao dao = new UserDaoImpl();
        User user = dao.findByKey(userName, passWord);

        if(user != null){
            return user;
        }
        return null;
    }
}
