package com.tiandelei.crm.workbench.service;

import com.tiandelei.crm.vo.Paginationvo;
import com.tiandelei.crm.workbench.domain.Activity;

import java.util.Map;

/**
 * Author:田得雷
 * 2019/6/29
 */
public interface ActivityService {
    Boolean save(Activity a);

    Paginationvo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);
}
