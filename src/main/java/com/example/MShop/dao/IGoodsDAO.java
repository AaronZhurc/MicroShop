package com.example.MShop.dao;

import com.example.MShop.vo.Goods;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IGoodsDAO extends IDAO<Integer, Goods>{
    public List<Goods> findAllByStatus(Integer status, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;
    public Integer getAllCountByStatus(Integer status,String column,String keyWord) throws Exception;
    public boolean doUpdateStatus(Set<Integer> id,Integer status) throws Exception;
    public Set<String> findAllByPhoto(Set<Integer> id) throws Exception;

    /**
     * 根据商品分类进行列表显示
     * @param iid
     * @param status
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public List<Goods> findAllByItem(Integer iid,Integer status,Integer currentPage,Integer lineSize,String column,String keyWord) throws Exception;
    public Integer getAllCountByItem(Integer iid,Integer status,String column,String keyWord) throws Exception;

    /**
     * 更新访问次数
     * @param id
     * @return
     * @throws Exception
     */
    public boolean doUpdateBow(Integer id) throws Exception;

    /**
     * 查询指定编号所有商品信息
     * @param ids
     * @return
     * @throws Exception
     */
    public List<Goods> findAllByGid(Set<Integer> ids) throws SQLException;

    /**
     * 商品库存量的变更
     * @param gid
     * @param num
     * @return
     * @throws Exception
     */
    public boolean doUpdateByAmount(Integer gid,Integer num) throws SQLException;
}
