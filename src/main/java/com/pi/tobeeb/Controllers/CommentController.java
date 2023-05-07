package com.pi.tobeeb.Controllers;
import com.pi.tobeeb.Entities.Comment;
import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.Reaction;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.CommentRepository;
import com.pi.tobeeb.Repositorys.PostRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Utils.BadWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;
@RestController
@RequestMapping(value="/comment")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    CommentRepository repo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value="/create/{post_id}",method = RequestMethod.POST)
    public ResponseEntity<Comment> createComment(@RequestBody Comment commentRequest, @PathVariable(value = "post_id") Long postId, @RequestParam("userid") Long userid){
        User user = userRepo.findById(userid).orElse(null);
        Post post = postRepo.findById(postId).orElse(null);
        if(post!=null ){
        commentRequest.setPost(post);
        commentRequest.setUser(user);
        commentRequest.setDateComment(new Date((new java.util.Date()).getTime()));
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            commentRequest.setCreatedAt(currentTimestamp);
            BadWordFilter.filter(commentRequest);

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


///////////////////////////////user/////////////////

    @RequestMapping(value="/byuser" ,method = RequestMethod.GET)
    public Set<Comment> getCommentsByUser(@RequestParam("iduser") Long iduser){

        return repo.findCommentsByUser(iduser);
    }


}
