package com.tiandelei.crm.workbench.dao;

import com.tiandelei.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);


}
