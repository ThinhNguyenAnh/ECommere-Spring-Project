package com.employee.order_entity;

import com.employee.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {



    Order findTop1ByUserOrderByOrderIDDesc(User user);
}
