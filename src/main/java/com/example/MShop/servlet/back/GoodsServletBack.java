package com.example.MShop.servlet.back;

import com.example.MShop.factory.ServiceBackFactory;
import com.example.MShop.vo.Admin;
import com.example.MShop.vo.Goods;
import com.example.MShop.vo.Item;
import com.jspsmart.upload.SmartUpload;
import util.validate.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@WebServlet(name="GoodsServletBack", urlPatterns="/pages/back/admin/goods/GoodsServletBack/*")
public class GoodsServletBack extends HttpServlet{
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        String path="/pages/errors.jsp";
        String status=request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
        if(status!=null){
            if("insertPre".equals(status)){
                path=this.insertPre(request);
            }else if("insert".equals(status)){
                path=this.insert(request,response);
            }else if("list".equals(status)){
                path=this.list(request);
            }else if("listStatus".equals(status)){
                path=this.listStatus(request);
            }else if("updateStatus".equals(status)){
                path=this.updateStatus(request);
            }else if("updatePre".equals(status)){
                path=this.updatePre(request);
            }else if("update".equals(status)){
                path=this.update(request,response);
            }else if("delete".equals(status)){
                path=this.delete(request);
            }
        }
        request.getRequestDispatcher(path).forward(request,response);
    }

    public String insertPre(HttpServletRequest request){
        try{
            Map<String,Object> map=ServiceBackFactory.getIGoodsServiceBackInstance().insertPre();
            request.setAttribute("allItems",map.get("allItems"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/pages/back/admin/goods/goods_insert.jsp";
    }

    public String insert(HttpServletRequest request,HttpServletResponse response){
        String msg=null;
        String url=null;
        SmartUpload smart=new SmartUpload();
        try{
            smart.initialize(super.getServletConfig(),request,response);
            smart.upload();
        }catch(Exception e){
            e.printStackTrace();
        }
        String iid=smart.getRequest().getParameter("iid");
        String title=smart.getRequest().getParameter("title");
        String price=smart.getRequest().getParameter("price");
        String amount=smart.getRequest().getParameter("amount");
        String note=smart.getRequest().getParameter("note");
        String status=smart.getRequest().getParameter("status");
        if(ValidateUtil.validateEmpty(title)&&ValidateUtil.validateRegex(price,"\\d+(\\.\\d{1,2})?")&&ValidateUtil.validateRegex(amount,"\\d+")&&ValidateUtil.validateEmpty(note)&&ValidateUtil.validateRegex(status,"\\d")&&ValidateUtil.validateRegex(iid,"\\d+")){
            Goods vo=new Goods();
            vo.setTitle(title);
            vo.setPrice(Double.parseDouble(price));
            vo.setAmount(Integer.parseInt(amount));
            vo.setNote(note);
            vo.setStatus(Integer.parseInt(status));
            vo.setPubdate(new Date());
            vo.setBow(0);
            Item item=new Item();
            item.setIid(Integer.parseInt(iid));
            vo.setItem(item);
            String aid=(String)request.getSession().getAttribute("aid");
            Admin admin=new Admin();
            admin.setAid(aid);
            vo.setAdmin(admin);
            try{
                if(smart.getFiles().getSize()>0){
                    if(smart.getFiles().getFile(0).getContentType().contains("image")){
                        vo.setPhoto(UUID.randomUUID()+"."+smart.getFiles().getFile(0).getFileExt());
                    }else{
                        vo.setPhoto("nophoto.jpg");
                    }
                }else{
                    vo.setPhoto("nophoto.jpg");
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                if(ServiceBackFactory.getIGoodsServiceBackInstance().insert(vo)){
                    String filePath=super.getServletContext().getRealPath("/upload/goods/")+vo.getPhoto();
                    if(smart.getFiles().getSize()>0){
                        if(smart.getFiles().getFile(0).getContentType().contains("image")){
                            smart.getFiles().getFile(0).saveAs(filePath);
                        }
                    }
                    msg="信息发布成功";
                }else{
                    msg="信息发布失败";
                }
                url="/pages/back/admin/goods/GoodsServletBack/insertPre";
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            msg="增加数据出错";
            url="/pages/back/admin/goods/GoodsServletBack/insertPre";
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }

    public String list(HttpServletRequest request){
        int currentPage=1;
        int lineSize=15;
        String column=null;
        String keyWord=null;
        String columnData="商品名称:title|发布管理员:aid";
        try{
            currentPage=Integer.parseInt(request.getParameter("cp"));
        }catch(Exception e){

        }
        try{
            lineSize=Integer.parseInt(request.getParameter("ls"));
        }catch(Exception e){

        }
        column=request.getParameter("col");
        keyWord=request.getParameter("kw");
        if(column==null){
            column="title";
        }
        if(keyWord==null){
            keyWord="";
        }
        try{
            Map<String,Object> map=ServiceBackFactory.getIGoodsServiceBackInstance().list(currentPage,lineSize,column,keyWord);
            request.setAttribute("allGoods",map.get("allGoods"));
            request.setAttribute("allRecorders",map.get("goodsCount"));
        }catch(Exception e){
            e.printStackTrace();
        }
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("lineSize",lineSize);
        request.setAttribute("column",column);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("columnData",columnData);
        request.setAttribute("url","/pages/back/admin/goods/GoodsServletBack/list");
        return "/pages/back/admin/goods/goods_list.jsp";
    }

    public String listStatus(HttpServletRequest request){
        int currentPage=1;
        int lineSize=15;
        String column=null;
        String keyWord=null;
        String columnData="商品名称:title|发布管理员:aid";
        try{
            currentPage=Integer.parseInt(request.getParameter("cp"));
        }catch(Exception e){

        }
        try{
            lineSize=Integer.parseInt(request.getParameter("ls"));
        }catch(Exception e){

        }
        column=request.getParameter("col");
        keyWord=request.getParameter("kw");
        if(column==null){
            column="title";
        }
        if(keyWord==null){
            keyWord="";
        }
        int status=Integer.parseInt(request.getParameter("status"));
        try{
            Map<String,Object> map=ServiceBackFactory.getIGoodsServiceBackInstance().listStatus(status,currentPage,lineSize,column,keyWord);
            request.setAttribute("allGoods",map.get("allGoods"));
            request.setAttribute("allRecorders",map.get("goodsCount"));
        }catch(Exception e){
            e.printStackTrace();
        }
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("lineSize",lineSize);
        request.setAttribute("column",column);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("columnData",columnData);
        request.setAttribute("url","/pages/back/admin/goods/GoodsServletBack/listStatus");
        request.setAttribute("paramName","status");
        request.setAttribute("paramValue",String.valueOf(status));
        return "/pages/back/admin/goods/goods_list.jsp";
    }
    public String updateStatus(HttpServletRequest request){
        String msg=null;
        String url=null;
        String referer=request.getHeader("referer");
        String type=request.getParameter("type");
        String ids=request.getParameter("ids");
        if(ValidateUtil.validateEmpty(ids)){
            String result[]=ids.split("\\|");
            Set<Integer> all=new HashSet<Integer>();
            for(int x=0;x<result.length;x++){
                String temp[]=result[x].split(":");
                all.add(Integer.parseInt(temp[0]));
            }
            boolean flag=false;
            try{
                if("up".equals(type)){
                    flag=ServiceBackFactory.getIGoodsServiceBackInstance().updateUp(all);
                }
                if("down".equals(type)){
                    flag=ServiceBackFactory.getIGoodsServiceBackInstance().updateDown(all);
                }
                if("delete".equals(type)){
                    flag=ServiceBackFactory.getIGoodsServiceBackInstance().updateDelete(all);
                }
                if(flag){
                    msg="商品状态更新成功";
                }else{
                    msg="商品状态更新失败";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            msg="更新数据出错";
        }
        url="/pages/back/admin/goods/GoodsServletBack"+referer.substring(referer.lastIndexOf("/"));
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String updatePre(HttpServletRequest request){
        String gid=request.getParameter("gid");
        String referer=request.getHeader("referer");
        if(ValidateUtil.validateEmpty(gid)){
            try{
                Map<String,Object> map=ServiceBackFactory.getIGoodsServiceBackInstance().updatePre(Integer.parseInt(gid));
                request.setAttribute("allItems",map.get("allItems"));
                request.setAttribute("goods",map.get("goods"));
                request.setAttribute("back","/pages/back/admin/goods/GoodsServletBack"+referer.substring(referer.lastIndexOf("/")));
            }catch(Exception e){
                e.printStackTrace();
            }
            return "/pages/back/admin/goods/goods_update.jsp";
        }else{
            request.setAttribute("msg","尚未选择需要更新的数据");
            request.setAttribute("url","/pages/back/admin/goods/GoodsServletBack"+referer.substring(referer.lastIndexOf("/")));
            return "/pages/forward.jsp";
        }
    }
    public String update(HttpServletRequest request,HttpServletResponse response){
        String msg=null;
        String url=null;
        SmartUpload smart=new SmartUpload();
        try{
            smart.initialize(super.getServletConfig(),request,response);
            smart.upload();
        }catch(Exception e){
            e.printStackTrace();
        }
        String oldpic=smart.getRequest().getParameter("oldpic");
        String gid=smart.getRequest().getParameter("gid");
        String iid=smart.getRequest().getParameter("iid");
        String title=smart.getRequest().getParameter("title");
        String price=smart.getRequest().getParameter("price");
        String amount=smart.getRequest().getParameter("amount");
        String note=smart.getRequest().getParameter("note");
        String status=smart.getRequest().getParameter("status");
        if(ValidateUtil.validateEmpty(gid)&&ValidateUtil.validateEmpty(title)&&ValidateUtil.validateRegex(price,"\\d+(\\.\\d{1,2})?")&&ValidateUtil.validateRegex(amount,"\\d+")&&ValidateUtil.validateEmpty(note)&&ValidateUtil.validateRegex(status,"\\d")&&ValidateUtil.validateRegex(iid,"\\d+")){
            Goods vo=new Goods();
            vo.setGid(Integer.parseInt(gid));
            vo.setTitle(title);
            vo.setPrice(Double.parseDouble(price));
            vo.setAmount(Integer.parseInt(amount));
            vo.setNote(note);
            vo.setStatus(Integer.parseInt(status));
            Item item=new Item();
            item.setIid(Integer.parseInt(iid));
            vo.setItem(item);
            try{
                if(smart.getFiles().getSize()>0){
                    if(smart.getFiles().getFile(0).getContentType().contains("image")){
                        if("nophoto.jpg".equals(oldpic)){
                            vo.setPhoto(UUID.randomUUID()+"."+smart.getFiles().getFile(0).getFileExt());
                        }else{
                            vo.setPhoto(oldpic);
                        }
                    }else{
                        vo.setPhoto(oldpic);
                    }
                }else{
                    vo.setPhoto(oldpic);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                if(ServiceBackFactory.getIGoodsServiceBackInstance().update(vo)){
                    String filePath=super.getServletContext().getRealPath("/upload/goods/")+vo.getPhoto();
                    if(smart.getFiles().getSize()>0){
                        if(smart.getFiles().getFile(0).getContentType().contains("image")){
                            smart.getFiles().getFile(0).saveAs(filePath);
                        }
                    }
                    msg="修改成功";
                }else{
                    msg="修改失败";
                }
                url=smart.getRequest().getParameter("back");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            msg="修改数据出错";
            url=request.getParameter("back");
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String delete(HttpServletRequest request){
        String msg=null;
        String url=null;
        String referer=request.getHeader("referer");
        String ids=request.getParameter("ids");
        if(ValidateUtil.validateEmpty(ids)){
            Set<Integer> allIds=new HashSet<Integer>();
            Set<String> allPhotos=new HashSet<String>();
            String result[]=ids.split("\\|");
            for(int x=0;x<result.length;x++){
                String temp[]=result[x].split(":");
                allIds.add(Integer.parseInt(temp[0]));
                if(!"nophoto.jpg".equals(temp[1])){
                    allPhotos.add(temp[1]);
                }
            }
            try{
                if(ServiceBackFactory.getIGoodsServiceBackInstance().delete(allIds)){
                    if(allPhotos.size()>0){
                        Iterator<String> iter=allPhotos.iterator();
                        while(iter.hasNext()){
                            String filePath=super.getServletContext().getRealPath("/upload/goods/")+iter.next();
                            File file=new File(filePath);
                            if(file.exists()){
                                file.delete();
                            }
                        }
                    }
                    msg="删除成功";
                }else{
                    msg="删除失败";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            msg="删除的数据有错误";
        }
        url="/pages/back/admin/goods/GoodsServletBack"+referer.substring(referer.lastIndexOf("/"));
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
}
