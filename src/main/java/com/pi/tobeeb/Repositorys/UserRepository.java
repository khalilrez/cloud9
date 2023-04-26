package com.pi.tobeeb.Repositorys;

import java.util.List;
import java.util.Optional;

import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    User  findByVerificationToken(String Token);


    public User findByEmail(String UserEmail);
    @Query("SELECT u FROM User u WHERE u.isverified = 0")
    List<User> findUnverifiedUsers();
    @Query("SELECT u FROM User u WHERE u.isverified = 1")
    List<User> findVerifiedUsers();

    public User findByphonenumber(String phone);



}
