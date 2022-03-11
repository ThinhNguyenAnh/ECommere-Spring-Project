package com.employee.employeewebapp;

import com.employee.order_entity.Order;
import com.employee.order_entity.OrderRepository;
import com.employee.user.User;
import com.employee.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testAdd(){
        User user = userRepository.findByEmail("mike@gmail.com");

        Order order = new Order(user,"Ngay 1",100);
        orderRepository.save(order);
    }
}
