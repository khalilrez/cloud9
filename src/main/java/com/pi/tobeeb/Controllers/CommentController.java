package com.pi.tobeeb.Controllers;
import com.pi.tobeeb.Entities.Comment;
import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Reaction;
import com.pi.tobeeb.Repositorys.CommentRepository;
import com.pi.tobeeb.Repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@RestController
@RequestMapping(value="/comment")
public class CommentController {

    @Autowired
    CommentRepository repo;

    @Autowired
    PostRepository postRepo;



/*
    @RequestMapping(value="/post",method = RequestMethod.GET)
    public String helloWorld(@RequestParam(name = "sofien") String name, @RequestParam(name = "hedi") String lastname){
        return "\nHello World from Spring Boot"+ name + lastname;
    }
    @RequestMapping(value="/post",method = RequestMethod.POST)
    public String helloWorldPost(@RequestBody Post post){
        return "\nHello World from Spring Boot POST " + post.getNamePost();
    }
    */
    @RequestMapping(value="/create/{post_id}",method = RequestMethod.POST)
    public ResponseEntity<Comment> createComment(@RequestBody Comment commentRequest, @PathVariable(value = "post_id") Long postId){
        Post post = postRepo.findById(postId).orElse(null);
        if(post!=null ){
            /*
            commentRequest.setUser(user);
 */


        commentRequest.setPost(post);
        commentRequest.setDateComment(new Date((new java.util.Date()).getTime()));
        repo.save(commentRequest);
        return new ResponseEntity<>(commentRequest, HttpStatus.CREATED);}
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    public Comment editComment(@RequestBody Comment new_comment){
        Comment old_comment = repo.findById(new_comment.getIdComment()).get();
        old_comment.setContent(new_comment.getContent());
        return repo.save(old_comment);
    }
    @RequestMapping(value="/bypost",method = RequestMethod.GET)
    public Iterable<Comment> findByPost(@RequestParam("post_id") Long postId){
        return repo.findAllByPostId(postId);
    }
    @RequestMapping(value="/remove",method = RequestMethod.DELETE)
    public void removeComment(@RequestParam("id") Long commentId)
    { Comment comment = repo.findById(commentId).orElse(null);
       repo.delete(comment);
    }
    @RequestMapping(value="/read" ,method = RequestMethod.GET)
    public Comment getComment(@RequestParam(name = "id") Long id){
        return repo.findById(id).get();
    }

    @RequestMapping(value="/all" ,method = RequestMethod.GET)
    public Iterable<Comment> getComment(){
        return repo.findAll();
    }


///////////////////////////////comment/////////////////

}
