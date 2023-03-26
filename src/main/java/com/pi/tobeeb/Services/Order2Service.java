package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Order2;
import com.pi.tobeeb.Interfaces.Order2Interface;
import com.pi.tobeeb.Repositorys.Order2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Order2Service implements Order2Interface {

    @Autowired
    private Order2Repository order2Repository;

    public List<Order2> getAllOrders() {
        return this.order2Repository.findAll();
    }

    public Order2 getOrderById(int id) {
        return order2Repository.findById(id).orElse((Order2) null);
    }

    public Order2 createOrder(Order2 order) {
        return order2Repository.save(order);
    }

    public Order2 updateOrder(int id, Order2 updatedOrder) {
        Order2 existingOrder = getOrderById(id);

        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setTotalprice(updatedOrder.getTotalprice());

        return order2Repository.save(existingOrder);
    }

    public void deleteOrder(int id) {
        order2Repository.deleteById(id);
    }
}
