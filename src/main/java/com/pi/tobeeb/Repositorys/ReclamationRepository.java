package com.pi.tobeeb.Repositorys;


import com.pi.tobeeb.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    @Query(value = "select a from Reclamation a where a.status=false ",nativeQuery = false)
    public List<Reclamation> getreclamnontraite();
}