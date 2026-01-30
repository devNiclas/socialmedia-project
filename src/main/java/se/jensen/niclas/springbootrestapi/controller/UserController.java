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

/**
 * This controller handles CRUD operations for user.
 * It contains endpoints for HTTP request related to handling user
 * Receives requests from the client
 * Calls UserService and  post service and returns JSON responses
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    /**
     * Constructor for creating UserController.
     * @param userService service for handling operations related to users.
     * @param postService service for handling operations related to posts.
     */
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    /**
     * This endpoint handles HTTP GET requests for get all users
     * Call the user service to get the user details
     * @return a list of users in a UserResponseDTO
     * Send status OK when the request is successful
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    /**
     * @param id User Id
     * @return user in a UserResponseDTO
     * Send status OK when the request is successful
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * @param id user id
     * @return user with posts in a UserWithPostResponseDTO
     */
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

    /**
     * This endpoint handles HTTP POST requests to registers a new user in the system
     * @param dto containing user registration data
     * @return UserResponseDTO containing the created user’s information,
     * Send status CREATED when the request is successful
     */
    @PermitAll // It is accessible to all clients without authentication
    @PostMapping()
    public ResponseEntity<UserResponseDTO> addUser(
            @RequestBody UserRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
    }

    /**
     *
     * Creates a new post for a specific user
     * @param userId USER ID of the user for whom the post is being created
     * @param request  containing the post creation data
     * @return ResponseEntity containing the created PostResponseDTO
     * Send status CREATED when the request is successful
     */
    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDTO> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDTO request) {
        PostResponseDTO postResponse = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);


    }


    /**
     *
     * Updates an existing user's information
     * @param id user ID
     * @param dto containing updated user data
     * @return returns a UserResponseDTO containing the updated user information,
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok().body(userService.updateUser(id, dto));
    }


    /**
     * Delete an existing user
     * This endpoint handles HTTP DELETE requests to remove a user from the system
     * @param id user id
     * @return ResponseEntity with HTTP status No Content, indicating that the request was successful and there is no response body.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();

    }


}
