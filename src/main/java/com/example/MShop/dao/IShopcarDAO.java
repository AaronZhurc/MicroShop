package com.example.MShop.dao;

import com.example.MShop.vo.Shopcar;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface IShopcarDAO extends IDAO<Integer,Shopcar>{
    /**
     * 数据重复增加时将已有数据自增
     * @param mid
     * @param gid
     * @return
     * @throws Exception
     */
    public boolean doUpdateAmount(String mid,Integer gid) throws SQLException;

    /**
     * 根据用户商品编号查询购物车信息
     * @param mid
     * @param gid
     * @return
     * @throws Exception
     */
    public Shopcar findByMemberAndGoods(String mid,Integer gid) throws SQLException;

    /**
     * 清空单一用户购物车
     * @param mid
     * @return
     * @throws Exception
     */
    public boolean doRemoveByMember(String mid) throws SQLException;

    /**
     * 批量保存新购物车数据
     * @param vos
     * @return
     * @throws Exception
     */
    public boolean doCreateBatch(Set<Shopcar> vos) throws SQLException;

    /**
     * 删除单一用户某一商品购物车
     * @param mid
     * @param gid
     * @return
     * @throws Exception
     */
    public boolean doRemoveByMemberAndGoods(String mid,Set<Integer> gid) throws SQLException;

    /**
     * 单一用户购买所有记录
     * @param mid
     * @return
     * @throws Exception
     */
    public Map<Integer,Integer> findAllByMember(String mid) throws SQLException;
}
