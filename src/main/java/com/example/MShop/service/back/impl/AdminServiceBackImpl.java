package com.example.MShop.service.back.impl;

import com.example.MShop.dbc.DatabaseConnection;
import com.example.MShop.factory.DAOFactory;
import com.example.MShop.service.back.IAdminServiceBack;
import com.example.MShop.vo.Admin;

public class AdminServiceBackImpl implements IAdminServiceBack {
    private DatabaseConnection dbc=new DatabaseConnection();
    @Override
    public boolean login(Admin vo) throws Exception {
        try{
            if(DAOFactory.getIAdminDAOInstance(this.dbc.getConnection()).findLogin(vo)){
                return DAOFactory.getIAdminDAOInstance(this.dbc.getConnection()).doUpdateLastDate(vo.getAid());
            }
            return false;
        }catch(Exception e){
            throw e;
        }finally{
            this.dbc.close();
        }
    }
}
