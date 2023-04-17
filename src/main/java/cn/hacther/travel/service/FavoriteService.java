package cn.hacther.travel.service;

public interface FavoriteService {
    public boolean isFavorite(int rid, int uid);

    public int favoritesQuery(int rid);

    public void favoriteSwitch(int rid, int uid, boolean flag);
}
