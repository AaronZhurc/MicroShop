package com.example.MShop.servlet.back;

import com.example.MShop.factory.ServiceBackFactory;
import util.validate.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "MemberServletBack",urlPatterns = "/pages/back/admin/member/MemberServletBack/*")
public class MemberServletBack extends HttpServlet {
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
            if("list".equals(status)){
                path=this.list(request);
            }else if("listStatus".equals(status)){
                path=this.listStatus(request);
            }else if("updateStatus".equals(status)) {
                path=this.updateStatus(request) ;
            }else if("show".equals(status)){
                path=this.show(request);
            }
        }
        request.getRequestDispatcher(path).forward(request,response);
    }
    public String list(HttpServletRequest request){
        int currentPage=1;
        int lineSize=15;
        String column=null;
        String keyWord=null;
        String columnData="用户名:mid|正式姓名:name|联系电话:phone|地址:address";
        try{
            currentPage=Integer.parseInt(request.getParameter("cp"));
        }catch (Exception e){

        }
        try{

        }catch (Exception e){
            currentPage=Integer.parseInt(request.getParameter("ls"));
        }
        column=request.getParameter("col");
        keyWord=request.getParameter("kw");
        if(column==null){
            column="mid";
        }
        if(keyWord==null){
            keyWord="";
        }
        try {
            Map<String,Object> map= ServiceBackFactory.getIMemberServiceBackInstance().list(currentPage,lineSize,column,keyWord);
            request.setAttribute("allMembers",map.get("allMembers"));
            request.setAttribute("allRecorders",map.get("memberCount"));
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("lineSize",lineSize);
        request.setAttribute("column",column);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("columnData",columnData);
        request.setAttribute("url","/pages/back/admin/member/MemberServletBack/list");
        return "/pages/back/admin/member/member_list.jsp";
    }
    public String listStatus(HttpServletRequest request){
        int status=0;
        int currentPage=1;
        int lineSize=15;
        String column=null;
        String keyWord=null;
        String columnData="用户名:mid|正式姓名:name|联系电话:phone|地址:address";
        try{
            status=Integer.parseInt(request.getParameter("status"));
        }catch (Exception e){

        }
        try{
            currentPage=Integer.parseInt(request.getParameter("cp"));
        }catch (Exception e){

        }
        try{

        }catch (Exception e){
            currentPage=Integer.parseInt(request.getParameter("ls"));
        }
        column=request.getParameter("col");
        keyWord=request.getParameter("kw");
        if(column==null){
            column="mid";
        }
        if(keyWord==null){
            keyWord="";
        }
        try {
            Map<String,Object> map= ServiceBackFactory.getIMemberServiceBackInstance().listByStatus(status,currentPage,lineSize,column,keyWord);
            request.setAttribute("allMembers",map.get("allMembers"));
            request.setAttribute("allRecorders",map.get("memberCount"));
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("lineSize",lineSize);
        request.setAttribute("column",column);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("columnData",columnData);
        request.setAttribute("url","/pages/back/admin/member/MemberServletBack/list");
        request.setAttribute("paramName","status");
        request.setAttribute("paramValue",String.valueOf(status));
        return "/pages/back/admin/member/member_list.jsp";
    }
    public String updateStatus(HttpServletRequest request){
        /*Enumeration<String> enu=request.getHeaderNames();
        while(enu.hasMoreElements()){
            String name=enu.nextElement();
            System.out.println("*** name ="+name+", value = "+request.getHeader(name));
        }*/
        String referer=request.getHeader("referer");
        String type=request.getParameter("type");
        String msg=null;
        String url=null;
        String ids=request.getParameter("ids");
        if(ValidateUtil.validateEmpty(ids)){
            String result[]=ids.split("\\|");
            Set<String> mid=new HashSet<String>();
            for(int x=0;x<result.length;x++){
                mid.add(result[x]);
            }
            try {
                boolean flag=false;
                if("active".equalsIgnoreCase(type)) {
                    flag=ServiceBackFactory.getIMemberServiceBackInstance().updateActive(mid);
                }
                if("lock".equalsIgnoreCase(type)){
                    flag=ServiceBackFactory.getIMemberServiceBackInstance().updateLock(mid);
                }
                if(flag){
                    msg = "用户状态更新成功";
                } else {
                    msg = "用户状态更新失败";
                }
                url="/pages/back/admin/member/MemberServletBack"+referer.substring(referer.lastIndexOf("/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            msg="未选择更新数据";
            url="/pages/back/admin/member/MemberServletBack"+referer.substring(referer.lastIndexOf("/"));
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String show(HttpServletRequest request){
        String referer=request.getHeader("referer");
        String msg=null;
        String url=null;
        String mid=request.getParameter("mid");
        if(ValidateUtil.validateEmpty(mid)) {
            try {
                request.setAttribute("member",ServiceBackFactory.getIMemberServiceBackInstance().show(mid));
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("msg",msg);
            request.setAttribute("url",url);
            return "/pages/back/admin/member/member_show.jsp";
        }else{
            msg="未选择查看数据或数据不存在";
            url="/pages/back/admin/member/MemberServletBack"+referer.substring(referer.lastIndexOf("/"));
            request.setAttribute("msg",msg);
            request.setAttribute("url",url);
            return "/pages/forward.jsp";
        }
    }
}
