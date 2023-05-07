package com.pi.tobeeb.Repositorys;

import java.util.List;
import java.util.Optional;


import java.util.Set;

import com.pi.tobeeb.Entities.Comment;
import com.pi.tobeeb.Entities.ERole;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByIdUser(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    User  findByVerificationToken(String Token);

    List <User> findByRoleName(ERole name);

    public User findByEmail(String UserEmail);
    @Query("SELECT u FROM User u WHERE u.isverified = 0")
    List<User> findUnverifiedUsers();
    @Query("SELECT u FROM User u WHERE u.isverified = 1")
    List<User> findVerifiedUsers();

    public User findByphonenumber(String phone);

    @Modifying
    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    public void updateFailedAttempts(int failAttempts, String username);


}
