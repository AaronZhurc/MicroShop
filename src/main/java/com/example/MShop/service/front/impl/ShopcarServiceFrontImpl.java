package com.example.MShop.service.front.impl;

import com.example.MShop.dbc.DatabaseConnection;
import com.example.MShop.factory.DAOFactory;
import com.example.MShop.service.front.IShopcarServiceFront;
import com.example.MShop.vo.Goods;
import com.example.MShop.vo.Shopcar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopcarServiceFrontImpl implements IShopcarServiceFront{
    private DatabaseConnection dbc=new DatabaseConnection();

    @Override
    public boolean addCar(Shopcar vo) throws Exception{
        try{
            if(DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).findByMemberAndGoods(vo.getMember().getMid(),vo.getGoods().getGid())!=null){
                return DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doUpdateAmount(vo.getMember().getMid(),vo.getGoods().getGid());
            }else{
                vo.setAmount(1);
                return DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doCreate(vo);
            }
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public Map<String,Object> listCar(String mid) throws Exception{
        try{
            Map<String,Object> map=new HashMap<String,Object>();
            Map<Integer,Integer> cars=DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).findAllByMember(mid);
            map.put("allShopcars",cars);
            map.put("allGoods",DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findAllByGid(cars.keySet()));
            return map;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public boolean deleteCar(String mid,Set<Integer> gid) throws Exception{
        try{
            return DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doRemoveByMemberAndGoods(mid,gid);
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }

    @Override
    public boolean update(String mid,Set<Shopcar> vos) throws Exception{
        try{
            if(vos.size()==0){
                return false;
            }
            if(DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doRemoveByMember(mid)){
                return DAOFactory.getIShopcarDAOInstance(this.dbc.getConnection()).doCreateBatch(vos);
            }
            return false;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }
    /*@Override
    public List<Goods> listCar(Set<Integer> ids) throws Exception{
        try{
            return DAOFactory.getIGoodsDAOInstance(this.dbc.getConnection()).findAllByGid(ids);
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }*/
}
