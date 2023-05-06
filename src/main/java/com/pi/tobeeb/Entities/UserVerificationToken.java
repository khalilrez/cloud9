package com.pi.tobeeb.Entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;


    private LocalDateTime expiryDate;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
    
    
}
