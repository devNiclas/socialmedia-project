package se.jensen.niclas.springbootrestapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
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


    @GetMapping("/feed")
    public ResponseEntity<List<PostResponseDTO>> getGlobalFeed() {
        List<PostResponseDTO> response = postService.getGlobalFeed();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(
            @Valid @RequestBody PostRequestDTO dto,
            Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId, dto));
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
                        p.getCreatedAt(),
                        null
                );
        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable int id,
            @Valid @RequestBody PostRequestDTO dto) {
        Long postId = (long) id;
        return ResponseEntity.ok(postService.updatePost(postId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        Long postId = (long) id;
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }


}
