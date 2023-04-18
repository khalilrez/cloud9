package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Message;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.MessageRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    public List<Message> findBySenderAndRecipient(User sender, User recipient) {
        return messageRepository.findBySenderAndRecipient(sender, recipient);
    }
    public List<Message> findByRecipient(User recipient) {
        return messageRepository.findByRecipient(recipient);
    }


    public Message save(Message message) {
        return messageRepository.save(message);
    }



    public void sendMessage(long senderUsername, long receiverUsername, String content) {
        User sender = userRepository.findById(senderUsername).get();
        User receiver = userRepository.findById(receiverUsername).get();
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(receiver);
        message.setContent(content);
        messageRepository.save(message);
    }
}