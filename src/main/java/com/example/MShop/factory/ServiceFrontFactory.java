package com.example.MShop.factory;

import com.example.MShop.service.front.IGoodsServiceFront;
import com.example.MShop.service.front.IMemberServiceFront;
import com.example.MShop.service.front.IOrdersServiceFront;
import com.example.MShop.service.front.IShopcarServiceFront;
import com.example.MShop.service.front.impl.GoodsServiceFrontImpl;
import com.example.MShop.service.front.impl.MemberServiceFrontImpl;
import com.example.MShop.service.front.impl.OrdersServiceFrontImpl;
import com.example.MShop.service.front.impl.ShopcarServiceFrontImpl;

public class ServiceFrontFactory {
    public static IMemberServiceFront getIMemberServiceFrontInstance(){
        return new MemberServiceFrontImpl();
    }
    public static IGoodsServiceFront getIGoodsServiceFrontInstance() {
        return new GoodsServiceFrontImpl();
    }
    public static IShopcarServiceFront getIShopcarServiceFrontInstance() {
        return new ShopcarServiceFrontImpl();
    }
    public static IOrdersServiceFront getIOrdersServiceFrontInstance() {
        return new OrdersServiceFrontImpl();
    }
}
