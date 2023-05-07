package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Reaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReactionRepository extends CrudRepository<Reaction, Long> {
    @Query("SELECT r FROM Reaction r LEFT JOIN FETCH r.post WHERE r.post.idPost = :id")
    public Iterable<Reaction> findAllByPostId(@Param("id") Long id);
}
