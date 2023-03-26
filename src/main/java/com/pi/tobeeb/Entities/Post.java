package com.pi.tobeeb.Entities;


import javax.persistence.*;
import java.sql.Date;
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

    /*@Column(name = "date_post", nullable = true)
    private Date datePost;*/


/*    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
*/
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "id_user")
    private User user;*/


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
}
