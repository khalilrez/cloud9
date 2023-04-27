package com.pi.tobeeb.Entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name= "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long idComment;

    @Column(name = "content", nullable = false)
    private String content;


    @Column(name = "date_comment", nullable = false)
    private Date dateComment;



    @Column(name = "created_at",nullable = false)
    private java.sql.Timestamp createdAt;

    @ManyToOne()
    @JoinColumn(name = "id_post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

   /* @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;*/
    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

*/

    public Comment(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment() {

    }
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "idComment=" + idComment +
                ", content='" + content + '\'' +
                '}';
    }

    public Comment(Long idComment, String content, Date dateComment, Post post, User user) {
        this.idComment = idComment;
        this.content = content;
        this.dateComment = dateComment;
        this.post = post;
        this.user = user;
    }

    public Comment(Post post) {
        this.post = post;
    }

    public Comment(Long idComment) {
        this.idComment = idComment;
    }

    public Long getIdComment() {
        return idComment;
    }
}
