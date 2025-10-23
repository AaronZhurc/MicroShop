package com.example.MShop.servlet.back;

import com.example.MShop.factory.ServiceBackFactory;
import com.example.MShop.vo.Item;
import util.validate.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "ItemServletBack", urlPatterns = "/pages/back/admin/item/ItemServletBack/*")
public class ItemServletBack extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String path = "/pages/errors.jsp";
        String status = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        if (status != null) {
            if ("insert".equals(status)) {
                path = this.insert(request);
            } else if ("update".equals(status)) {
                path = this.update(request);
            } else if ("delete".equals(status)) {
                path = this.delete(request);
            } else if ("list".equals(status)) {
                path = this.list(request);
            }
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    public String insert(HttpServletRequest request) {
        String msg = null;
        String url = null;
        String title = request.getParameter("title");
        if (ValidateUtil.validateEmpty(title)) {
            Item vo = new Item();
            vo.setTitle(title);
            try {
                if (ServiceBackFactory.getIItemServiceBackInstance().insert(vo)) {
                    msg = "商品分类信息增加成功";
                } else {
                    msg = "请确认输入数据是否正确";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            msg = "请确认输入数据是否正确";
        }
        url = "/pages/back/admin/item/item_insert.jsp";
        request.setAttribute("msg", msg);
        request.setAttribute("url", url);
        return "/pages/forward.jsp";
    }

    public String update(HttpServletRequest request) {
        String msg = null;
        String url = null;
        String iid = request.getParameter("iid");
        String title = request.getParameter("title");
        if (ValidateUtil.validateEmpty(iid) && ValidateUtil.validateEmpty(title)) {
            Item vo = new Item();
            vo.setIid(Integer.parseInt(iid));
            vo.setTitle(title);
            try {
                if (ServiceBackFactory.getIItemServiceBackInstance().update(vo)) {
                    msg = "更新成功";
                } else {
                    msg = "更新失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            msg = "数据错误";
        }
        url = "/pages/back/admin/item/ItemServletBack/list";
        request.setAttribute("msg", msg);
        request.setAttribute("url", url);
        return "/pages/forward.jsp";
    }

    public String delete(HttpServletRequest request) {
        String msg = null;
        String url = null;
        String ids = request.getParameter("ids");
        if (ValidateUtil.validateEmpty(ids)) {
            String result[] = ids.split("\\|");
            Set<Integer> all = new HashSet<Integer>();
            for (int x = 0; x < result.length; x++) {
                all.add(Integer.parseInt(result[x]));
            }
            try {
                if (ServiceBackFactory.getIItemServiceBackInstance().delete(all)) {
                    msg = "删除成功";
                } else {
                    msg = "删除失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            msg = "数据错误";
        }
        url = "/pages/back/admin/item/ItemServletBack/list";
        request.setAttribute("msg", msg);
        request.setAttribute("url", url);
        return "/pages/forward.jsp";
    }

    public String list(HttpServletRequest request) {
        try {
            request.setAttribute("allItems", ServiceBackFactory.getIItemServiceBackInstance().list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/pages/back/admin/item/item_list.jsp";
    }
}
