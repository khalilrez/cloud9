package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Order2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order2Repository extends JpaRepository<Order2, Integer> {
}
