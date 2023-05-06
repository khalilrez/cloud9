package com.pi.tobeeb.Repositorys;
import java.util.Optional;

import com.pi.tobeeb.Entities.ERole;
import com.pi.tobeeb.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}