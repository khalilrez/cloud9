package com.pi.tobeeb.Repositorys;
import com.pi.tobeeb.Entities.Comment;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post WHERE c.post.idPost = :id")
    public Iterable<Comment> findAllByPostId(@Param("id") Long id);
    @Query("SELECT c FROM Comment c WHERE c.user.idUser = :iduser")
    Set<Comment> findCommentsByUser(@Param("iduser") Long iduser);



}
