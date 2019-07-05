package com.tiandelei.crm.workbench.service.impl;

import com.tiandelei.crm.utils.SqlSessionUtil;
import com.tiandelei.crm.utils.UUIDUtil;
import com.tiandelei.crm.workbench.dao.ClueActivityRelationDao;
import com.tiandelei.crm.workbench.dao.ClueDao;
import com.tiandelei.crm.workbench.domain.Clue;
import com.tiandelei.crm.workbench.domain.ClueActivityRelation;
import com.tiandelei.crm.workbench.service.ClueService;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);


    @Override
    public boolean save(Clue c) {

        boolean flag = true;

        int count = clueDao.save(c);

        if (count!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue c = clueDao.detail(id);

        return c;
    }

    @Override
    public boolean dissociated(String id) {

        boolean flag = true;

        int count = clueActivityRelationDao.dissociated(id);

        if (count!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        boolean flag = true;

        for (String aid : aids){

            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();

            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(cid);
            clueActivityRelation.setActivityId(aid);

            int count = clueActivityRelationDao.save(clueActivityRelation);

            if (count!=1){

                flag = false;

            }
        }
        
        return flag;
    }
}
