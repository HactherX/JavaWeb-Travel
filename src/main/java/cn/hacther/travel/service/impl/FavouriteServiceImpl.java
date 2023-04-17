package cn.hacther.travel.service.impl;

import cn.hacther.travel.dao.FavoriteDao;
import cn.hacther.travel.dao.impl.FavoriteDaoImpl;
import cn.hacther.travel.service.FavoriteService;

public class FavouriteServiceImpl implements FavoriteService {
    private FavoriteDao dao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(int rid, int uid) {
        boolean flag = dao.isFavorite(rid, uid);
        return flag;
    }

    @Override
    public int favoritesQuery(int rid) {
        int countX = dao.favoritesQuery(rid);
        return countX;
    }

    @Override
    public void favoriteSwitch(int rid, int uid, boolean flag) {
        if(flag){
            dao.favoriteOff(rid, uid);
        }else {
            dao.favoriteOn(rid, uid);
        }
    }
}
