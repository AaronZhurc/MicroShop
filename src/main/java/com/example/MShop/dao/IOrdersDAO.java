package com.example.MShop.dao;

import com.example.MShop.vo.Orders;

import java.sql.SQLException;
import java.util.List;

public interface IOrdersDAO extends IDAO<Integer,Orders>{
    /**
     * 去的当前增长后的订单编号
     * @return
     * @throws Exception
     */
    public Integer findLastInsertId() throws SQLException;
    public boolean doCreateOrders(Orders vo) throws SQLException;

    /**
     * 根据用户编号列出订单信息
     * @param mid
     * @param currentPage
     * @param lineSize
     * @return
     * @throws Exception
     */
    public List<Orders> findAllByMember(String mid,Integer currentPage,Integer lineSize) throws Exception;
    public Integer getAllCountByMember(String mid) throws Exception;

    /**
     * 查询一个用户的一个订单信息
     * @param mid
     * @param oid
     * @return
     * @throws Exception
     */
    public Orders findByIdAndMember(String mid,Integer oid) throws Exception;
}
