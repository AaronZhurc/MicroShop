package com.example.MShop.service.back;

import com.example.MShop.vo.Member;

import java.util.Map;
import java.util.Set;

public interface IMemberServiceBack {
    /**
     * 全部用户分页数据显示
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 根据status
     * @param status
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Map<String,Object> listByStatus(int status,int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 更新为激活状态status=1
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean updateActive(Set<String> ids)throws Exception;

    /**
     * 更新为锁定状态status=0
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean updateLock(Set<String> ids)throws Exception;

    /**
     * 查看单人完整信息
     * @param id
     * @return
     * @throws Exception
     */
    public Member show(String id) throws Exception;
}
