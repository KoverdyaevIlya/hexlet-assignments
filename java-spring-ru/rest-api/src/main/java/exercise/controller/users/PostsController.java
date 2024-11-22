package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

// BEGIN
@RestController()
@RequestMapping("/api/users")
public class PostsController {

    private final List<Post> posts = new ArrayList<Post>();

    @GetMapping("/{id}/posts")
    public List<Post> getPostsByUserId(@PathVariable int id) {
        return posts.stream().filter(p -> p.getUserId() == id).toList();
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post, @PathVariable int id) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }

}


// END
