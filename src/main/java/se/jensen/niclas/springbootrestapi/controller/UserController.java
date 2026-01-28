package se.jensen.niclas.springbootrestapi.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.niclas.springbootrestapi.dto.*;
import se.jensen.niclas.springbootrestapi.service.PostService;
import se.jensen.niclas.springbootrestapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDTO> getUserWithPosts(@PathVariable Long id) {
        UserWithPostsResponseDTO response = userService.getUserWithPosts(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    public UserResponseDTO getMe(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }

    @PermitAll
    @PostMapping()
    public ResponseEntity<UserResponseDTO> addUser(
            @RequestBody UserRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDTO> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDTO request) {
        PostResponseDTO postResponse = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);


    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok().body(userService.updateUser(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();

    }


}
