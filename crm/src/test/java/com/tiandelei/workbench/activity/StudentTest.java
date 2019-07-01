package com.tiandelei.workbench.activity;

import com.tiandelei.crm.utils.ServiceFactory;
import com.tiandelei.crm.utils.UUIDUtil;
import com.tiandelei.crm.workbench.domain.Activity;
import com.tiandelei.crm.workbench.service.ActivityService;
import com.tiandelei.crm.workbench.service.impl.ActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author:田得雷
 * 2019/7/1
 */
public class StudentTest {
    //单元测试

    @Test
    public void testSave(){

        ActivityService  as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = new Activity();

        a.setId(UUIDUtil.getUUID());
        a.setName("嘻嘻哈哈");

//        as.save(a);

        //断言
        boolean flag = as.save(a);

        Assert.assertEquals(flag, true
        );


    }

    @Test
    public void testUpdate(){

        System.out.println("测试修改");

    }

    @Test
    public void testDelete(){

        System.out.println("测试删除");

    }

}
