package com.tiandelei.crm.workbench.dao;

/**
 * Author:田得雷
 * 2019/6/29
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

}
