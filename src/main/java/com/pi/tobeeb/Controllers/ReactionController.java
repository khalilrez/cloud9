package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Reaction;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.PostRepository;
import com.pi.tobeeb.Repositorys.ReactionRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/react")
public class ReactionController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    ReactionRepository repo;
    @Autowired
    PostRepository postRepo;

        @RequestMapping(value="/create/{post_id}/user/{user_id}",method = RequestMethod.POST)
        public ResponseEntity<Reaction> createReaction(@RequestBody Reaction react, @PathVariable("post_id") Long idPost, @PathVariable("user_id") Long userId) {
            Post post = postRepo.findById(idPost).orElse(null);
            User user = userRepo.findById(userId).orElse(null);
            System.out.println(post);
            if(post!=null && user != null){
                react.setPost(post);
                react.setUser(user);
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
