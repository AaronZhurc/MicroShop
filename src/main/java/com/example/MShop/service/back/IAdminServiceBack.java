package com.example.MShop.service.back;

import com.example.MShop.vo.Admin;

public interface IAdminServiceBack {
    /**
     * 管理员登录
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean login(Admin vo) throws Exception;

}
