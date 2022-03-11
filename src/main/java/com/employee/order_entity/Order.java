package com.employee.order_entity;

import com.employee.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Integer orderID;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String date;
    private double total;

    @OneToMany(mappedBy="order")
    private Set<OrderDetail> orderDetail;

    public Order() {
    }

    public Order(User user, String date, double total) {
        this.user = user;
        this.date = date;
        this.total = total;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
