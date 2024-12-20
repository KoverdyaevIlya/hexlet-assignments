package exercise;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

import static java.util.stream.Collectors.toCollection;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(posts.size())).body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> getPost(@PathVariable String id) {
        Optional<Post> optionalPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();


        return ResponseEntity.status(optionalPost.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(optionalPost);

    }

    @PostMapping("/posts")
    @ResponseStatus(CREATED)
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(CREATED).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {

        Optional<Post> optionalPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        posts = posts.stream().map(p -> p.getId().equals(id) ? post : p).collect(toCollection(ArrayList::new));

        return ResponseEntity.status(optionalPost.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(post);

    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
