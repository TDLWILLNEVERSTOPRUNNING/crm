package com.tiandelei.crm.settings.service.impl;

import com.tiandelei.crm.settings.dao.DicTypeDao;
import com.tiandelei.crm.settings.dao.DicValueDao;
import com.tiandelei.crm.settings.service.DicService;
import com.tiandelei.crm.utils.SqlSessionUtil;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class DicServiceImpl implements DicService {

    DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);

    DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

}
