package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {
    UserVerificationToken findByToken(String Token);
}