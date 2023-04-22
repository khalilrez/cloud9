package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Order2;
import com.pi.tobeeb.Interfaces.Order2Interface;
import com.pi.tobeeb.Repositorys.Order2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Order2Service implements Order2Interface {

    @Autowired
    private Order2Repository order2Repository;
    @Override
    public List<Order2> getAllOrders() {
        return (List<Order2>) this.order2Repository.findAll();
    }
    @Override
    public Order2 getOrderById(int id) {
        return order2Repository.findById(id).orElse((Order2) null);
    }
    @Override

    public Order2 createOrder(Order2 order) {
        return order2Repository.save(order);
    }
    @Override

    public Order2 updateOrder(int id, Order2 updatedOrder) {
        Order2 existingOrder = getOrderById(id);

        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setTotalprice(updatedOrder.getTotalprice());

        return order2Repository.save(existingOrder);
    }
    @Override

    public void deleteOrder(int id) {
        order2Repository.deleteById(id);
    }
    public List<Order2> search(String searchTerm) {
        return order2Repository.search(searchTerm);
    }

}
