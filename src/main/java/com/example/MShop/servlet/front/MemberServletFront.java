package com.example.MShop.servlet.front;

import com.example.MShop.factory.ServiceFrontFactory;
import com.example.MShop.vo.Member;
import util.BasePath;
import util.CookieUtil;
import util.MD5Code;
import util.validate.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet(name = "MemberServletFront",urlPatterns = "/pages/MemberServletFront/*")
public class MemberServletFront extends HttpServlet {
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
            if("regist".equals(status)){
                path=this.regist(request);
            }else if("active".equals(status)){
                path=this.active(request);
            }else if("login".equals(status)){
                path=this.login(request,response);
            }else if("logout".equals(status)){
                path=this.logout(request,response);
            }
        }
        request.getRequestDispatcher(path).forward(request,response);
    }
    public String regist(HttpServletRequest request){
        String msg=null;
        String url=null;
        String mid=request.getParameter("mid");
        String password=request.getParameter("password");
        if(ValidateUtil.validateEmpty(mid)&&ValidateUtil.validateEmpty(password)){
            Member vo=new Member();
            vo.setMid(mid);
            vo.setPassword(new MD5Code().getMD5ofStr(password));
            vo.setRegdate(new Date());
            vo.setPhoto("nophoto.jpg");
            vo.setCode(UUID.randomUUID().toString());
            vo.setStatus(2);
            try {
                if (ServiceFrontFactory.getIMemberServiceFrontInstance().regist(vo)){
                    msg="注册成功，请进行账户激活";
                    url="/index.jsp";
                    System.out.println("激活码"+ BasePath.getBasePath(request)+"/pages/MemberServletFront/active?mid="+mid+"&code="+vo.getCode());
                }else {
                    msg="注册失败，请重新注册";
                    url="/pages/member_regist.jsp";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            msg="输入的用户注册信息不正确，请重新注册";
            url="/pages/member_regist.jsp";
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String active(HttpServletRequest request){
        String msg=null;
        String url=null;
        String mid=request.getParameter("mid");
        String code=request.getParameter("code");
        if(ValidateUtil.validateEmpty(mid)&&ValidateUtil.validateEmpty(code)) {
            Member vo=new Member();
            vo.setMid(mid);
            vo.setCode(code);
            try {
                if(ServiceFrontFactory.getIMemberServiceFrontInstance().active(vo)){
                    msg="激活成功，请登录";
                    url="/pages/member_login.jsp";
                }else{
                    msg="激活失败";
                    url="/index.jsp";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            msg="激活出错";
            url="/index.jsp";
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String login(HttpServletRequest request, HttpServletResponse response){
        String msg=null;
        String url=null;
        String mid=request.getParameter("mid");
        String password=request.getParameter("password");
        String code=request.getParameter("code");
        String rand= (String) request.getSession().getAttribute("rand");
        if(ValidateUtil.validateEmpty(mid)&&ValidateUtil.validateEmpty(password)&&ValidateUtil.validateEmpty(code)&&ValidateUtil.validateEmpty(rand)) {
            if(ValidateUtil.validateSame(code,rand)){
                Member vo=new Member();
                vo.setMid(mid);
                vo.setPassword(new MD5Code().getMD5ofStr(password));
                vo.setCode(code);
                try {
                    if(ServiceFrontFactory.getIMemberServiceFrontInstance().login(vo)){
                        request.getSession().setAttribute("mid",mid);
                        request.getSession().setAttribute("photo",vo.getPhoto());
                        msg="登陆成功";
                        url="/index.jsp";
                        if(request.getParameter("reme")!=null) {
                            int expiry=Integer.parseInt(request.getParameter("reme"));
                            CookieUtil.save(response, request, "mid", mid, expiry);
                            CookieUtil.save(response, request, "password", vo.getPassword(), expiry);
                        }
                    }else{
                        msg="用户名或密码错误";
                        url="/pages/member_login.jsp";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                msg="验证码输入错误，请重新登录";
                url="/pages/member_login.jsp";
            }
        }else {
            msg="信息输入错误，请重新登录";
            url="/pages/member_login.jsp";
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.clear(request,response);
        request.getSession().invalidate();
        request.setAttribute("msg","您已安全退出");
        request.setAttribute("url","/index.jsp");
        return "/pages/forward.jsp";
    }
}
