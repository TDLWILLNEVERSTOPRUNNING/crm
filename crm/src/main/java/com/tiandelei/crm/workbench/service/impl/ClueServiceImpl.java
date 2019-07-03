package com.tiandelei.crm.workbench.service.impl;

import com.tiandelei.crm.utils.SqlSessionUtil;
import com.tiandelei.crm.workbench.dao.ClueDao;
import com.tiandelei.crm.workbench.service.ClueService;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

}
