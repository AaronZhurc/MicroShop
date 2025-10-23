package com.example.MShop.servlet.back;

import com.example.MShop.factory.ServiceBackFactory;
import com.example.MShop.vo.Admin;
import util.MD5Code;
import util.validate.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;

@WebServlet(name = "AdminLoginServletBack",urlPatterns = "/pages/back/AdminLoginServletBack/*")
public class AdminLoginServletBack extends HttpServlet {
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
            if("login".equals(status)){
                path=this.login(request);
            }else if("logout".equals(status)){
                path=this.logout(request);
            }
        }
        request.getRequestDispatcher(path).forward(request,response);
    }
    public String login(HttpServletRequest request){
        String msg=null;
        String url=null;
        String aid=request.getParameter("aid");
        String password=request.getParameter("password");
        String code=request.getParameter("code");
        String rand= (String) request.getSession().getAttribute("rand");
        if(ValidateUtil.validateEmpty(aid)&&ValidateUtil.validateEmpty(password)&&ValidateUtil.validateEmpty(code)&&ValidateUtil.validateEmpty(rand)) {
            if (ValidateUtil.validateSame(code, rand)) {
                Admin vo = new Admin();
                vo.setAid(aid);
                vo.setPassword(new MD5Code().getMD5ofStr(password));
                try {
                    if (ServiceBackFactory.getIAdminServiceBackInstance().login(vo)) {
                        request.getSession().setAttribute("aid", aid);
                        request.getSession().setAttribute("lastdate", vo.getLastdate());
                        msg = "管理员登陆成功";
                        url = "/pages/back/admin/index.jsp";
                    } else {
                        msg = "用户名或密码填写错误";
                        url = "/pages/back/login.jsp";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                msg = "登陆信息填写错误，请重新填写";
                url = "/pages/back/login.jsp";
            }
        }else{
            msg="验证码输入错误，请重新登录";
            url="/pages/back/login.jsp";
        }
        request.setAttribute("msg",msg);
        request.setAttribute("url",url);
        return "/pages/forward.jsp";
    }
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        request.setAttribute("msg","注销成功");
        request.setAttribute("url","/pages/back/login.jsp");
        return "/pages/forward.jsp";
    }
}
