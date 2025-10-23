package com.example.MShop.service.front;

import com.example.MShop.vo.Goods;
import com.example.MShop.vo.Shopcar;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IShopcarServiceFront {
    /**
     * 购物车数据增加
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean addCar(Shopcar vo) throws Exception;

    /**
     * 通过当前的用户id查询出所有的购买商品
     * @param mid
     * @return
     * @throws Exception
     */
    public Map<String,Object> listCar(String mid) throws Exception;
    //public List<Goods> listCar(Set<Integer> ids) throws Exception;

    /**
     * 删除掉一个指定用户和商品的信息
     * @param mid
     * @param gid
     * @return
     * @throws Exception
     */
    public boolean deleteCar(String mid,Set<Integer> gid) throws Exception;

    /**
     * 更新购买所有的商品信息
     * @param mid
     * @param vos
     * @return
     * @throws Exception
     */
    public boolean update(String mid,Set<Shopcar> vos) throws Exception;
}
