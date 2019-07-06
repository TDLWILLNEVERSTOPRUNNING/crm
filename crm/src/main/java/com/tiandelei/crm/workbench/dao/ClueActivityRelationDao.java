package com.tiandelei.crm.workbench.dao;


import com.tiandelei.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int dissociated(String id);

    int save(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(String id);
}
