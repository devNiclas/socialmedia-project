package se.jensen.niclas.springbootrestapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.service.PostService;

import java.util.List;

/**
 * This controller handles CRUD operations for posts.
 * It contains endpoints for HTTP request related to handling post
 * Receives requests from the client
 * Extracts user info from JWT
 * Calls PostService and returns JSON responses
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * @param postService
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }


    /**
     * This endpoint handles HTTP GET requests
     * Show all posts from the  global feed
     * Call the post service to get the global feed.
     * @return a list of post in a PostResponseDTO
     * HTTP status
     * Send status OK when the request is successful
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getGlobalFeed() {
        List<PostResponseDTO> response = postService.getGlobalFeed();

        return ResponseEntity.ok(response);
    }

    /**
     * This endpoint handles HTTP POST requests
     * It let the user create new post
     * The authenticated user's details are checked before creating  new post
     * @param dto the request body containing post creation data
     * @param authentication  object holding the JWT token
     * @return a ResponseEntity containing the created PostResponseDTO containing the created post data with the status CREATED
     */
    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(
            @Valid @RequestBody PostRequestDTO dto,
            Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId, dto));
    }

    /**
     * This method let the user find a post by post ID
     * The ID received as a path variable
     * @param id post Id
     * @return If the post is found method returns post details in a ResponseEntity with status OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable int id) {
        Long postId = (long) id; //converted to long before being passed to the service layer
        return ResponseEntity.ok(postService.getPostsById(postId));
    }

    /**
     * This method use to Update an existing post identified by its ID.
     * This endpoint handles HTTP PUT requests to update a post
     * The post ID is provided as a path variable
     * @param id  ID of the post to update
     * @param dto the request body containing updated post data
     * @return a ResponseEntity containing the created PostResponseDTO containing the updated post data with the status ok
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable int id,
            @Valid @RequestBody PostRequestDTO dto) {
        Long postId = (long) id;
        return ResponseEntity.ok(postService.updatePost(postId, dto));
    }

    /**
     * This method use to delete an existing post identified by its ID.
     * This endpoint handles HTTP DELETE requests
     * The post ID is provided as a path variable
     * @param id ID of the post to delete
     * @return ResponseEntity containing the created PostResponseDTO containing the deleted post data with the status ok
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        Long postId = (long) id;
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }


}
