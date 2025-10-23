<%--
  Created by IntelliJ IDEA.
  User: zrc12
  Date: 2021-6-3
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c"%>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String loginUrl=basePath+"pages/MemberServletFront/login";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>微商城</title>
    <link type="text/css" rel="stylesheet" href="css/mldn.css">
    <script type="text/javascript" src="js/mldn.js"></script>
    <script type="text/javascript" src="js/member.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div id="mainDiv">
        <form action="<%=loginUrl%>" method="post" onsubmit="return validateLogin()">
            <table border="1" cellpadding="5" cellspacing="0" bgcolor="F2F2F2" width="100%">
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td colspan="3">用户登录</td>
                </tr>
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td width="15%">用户ID</td>
                    <td width="40%"><input type="text" name="mid" id="mid" class="init" onblur="validateMid()"></td>
                    <td width="45%"><span id="midMsg"></span></td>
                </tr>
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td width="15%">密&nbsp;&nbsp;码</td>
                    <td width="40%"><input type="password" name="password" id="password" class="init" onblur="validatePassword()"></td>
                    <td width="45%"><span id="passwordMsg"></span></td>
                </tr>
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td width="15%">验证码</td>
                    <td width="40%">
                        <input type="text" name="code" id="code" maxlength="4" size="4" class="init" onblur="validateCode()">
                        <img src="pages/image.jsp" style="width:80px;height: 25px;">
                    </td>
                    <td width="45%"><span id="codeMsg"></span></td>
                </tr>
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td colspan="3">
                        <input type="checkbox" name="reme" id="reme" value="77760000">记住密码
                    </td>
                </tr>
                <tr onmouseover="changeColor(this,'white')" onmouseout="changeColor(this,'F2F2F2')">
                    <td colspan="3">
                        <input type="submit" value="登录">
                        <input type="reset" value="重置">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
