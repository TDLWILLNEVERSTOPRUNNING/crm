package com.tiandelei.crm.vo;

import java.util.List;

/**
 * Author:田得雷
 * 2019/7/1
 */
public class Paginationvo<T> {

    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
