package se.jensen.niclas.springbootrestapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;
import se.jensen.niclas.springbootrestapi.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final List<Post> posts = new ArrayList<>();

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable int id) {
        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.notFound().build();
        }
        Post p = posts.get(id);

        PostResponseDTO response =
                new PostResponseDTO(
                        0L,
                        p.getText(),
                        p.getCreatedAt()
                );
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable int id,
            @Valid @RequestBody PostRequestDTO dto) {

        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.notFound().build();
        }

        Post post = posts.get(id);
        post.setText(dto.text());

        PostResponseDTO response = new PostResponseDTO(
                0L,
                post.getText(),
                post.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.notFound().build();
        }
        posts.remove(id);
        return ResponseEntity.noContent().build();
    }


}
