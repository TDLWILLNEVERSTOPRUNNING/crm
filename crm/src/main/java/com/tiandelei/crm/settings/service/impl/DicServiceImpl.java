package com.tiandelei.crm.settings.service.impl;

import com.tiandelei.crm.settings.dao.DicTypeDao;
import com.tiandelei.crm.settings.dao.DicValueDao;
import com.tiandelei.crm.settings.domin.DicType;
import com.tiandelei.crm.settings.domin.DicValue;
import com.tiandelei.crm.settings.service.DicService;
import com.tiandelei.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:田得雷
 * 2019/7/3
 */
public class DicServiceImpl implements DicService {

    DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);

    DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


    @Override
    public Map<String, Object> getAllDicValue() {

        Map<String, Object> map = new HashMap<>();

        //取得字典类型列表
        List<DicType> dtList = dicTypeDao.getTypeList();

        //遍历出来每一个类型
        for (DicType dt : dtList){
            String code = dt.getCode();


            //根据每一个类型编码取得字典值列表
            List<DicValue> dvList = dicValueDao.getDicValueByTypeCode(code);

            //保存到map中
            map.put(code, dvList);
        }
        //返回map
        return map;
    }
}
