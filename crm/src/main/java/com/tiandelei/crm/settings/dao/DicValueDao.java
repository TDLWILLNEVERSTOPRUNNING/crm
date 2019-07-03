package com.tiandelei.crm.settings.dao;

import com.tiandelei.crm.settings.domin.DicValue;

import java.util.List;

/**
 * Author:田得雷
 * 2019/7/3
 */
public interface DicValueDao {

    List<DicValue> getDicValueByTypeCode(String code);
}
