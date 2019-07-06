package com.tiandelei.crm.workbench.service.impl;

import com.tiandelei.crm.utils.DateTimeUtil;
import com.tiandelei.crm.utils.SqlSessionUtil;
import com.tiandelei.crm.utils.UUIDUtil;
import com.tiandelei.crm.workbench.dao.*;
import com.tiandelei.crm.workbench.domain.*;
import com.tiandelei.crm.workbench.service.ClueService;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class ClueServiceImpl implements ClueService {

    //线索相关表

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    //客户相关得表

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);//客户

    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);//客户备注

    //联系人相关的表

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);

    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);//联系人市场活动

    //交易相关的表

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);

    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);//交易历史


    @Override
    public boolean save(Clue c) {

        boolean flag = true;

        int count = clueDao.save(c);

        if (count != 1) {

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

        if (count != 1) {

            flag = false;

        }

        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        boolean flag = true;

        for (String aid : aids) {

            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();

            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(cid);
            clueActivityRelation.setActivityId(aid);

            int count = clueActivityRelationDao.save(clueActivityRelation);

            if (count != 1) {

                flag = false;

            }
        }

        return flag;
    }


}
