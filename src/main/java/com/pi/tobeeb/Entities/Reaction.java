package com.pi.tobeeb.Entities;

import com.pi.tobeeb.utils.ReactionType;

import javax.persistence.*;

@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReaction;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", referencedColumnName = "id_post")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public User getUser() {
        return user;
    }

    public Long getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(Long idReaction) {
        this.idReaction = idReaction;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;

    }
    public void setUser(User user) {
        this.user =user;


    }
}
