package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Delivery;
import com.pi.tobeeb.Exceptions.ResourceNotFoundException;
import com.pi.tobeeb.Repositorys.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdelevireyImp implements Idelevirey {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Override
    public Delivery addDelivery(Delivery delivery)
    {
        return deliveryRepository.save(delivery);
    }
    @Override
    public Delivery updateDelivery(Delivery  iddelivery)
    {
        Delivery delivery = deliveryRepository.findById(iddelivery.getIdDelivery()).orElseThrow(() -> new ResourceNotFoundException("Delivery", "id", iddelivery.getIdDelivery()));
        delivery.setDeliveryDate(iddelivery.getDeliveryDate());
        delivery.setCustomerName(iddelivery.getCustomerName());
        delivery.setCustomerAddress(iddelivery.getCustomerAddress());
        return deliveryRepository.save(delivery);

    }
    @Override
    public void removedelivery(int id) {

        deliveryRepository.deleteById(id);
    }


    @Override
    public List<Delivery> retrieveDeliverys() {

        return deliveryRepository.findAll();
    }


}
