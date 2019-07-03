package com.tiandelei.crm.web.listner;


import com.tiandelei.crm.settings.service.DicService;
import com.tiandelei.crm.settings.service.impl.DicServiceImpl;
import com.tiandelei.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.Set;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class SysInitListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("上下文域对象创建了");

        ServletContext appliaction = event.getServletContext();

        System.out.println("从监听器中，取出上下文域对象" + appliaction);

        //处理数据字典
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        System.out.println("服务器处理数字字典开始");

        Map<String,Object> map = ds.getAllDicValue();

        Set<String> set = map.keySet();

        for (String Key : set){
            appliaction.setAttribute(Key, map.get(Key));
        }

        System.out.println("服务器处理数字字典结束");

    }
}
