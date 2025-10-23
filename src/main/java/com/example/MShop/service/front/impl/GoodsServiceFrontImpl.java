package com.example.MShop.service.front.impl;

import com.example.MShop.dbc.DatabaseConnection;
import com.example.MShop.factory.DAOFactory;
import com.example.MShop.service.front.IGoodsServiceFront;
import com.example.MShop.vo.Goods;

import java.util.HashMap;
import java.util.Map;

public class GoodsServiceFrontImpl implements IGoodsServiceFront{
    private DatabaseConnection dbc = new DatabaseConnection();

    @Override
    public Goods show(int gid) throws Exception{
        try{
            Goods vo=DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findById(gid);
            if(vo!=null){
                vo.setItem(DAOFactory.getIItemDAOInstance(this.dbc.getConnection()).findById(vo.getItem().getIid()));
                DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).doUpdateBow(gid);
            }
            return vo;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception{
        try{
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("allItems",DAOFactory.getIItemDAOInstance(this.dbc.getConnection()).findAll());
            map.put("allGoods",DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findAllByStatus(1,currentPage,lineSize,column,keyWord));
            map.put("goodsCount",DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).getAllCountByStatus(1,column,keyWord));
            return map;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public Map<String,Object> listByItem(int iid,int currentPage,int lineSize,String column,String keyWord) throws Exception{
        try{
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("allItems",DAOFactory.getIItemDAOInstance(this.dbc.getConnection()).findAll());
            map.put("allGoods",DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findAllByItem(iid,1,currentPage,lineSize,column,keyWord));
            map.put("goodsCount",DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).getAllCountByItem(iid,1,column,keyWord));
            return map;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }
}
