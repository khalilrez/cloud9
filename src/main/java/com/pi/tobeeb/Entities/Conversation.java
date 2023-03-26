package com.pi.tobeeb.Entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Conversation {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
