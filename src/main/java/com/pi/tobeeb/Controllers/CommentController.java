package com.pi.tobeeb.Controllers;
import com.pi.tobeeb.Entities.Comment;
import com.pi.tobeeb.Repositorys.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/comment")
public class CommentController {

    @Autowired
    CommentRepository repo;


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
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment){
          return repo.save(comment);
    }
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    public Comment editComment(@RequestBody Comment new_comment){
        Comment old_comment = repo.findById(new_comment.getIdComment()).get();
        old_comment.setContent(new_comment.getContent());
        return repo.save(old_comment);
    }
    @RequestMapping(value="/remove",method = RequestMethod.DELETE)
    public void removeComment(@RequestBody Comment comment){
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
