package com.example.MShop.service.front;

import com.example.MShop.exception.EmptyShopcarException;
import com.example.MShop.exception.UnCompleteMemberInfomrationException;
import com.example.MShop.exception.UnEnoughAmountException;
import com.example.MShop.vo.Orders;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOrdersServiceFront{
    /**
     * 创建订单操作
     * @param mid
     * @return
     * @throws UnCompleteMemberInfomrationException 个人信息不完整
     * @throws UnEnoughAmountException 没有足够库存量
     * @throws EmptyShopcarException 购物车为空
     * @throws SQLException
     */
    public boolean insert(String mid) throws UnCompleteMemberInfomrationException, UnEnoughAmountException, EmptyShopcarException, SQLException;

    /**
     * 查看一个用户所有订单信息
     * @param mid
     * @return
     * @throws Exception
     */
    public Map<String,Object> listByMember(String mid,int currentPage,int lineSize) throws Exception;

    /**
     * 查看一个订单信息及所对应详情
     * @param mid
     * @param oid
     * @return
     * @throws Exception
     */
    public Orders show(String mid,int oid) throws Exception;
}
