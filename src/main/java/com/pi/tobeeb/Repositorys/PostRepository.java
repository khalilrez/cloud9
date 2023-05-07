package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    /*
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.idPost = :id")
    Optional<Post> findByIdWithComments(@Param("id") Long id);*/
    @Query(" SELECT p FROM Post p LEFT JOIN p.user u WHERE u.idUser = :iduser")
    Set<Post> findPostsByUser(@Param("iduser") Long iduser);
    @Query("SELECT p, COUNT(r) as reaction_count FROM Post p LEFT JOIN p.reactions r GROUP BY p ORDER BY reaction_count DESC")
    List<Object[]> findPostsOrderByReactionsDesc();


    List<Post> findByNamePostContainingOrContentPostContaining(String namePost, String contentPost);

    }


