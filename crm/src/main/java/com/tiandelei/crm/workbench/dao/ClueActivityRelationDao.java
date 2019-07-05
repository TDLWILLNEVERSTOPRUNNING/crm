package com.tiandelei.crm.workbench.dao;


import com.tiandelei.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int dissociated(String id);

    int save(ClueActivityRelation clueActivityRelation);
}
