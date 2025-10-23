package com.example.MShop.filter;

import com.example.MShop.factory.ServiceFrontFactory;
import com.example.MShop.vo.Member;
import util.CookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = {"/index.jsp","/pages/front/*"})
public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpSession ses= request.getSession();
        if(ses.getAttribute("mid")==null){
            Map<String,String> map= CookieUtil.load(request);
            if(map.containsKey("mid")&&map.containsKey("password")) {
                Member vo = new Member();
                vo.setMid(map.get("mid"));
                vo.setPassword(map.get("password"));
                try {
                    if (ServiceFrontFactory.getIMemberServiceFrontInstance().login(vo)) {
                        ses.setAttribute("mid", vo.getMid());
                        ses.setAttribute("photo", vo.getPhoto());
                    }
                } catch (Exception e) {

                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
