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

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {


        String createTime = DateTimeUtil.getSysTime();

        boolean flag = true;

        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        /*

            通过id查询得到了需要转换的线索对象c

            将来生成客户的时候，需要从c中取出公司的信息转换为客户信息
            将来生成联系人的时候，需要从c中取出人相关的信息转换为联系人信息

         */
        Clue c = clueDao.getById(clueId);

        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        //取得公司名称
        String company = c.getCompany();
        //根据公司名称取得客户对象
        Customer cus = customerDao.getByName(company);

        //如果cus为空，说明没有查到该客户，我们需要新建一个客户
        if (cus == null) {

            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setWebsite(c.getWebsite());
            cus.setPhone(c.getPhone());
            cus.setOwner(c.getOwner());
            cus.setNextContactTime(c.getNextContactTime());
            cus.setName(company);
            cus.setDescription(c.getDescription());
            cus.setCreateTime(createTime);
            cus.setCreateBy(createBy);
            cus.setContactSummary(c.getContactSummary());
            cus.setAddress(c.getAddress());

            //添加客户
            int count1 = customerDao.save(cus);
            if (count1 != 1) {

                flag = false;

            }

        }

        //---------------------------------------------------------------------------
        //---------------------------------------------------------------------------
        //第二步处理完之后，下面的步骤如果要应用到客户相关的信息（尤其是客户的id），我们就使用cus.getId()
        //---------------------------------------------------------------------------
        //---------------------------------------------------------------------------


        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(c.getSource());
        con.setOwner(c.getOwner());
        con.setNextContactTime(c.getNextContactTime());
        con.setMphone(c.getMphone());
        con.setJob(c.getJob());
        con.setFullname(c.getFullname());
        con.setEmail(c.getEmail());
        con.setDescription(c.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateTime(createTime);
        con.setCreateBy(createBy);
        con.setContactSummary(c.getContactSummary());
        con.setAppellation(c.getAppellation());
        con.setAddress(c.getAddress());

        //添加联系人
        int count2 = contactsDao.save(con);
        if (count2 != 1) {

            flag = false;

        }

        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        //经过第三步之后，下面的步骤如果需要使用到联系人相关的信息（尤其是id），我们使用con.getId()
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------

        //(4) 线索备注转换到客户备注以及联系人备注
        //查询与该线索关联的备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        //遍历每一条线索备注
        for (ClueRemark clueRemark : clueRemarkList) {

            //取出每一个需要转换的备注信息
            String noteContent = clueRemark.getNoteContent();

            //创建客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(cus.getId());
            customerRemark.setCreateTime(createTime);
            customerRemark.setCreateBy(createBy);
            //添加客户备注
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1) {

                flag = false;

            }

            //创建联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setCreateBy(createBy);
            //添加联系人备注
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1) {

                flag = false;

            }
        }

        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        //查询出与该线索关联的市场活动的关联关系列表
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {

            //取出每一个与该线索关联的市场活动
            String activityId = clueActivityRelation.getActivityId();

            //创建联系人与市场活动的关联关系对象
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(con.getId());
            contactsActivityRelation.setActivityId(activityId);

            //添加联系人与市场活动的关联关系
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1) {

                flag = false;

            }


        }

        //(6) 如果有创建交易需求，创建一条交易
        if(t!=null){

            //如果t不为null，说明需要创建交易
            //在控制器中已经为t对象赋予了一些重要的属性值 id,creatBy,createTime,money,name,expectedDate,stage,activityId
            //除此之外，我们也可以通过线索继续丰富t对象的属性值
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());

            //添加交易
            int count6 = tranDao.save(t);
            if(count6!=1){

                flag = false;

            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setCreateBy(createBy);
            th.setCreateTime(createTime);
            th.setExpectedDate(t.getExpectedDate());
            th.setMoney(t.getMoney());
            th.setStage(t.getStage());
            th.setTranId(t.getId());
            //添加交易历史
            int count7 = tranHistoryDao.save(th);
            if(count7!=1){

                flag = false;

            }

        }

        //(8) 删除线索备注
        for(ClueRemark clueRemark:clueRemarkList){

            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8!=1){

                flag = false;

            }

        }

        //(9) 删除线索和市场活动的关系
        for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){

            int count9 = clueActivityRelationDao.dissociated(clueActivityRelation.getId());
            if(count9!=1){

                flag = false;

            }

        }

        //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if(count10!=1){

            flag = false;

        }


        return flag;
    }

}
