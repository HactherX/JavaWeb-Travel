package cn.hacther.travel.service.impl;

import cn.hacther.travel.dao.impl.CategoryDaoImpl;
import cn.hacther.travel.domain.Category;
import cn.hacther.travel.service.CategoryService;
import cn.hacther.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        List<Category> cg = null;
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> tuples;
        tuples = jedis.zrangeWithScores("category", 0, -1);

        if(tuples == null || tuples.size() == 0){
            CategoryDaoImpl dao = new CategoryDaoImpl();
            List<Category> all = dao.findAll();
            for (Category category : all) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
            tuples = jedis.zrangeWithScores("category", 0, -1);
        }

        cg = new ArrayList<Category>();
        for (Tuple tuple : tuples) {
            Category x = new Category();
            x.setCid((int) tuple.getScore());
            x.setCname(tuple.getElement());
            cg.add(x);
        }

        return cg;
    }
}
