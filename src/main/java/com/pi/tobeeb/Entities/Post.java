package com.pi.tobeeb.Entities;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Long idPost;

    @Column(name = "name_post", nullable = false)
    private String namePost;

    @Column(name = "content_post", nullable = false)
    private String contentPost;


    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;


    @Column(name = "created_at",nullable = false)
    private java.sql.Timestamp createdAt;


   @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
   private List<Reaction> reactions;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private double sentimentScore;


    public Post(Long idPost, String namePost, String contentPost, Timestamp createdAt ) {

        this.idPost = idPost;
        this.namePost = namePost;
        this.contentPost = contentPost;
        this.imageUrl = imageUrl;


    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // constructors, getters, and setters
    public Post() {
    }

    public Post(String namePost, String contentPost) {
        this.namePost = namePost;
        this.contentPost = contentPost;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSentimentScore(double averageSentiment) {
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

}
