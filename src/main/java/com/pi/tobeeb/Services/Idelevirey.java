package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Delivery;

import java.util.List;

public interface Idelevirey {
    public Delivery addDelivery(Delivery delivery);

    public Delivery updateDelivery(Delivery  delivery);
    public void removedelivery(int  id);
    public List<Delivery> retrieveDeliverys();

}