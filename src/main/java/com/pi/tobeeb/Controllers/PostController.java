package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Post;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.PostRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import com.pi.tobeeb.Utils.BadWordFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@RestController
@Slf4j
@RequestMapping(value="/post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    PostRepository repo;
    @Autowired
    UserRepository userRepo;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post, @RequestParam("userid") Long userid ) {

        User user = userRepo.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        post.setCreatedAt(currentTimestamp);
        BadWordFilter.filter(post);
        return repo.save(post);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Post editPost(@RequestBody Post new_post) {
        Post old_post = repo.findById(new_post.getIdPost()).get();
        old_post.setContentPost(new_post.getContentPost());
        old_post.setNamePost(new_post.getNamePost());
        old_post.setImageUrl(new_post.getImageUrl());
        return repo.save(old_post);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public void removePost(@RequestParam("id") Long postid) {
        Post post = repo.findById(postid).orElse(null);
        repo.delete(post);
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Post getPost(@RequestParam(name = "id") Long id) {
        return repo.findById(id).get();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Post> getPost() {
        return repo.findAll();
    }


    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public Iterable<Post> getPostsByUser(@RequestParam("iduser") Long iduser) {

        return repo.findPostsByUser(iduser);
        
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String searchTerm) {
        List<Post> posts = repo.findByNamePostContainingOrContentPostContaining(searchTerm, searchTerm);

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }
    @GetMapping("/sorted-by-reactions")
    public List<Post> getPostsSortedByReactions() {
        List<Object[]> postsAndReactions = repo.findPostsOrderByReactionsDesc();
        List<Post> sortedPosts = new ArrayList<>();
        for (Object[] postAndReaction : postsAndReactions) {
            Post post = (Post) postAndReaction[0];
            sortedPosts.add(post);
        }
        return sortedPosts;
    }


}

