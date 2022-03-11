package com.employee.order_entity;

import com.employee.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;


    public void saveOrder(Order order) {
       orderRepo.save(order);

    }

    public Order getLastOrderId(User user) {
        Order order = orderRepo.findTop1ByUserOrderByOrderIDDesc(user);

        return order;
    }
}
