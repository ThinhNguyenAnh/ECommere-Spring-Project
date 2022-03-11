package com.employee.order_entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository repo;

    public void save (OrderDetail orderDetail){
        repo.save(orderDetail);
    }
}
