package com.pi.tobeeb.Controllers;


import com.pi.tobeeb.Entities.Message;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.MessageRepository;
import com.pi.tobeeb.Services.MessageService;
import com.pi.tobeeb.Services.UserService;
import org.apache.logging.log4j.core.config.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;


@Controller
public class MessageController
{   private UserService userService;
    private MessageRepository messageRepository;
    private MessageService messageService;



    @PostMapping("/messagee")
    public List<String> createMessage(@RequestBody Message message) {
        {
            List<String> motsARechercher = Arrays.asList("merde", "bordel", "mot3");
            String texte = message.getContent();
           // String email=iMicroGrowth.getCurrentUserName();
            for (String mot : motsARechercher) {
                if (texte.contains(mot)) {
                    message.setSentAt(LocalDateTime.now());
                    message.setContent("*****(Attention message cencuré)");
                    //message.getSender().setEmail(email);
                    messageService.save(message);

                    // return ResponseEntity.badRequest().body("message censuré");
                }
            }}
        message.setSentAt(LocalDateTime.now());
        messageService.save(message);
        List<Message> messages = messageService.findBySenderAndRecipient(message.getSender(), message.getRecipient());

        messages.addAll(messageService.findBySenderAndRecipient(message.getRecipient(), message.getSender()));
        Collections.sort(messages, Comparator.comparing(Message::getSentAt));
        List<String> s=new ArrayList<>();
        for (Message m:messages) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.of(m.getSentAt().getYear(), m.getSentAt().getMonth(), m.getSentAt().getDayOfMonth(), m.getSentAt().getHour(), m.getSentAt().getMinute());
            String formattedDateTime = dateTime.format(formatter);
            s.add(m.getRecipient().getUsername());
            s.add(m.getContent());
            s.add(formattedDateTime);
        }
        return s;}
    // }


    @GetMapping("/messagees/{sender}/{recipient}")
    public List<String> getMessages(@PathVariable String sender, @PathVariable String recipient) {
        User senderUser = userService.findByUsername(sender);
        User recipientUser = userService.findByUsername(recipient);
        List<Message> messages = messageService.findBySenderAndRecipient(senderUser, recipientUser);

        messages.addAll(messageService.findBySenderAndRecipient(recipientUser, senderUser));
        Collections.sort(messages, Comparator.comparing(Message::getSentAt));
        List<String> s=new ArrayList<>();
        for (Message m:messages) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.of(m.getSentAt().getYear(), m.getSentAt().getMonth(), m.getSentAt().getDayOfMonth(), m.getSentAt().getHour(), m.getSentAt().getMinute());
            String formattedDateTime = dateTime.format(formatter);

            s.add(m.getContent());
            s.add(formattedDateTime);
        }
        return s;
    }







    @GetMapping("/messagees/{recipient}")
    public List<Message> getMessagesByRecipient(@PathVariable String recipient) {
        User recipientUser = userService.findByUsername(recipient);
        return messageService.findByRecipient(recipientUser);
    }

    @PostMapping("/messageezz")
    public Message createMessage1(@RequestBody Message message) {
        //if (profanityDetector.containsProfanity(message.getContent())) {
        //  message.setContent("Message censuré");
        //}
        message.setSentAt(LocalDateTime.now());
        return messageService.save(message);
        //return ResponseEntity.ok().build();}
    }



   @MessageMapping ("/message")
    @SendTo("/topic/return-to")
    public Message getContent(@RequestBody Message message){
        //  try
        //  {
        //  Thread.sleep(2000);

        // }catch(InterruptedException e ){
        //   e.printStackTrace();
        //}

        return message;
    }

}
