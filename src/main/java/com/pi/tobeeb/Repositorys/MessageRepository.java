package com.pi.tobeeb.Repositorys;


import com.pi.tobeeb.Entities.Message;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
   List<Message> findBySenderAndRecipient(User sender, User recipient);
    List<Message> findByRecipient(User recipient);

}