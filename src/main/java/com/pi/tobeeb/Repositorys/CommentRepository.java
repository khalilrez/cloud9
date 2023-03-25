package com.pi.tobeeb.Repositorys;
import com.pi.tobeeb.Entities.Comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
