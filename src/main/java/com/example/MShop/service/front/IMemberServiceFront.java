package com.example.MShop.service.front;

import com.example.MShop.vo.Member;

public interface IMemberServiceFront {
    /**
     * 注册
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean regist(Member vo) throws Exception;

    /**
     * 实现激活操作
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean active(Member vo) throws Exception;

    /**
     * 登录
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean login(Member vo) throws Exception;
    public Member updatePre(String mid) throws Exception;
    public boolean update(Member vo) throws Exception;
}
