package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Reaction;
import com.pi.tobeeb.Repositorys.PostRepository;
import com.pi.tobeeb.Repositorys.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/react")
public class ReactionController {
    @Autowired
    ReactionRepository repo;
    @Autowired
    PostRepository postRepo;

        @RequestMapping(value="/create/{post_id}",method = RequestMethod.POST)
        public ResponseEntity<Reaction> createPost(@RequestBody Reaction react, @PathVariable("post_id") Long idPost){
            Post post = postRepo.findById(idPost).orElse(null);
            System.out.println(post);
            if(post!=null){
                react.setPost(post);
                repo.save(react);
                return new ResponseEntity<>(react, HttpStatus.CREATED);}
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        @RequestMapping(value="/bypost",method = RequestMethod.GET)
        public Iterable<Reaction> findByPost(@RequestParam("post_id") Long postId){
           return repo.findAllByPostId(postId);
        }
        @RequestMapping(value="/remove",method = RequestMethod.DELETE)
        public void removeReact(@RequestParam("id") Long reactId){
            Reaction react = repo.findById(reactId).orElse(null);
            repo.delete(react);
        }



    }
