package com.tiandelei.crm.web.filter;


import com.tiandelei.crm.settings.domin.User;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author:田得雷
 * 2019/6/29
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("执行判断有没有登录过得过滤器");

        /*
        从request中取session，从session中取User
        判断user是否为空
        如果user登录过说明User不为空
        如果没有登录过说明User为空
        */

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //取访问路径
        String Path = request.getServletPath();

        //如果是这两个路径不需要拦截
        if ("/login.jsp".equals(Path) || "/settings/user/login.do".equals(Path)) {

            //直接放行
            chain.doFilter(req, resp);

            //其他资源
        } else {

            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {

                //user不为null说明登陆过将请求放行
                chain.doFilter(req, resp);
            } else {

                //user为null说明没有登陆过，重定向到登录页

            /*
            不论是请求还是转发，我们使用的路径一定是绝对路径
            传统的绝对路径写法：/项目名/具体资源路径。。。

            * 请求转发：
            * 使用的是一种特殊的绝对路径写法前面不加/项目名，这种路径也叫做内部路径
            * /具体资源路径/。。
            *
            *
            * 重定向
            * 使用传统的绝对路径写法：/项目名/具体资源路径。。。
            *
            *
            * request.getContextPath():  /当前项目的项目名
            * */

                response.sendRedirect(request.getContextPath() + "/login.jsp");///crm/login/jsp : 写死了换一个项目粘的代码都需要换成当前项目名

            }
        }
    }
}
