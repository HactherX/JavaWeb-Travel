package cn.hacther.travel.dao;

public interface FavoriteDao {
    public boolean isFavorite(int rid, int uid);

    public int favoritesQuery(int rid);

    public void favoriteOn(int rid, int uid);

    public void favoriteOff(int rid, int uid);
}
