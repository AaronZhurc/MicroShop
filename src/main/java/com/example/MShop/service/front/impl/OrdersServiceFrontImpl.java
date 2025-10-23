package com.example.MShop.service.front.impl;

import com.example.MShop.dbc.DatabaseConnection;
import com.example.MShop.exception.EmptyShopcarException;
import com.example.MShop.exception.UnCompleteMemberInfomrationException;
import com.example.MShop.exception.UnEnoughAmountException;
import com.example.MShop.factory.DAOFactory;
import com.example.MShop.service.front.IOrdersServiceFront;
import com.example.MShop.vo.Details;
import com.example.MShop.vo.Goods;
import com.example.MShop.vo.Member;
import com.example.MShop.vo.Orders;

import java.sql.SQLException;
import java.util.*;

public class OrdersServiceFrontImpl implements IOrdersServiceFront{
    private DatabaseConnection dbc=new DatabaseConnection();
    @Override
    public boolean insert(String mid) throws UnCompleteMemberInfomrationException, UnEnoughAmountException, EmptyShopcarException, SQLException{
        boolean flag=false;
        dbc.getConnection().setAutoCommit(false);
        try{
            Member member=DAOFactory.getIMemberDAOInstance(this.dbc.getConnection()).findById2(mid);
            if(member.getName()==null||member.getPhone()==null||member.getAddress()==null){
                throw new UnCompleteMemberInfomrationException("用户个人信息不完整");
            }
            Map<Integer,Integer> cars=DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).findAllByMember(mid);
            if(cars.size()==0){
                throw new EmptyShopcarException("购物车为空");
            }
            List<Goods> allGoods=DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findAllByGid(cars.keySet());
            Iterator<Goods> iterGoods=allGoods.iterator();
            double pay=0.0;
            while(iterGoods.hasNext()){
                Goods vo=iterGoods.next();
                if(vo.getAmount()-cars.get(vo.getGid())<0){
                    throw new UnEnoughAmountException("商品数量不足");
                }
                pay+=vo.getPrice()*cars.get(vo.getGid());
            }
            Orders orders=new Orders();
            orders.setMember(member);
            orders.setName(member.getName());
            orders.setPhone(member.getPhone());
            orders.setAddress(member.getAddress());
            orders.setCredate(new Date());
            orders.setPay(pay);
            flag=DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).doCreateOrders(orders);
            orders.setOid(DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).findLastInsertId());
            iterGoods=null;
            iterGoods=allGoods.iterator();
            List<Details> all=new ArrayList<Details>();
            while(iterGoods.hasNext()){
                Details vo=new Details();
                Goods goods=iterGoods.next();
                vo.setGoods(goods);
                vo.setOrders(orders);
                int amount=cars.get(goods.getGid());
                vo.setAmount(amount);
                vo.setTitle(goods.getTitle());
                vo.setPrice(goods.getPrice());
                all.add(vo);
                flag=DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).doUpdateByAmount(goods.getGid(),0-amount);
            }
            flag=DAOFactory.getIDetailsDAOInstance(this.dbc.getConnection()).doCreateBath(all);
            flag=DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doRemoveByMember(mid);
            dbc.getConnection().commit();
        }catch(SQLException e){
            dbc.getConnection().rollback();
            throw e;
        }finally{
            this.dbc.close();
        }
        return flag;
    }

    @Override
    public Map<String,Object> listByMember(String mid,int currentPage,int lineSize) throws Exception{
        try{
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("allOrders",DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).findAllByMember(mid,currentPage,lineSize));
            map.put("ordersCount",DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).getAllCountByMember(mid));
            return map;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public Orders show(String mid,int oid) throws Exception{
        try{
            Orders vo=DAOFactory.getIOrdersDAOInstance(this.dbc.getConnection()).findByIdAndMember(mid,oid);
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
