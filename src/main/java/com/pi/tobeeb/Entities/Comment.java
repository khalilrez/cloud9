package com.pi.tobeeb.Entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;





    public Comment() {

    }
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Comment(Long idComment) {
        this.idComment = idComment;
    }

    public Long getIdComment() {
        return idComment;
    }
}
