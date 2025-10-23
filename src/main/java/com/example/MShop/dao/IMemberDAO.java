package com.example.MShop.dao;

import com.example.MShop.vo.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IMemberDAO extends IDAO<String, Member>{
    /**
     * 判断给定的mid与给定的code是否相同
     * @param mid 要激活的用户id
     * @param code 设置好的激活码
     * @return
     */
    public boolean findByCode(String mid,String code) throws Exception;

    /**
     * 更新指定用户的状态操作
     * @param mid
     * @param status 0锁定，1激活，2等待激活
     * @return
     * @throws Exception
     */
    public boolean doUpdateStatus(String mid,Integer status) throws Exception;

    /**
     * 可以查询出照片信息，参数传递
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean findLogin(Member vo) throws Exception;

    /**
     * 根据用户状态进行数据列表操作
     * @param status
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public List<Member> findAllByStatus(Integer status, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;

    /**
     * 根据用户的状态统计所有的数据量
     * @param status
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Integer getAllCount(Integer status, String column, String keyWord) throws Exception;

    /**
     * 数据批量更新，状态由外部设置
     * @param ids
     * @param status
     * @return
     * @throws Exception
     */
    public boolean doUpdateStatus(Set<String> ids, Integer status) throws Exception;
    public boolean doUpdateMember(Member vo) throws Exception;

    public Member findById2(String mid) throws SQLException;
}
