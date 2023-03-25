package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Resource
public class PostService {


    @Autowired
    PostRepository repo;


    public Post create(Post post){
        return repo.save(post);
    }



}
