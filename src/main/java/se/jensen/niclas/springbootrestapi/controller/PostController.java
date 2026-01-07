package se.jensen.niclas.springbootrestapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final List<Post> posts = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> response =
                posts.stream()
                        .map(post -> new PostResponseDTO(
                                post.getId(),
                                post.getText(),
                                post.getCreatedAt()))
                        .toList();


        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(
            @Valid @RequestBody PostRequestDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setId(0L);
        post.setText(dto.text());
        post.setCreatedAt(now);

        posts.add(post);

        PostResponseDTO response =
                new PostResponseDTO(post.getId(),
                        post.getText(), post.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

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
