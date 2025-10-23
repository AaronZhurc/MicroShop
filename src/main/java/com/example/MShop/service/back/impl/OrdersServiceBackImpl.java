package com.example.MShop.service.back.impl;

import com.example.MShop.dbc.DatabaseConnection;
import com.example.MShop.factory.DAOFactory;
import com.example.MShop.service.back.IOrdersServiceBack;
import com.example.MShop.vo.Orders;
import util.dao.AbstractDAOImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class OrdersServiceBackImpl implements IOrdersServiceBack{
    private DatabaseConnection dbc=new DatabaseConnection();

    @Override
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception{
        try{
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("allOrders",DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).findAllSplit(currentPage,lineSize,column,keyWord));
            map.put("ordersCount",DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).getAllCount(column,keyWord));
            return map;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public Orders show(int oid) throws Exception{
        try{
            Orders vo=DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).findById(oid);
            if(vo!=null){
                vo.setAllDetails(DAOFactory.getIDetailsDAOInstance(this.dbc.getConnection()).findAllByOrders(oid));
            }
            return vo;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }
}
