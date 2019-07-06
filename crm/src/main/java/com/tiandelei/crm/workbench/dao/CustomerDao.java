package com.tiandelei.crm.workbench.dao;

import com.tiandelei.crm.workbench.domain.Customer;

public interface CustomerDao {


    Customer getByName(String company);

    int save(Customer cus);
}
