package com.example.MShop.service.back;

import com.example.MShop.vo.Goods;

import java.util.Map;
import java.util.Set;

public interface IGoodsServiceBack {
    /**
     * 商品增加前数据查询
     * @return
     * @throws Exception
     */
    public Map<String,Object> insertPre() throws Exception;

    /**
     * 商品增加
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insert(Goods vo) throws Exception;

    /**
     * 基础列表操作
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;
    public Map<String,Object> listStatus(int status,int currentPage,int lineSize,String column,String keyWord) throws Exception;
    public boolean updateUp(Set<Integer> gid) throws Exception;
    public boolean updateDown(Set<Integer> gid) throws Exception;
    public boolean updateDelete(Set<Integer> gid) throws Exception;

    /**
     * 商品修改前的数据查询
     * @param gid
     * @return
     * @throws Exception
     */
    public Map<String,Object> updatePre(int gid) throws Exception;
    public boolean update(Goods vo) throws Exception;

    /**
     * 批量删除，清空图片信息
     * @param ids
     * @return
     * @throws Exception
     */
    public Map<String,Object> deleteAll(Set<Integer> ids) throws Exception;
    public boolean delete(Set<Integer> id) throws Exception;
}
