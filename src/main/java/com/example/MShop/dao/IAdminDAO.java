package com.example.MShop.dao;

import com.example.MShop.vo.Admin;

public interface IAdminDAO extends IDAO<String, Admin>{
    /**
     * 管理员登录，取出上一次登录时间
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean findLogin(Admin vo) throws Exception;

    /**
     * 本操作是更新最后一次的登陆日期
     * @param aid
     * @return
     * @throws Exception
     */
    public boolean doUpdateLastDate(String aid) throws Exception;

}
