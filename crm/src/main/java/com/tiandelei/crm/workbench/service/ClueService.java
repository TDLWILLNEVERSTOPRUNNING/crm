package com.tiandelei.crm.workbench.service;

import com.tiandelei.crm.workbench.domain.Clue;
import com.tiandelei.crm.workbench.domain.Tran;

/**
 * Author:田得雷
 * 2019/7/3
 */
public interface ClueService {


    boolean save(Clue c);

    Clue detail(String id);

    boolean dissociated(String id);

    boolean bund(String cid, String[] aids);
    
}
