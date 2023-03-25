package com.pi.tobeeb.Entities;



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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post")
    private Post post;

    @Column(name = "date_comment", nullable = false)
    private Date dateComment;



    public Comment() {

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
