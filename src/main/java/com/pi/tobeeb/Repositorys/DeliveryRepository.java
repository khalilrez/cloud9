package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery,Integer> {
}
