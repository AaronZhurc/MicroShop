package com.example.MShop.servlet.front;

import com.example.MShop.factory.ServiceFrontFactory;
import com.example.MShop.util.ShopcarCookieUtil;
import com.example.MShop.vo.Goods;
import com.example.MShop.vo.Member;
import com.example.MShop.vo.Shopcar;
import util.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ShopcarServletFront",urlPatterns = "/pages/front/shopcar/ShopcarServletFront/*")
public class ShopcarServletFront extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String path="/pages/errors.jsp";
        String status=request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
        if(status!=null){
            if("insert".equals(status)){
                path=this.insert(request,response);
            }else if("list".equals(status)){
                path=this.list(request);
            }else if("update".equals(status)){
                path=this.update(request,response);
            }else if("delete".equals(status)){
                path=this.delete(request,response);
            }
        }
        request.getRequestDispatcher(path).forward(request,response);
    }
    public String insert(HttpServletRequest request,HttpServletResponse response) {
        //session
        /*cookie*/
        //Map<Integer,Integer> map=null;
        /*Map<Integer,Integer> map=ShopcarCookieUtil.loadCar(request);
        if(request.getSession().getAttribute("allCars")!=null){
            map=(Map<Integer,Integer>)request.getSession().getAttribute("allCars");
        }else{
            map=new HashMap<Integer,Integer>();
        }*/
        int gid=Integer.parseInt(request.getParameter("gid"));
        String mid=(String)request.getSession().getAttribute("mid");
        Shopcar vo = new Shopcar();
        Member member = new Member();
        member.setMid(mid);
        vo.setMember(member);
        Goods goods = new Goods();
        goods.setGid(gid);
        vo.setGoods(goods);
        /*int count=1;
        if(map.containsKey(gid)){
            count=map.get(gid)+1;
        }*/
        //map.put(gid,count);
        //request.getSession().setAttribute("allCars",map);
        /*ShopcarCookieUtil.addCar(request,response,gid,count);
        String msg="购物车添加成功";*/
        String msg=null;
        try{
            if(ServiceFrontFactory.getIShopcarServiceFrontInstance().addCar(vo)){
                msg="购物车添加成功！";
            }else{
                msg="购物车添加失败！";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String referer=request.getHeader("referer");
        String url="/pages/front/goods/GoodsServletFront"+referer.substring(referer.lastIndexOf("/"));
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String list(HttpServletRequest request){
        String mid=(String)request.getSession().getAttribute("mid");
        /*Map<Integer,Integer> map=(Map<Integer,Integer>)request.getSession().getAttribute("allCars");
        if(map!=null){
            try{
                request.setAttribute("allGoods",ServiceFrontFactory.getIShopcarServiceFrontInstance().listCar(map.keySet()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        request.setAttribute("allCars",map);*/
        try{
            Map<String,Object> map=ServiceFrontFactory.getIShopcarServiceFrontInstance().listCar(mid);
            request.setAttribute("allGoods",map.get("allGoods"));
            request.setAttribute("allCars",map.get("allShopcars"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/pages/front/shopcar/car_list.jsp";
    }
    public String update(HttpServletRequest request,HttpServletResponse response){
        String mid=(String)request.getSession().getAttribute("mid");
        ShopcarCookieUtil.clearCar(request,response);
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        Enumeration<String> enu=request.getParameterNames();
        Set<Shopcar> all = new HashSet<Shopcar>() ;
        while(enu.hasMoreElements()){
            String temp=enu.nextElement();
            int gid=Integer.parseInt(temp);
            int count=Integer.parseInt(request.getParameter(temp));
            Shopcar vo=new Shopcar();
            Member member=new Member();
            member.setMid(mid);
            vo.setMember(member);
            Goods goods=new Goods();
            goods.setGid(gid);
            vo.setGoods(goods);
            vo.setAmount(count);
            all.add(vo);
        }
        String msg=null;
        String url=null;
        try{
            if(ServiceFrontFactory.getIShopcarServiceFrontInstance().update(mid,all)){
                msg="更新成功";
            }else{
                msg="更新失败";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        url="/pages/front/shopcar/ShopcarServletFront/list";
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
        /*while(enu.hasMoreElements()){
            String temp=enu.nextElement();
            int gid=Integer.parseInt(temp);
            int count=Integer.parseInt(request.getParameter(temp));
            //map.put(gid,count);
            ShopcarCookieUtil.addCar(request,response,gid,count);
        }
        request.getSession().setAttribute("allCars",map);
        return "/pages/front/shopcar/ShopcarServletFront/list";*/
    }

    public String delete(HttpServletRequest request,HttpServletResponse response){
        String mid=(String)request.getSession().getAttribute("mid");
        //Map<Integer,Integer> map=(Map<Integer,Integer>)request.getSession().getAttribute("allCars");
        /*String ids=request.getParameter("ids");
        String result[]=ids.split("\\|");
        Set<Integer> set=new HashSet<Integer>();
        for(int x=0;x<result.length;x++){
            //map.remove(Integer.parseInt(result[x]));
            set.add(Integer.parseInt(result[x]));
        }
        ShopcarCookieUtil.removeCar(request,response,set);*/
        //request.getSession().setAttribute("allCars",map);
        String ids=request.getParameter("ids");
        String result[]=ids.split("\\|");
        Set<Integer> set=new HashSet<Integer>();
        for(int x=0;x<result.length;x++){
            set.add(Integer.parseInt(result[x]));
        }
        String msg=null;
        String url=null;
        try{
            if(ServiceFrontFactory.getIShopcarServiceFrontInstance().deleteCar(mid,set)){
                msg="删除成功";
            }else{
                msg="删除失败";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        url="/pages/front/shopcar/ShopcarServletFront/list";
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        /*return "/pages/front/shopcar/ShopcarServletFront/list";*/
        return "/pages/forward.jsp";
    }

    public static void clearCar(HttpServletRequest request,HttpServletResponse response){
        CookieUtil.clear(request,response);
    }
}
