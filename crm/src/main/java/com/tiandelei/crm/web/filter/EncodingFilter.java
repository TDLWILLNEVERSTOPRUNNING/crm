package com.tiandelei.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Author:田得雷
 * 2019/6/29
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到过滤字符编码的过滤器");

        //过滤post请求中文参数
        req.setCharacterEncoding("UTF-8");

        //过滤响应流响应的文字信息
        resp.setContentType("text/html;charset=utf-8");

        //使用过滤器chain将请求放行
        chain.doFilter(req, resp);
    }
}
