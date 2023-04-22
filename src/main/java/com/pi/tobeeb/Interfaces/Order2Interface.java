package com.pi.tobeeb.Interfaces;

import com.pi.tobeeb.Entities.Order2;

import java.util.List;

public interface Order2Interface {
    public List<Order2> getAllOrders();
    public Order2 getOrderById(int id);
    public Order2 createOrder(Order2 order);
    public Order2 updateOrder(int id, Order2 updatedOrder);
    public void deleteOrder(int id);
}

