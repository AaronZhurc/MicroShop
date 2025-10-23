package com.example.MShop.service.front;

import com.example.MShop.vo.Goods;

import java.util.Map;

public interface IGoodsServiceFront{
    /**
     * 进行全部商品列表
     * @param currentPage
     * @param lineSize
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;
    public Map<String,Object> listByItem(int iid,int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 显示商品完整信息
     * @param gid
     * @return
     * @throws Exception
     */
    public Goods show(int gid) throws Exception;
}
