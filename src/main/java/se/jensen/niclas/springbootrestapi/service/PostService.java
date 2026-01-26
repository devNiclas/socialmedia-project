package se.jensen.niclas.springbootrestapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.mapper.PostMapper;
import se.jensen.niclas.springbootrestapi.model.Post;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.PostRepository;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *  This class contains the business logic for performing CRUD operations
 *  (Create, Read, Update, Delete) on posts.
 */
@Service
public class PostService {
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final PostMapper postMapper;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    /**
     * Constructor for post service
     * @param userRepo repository for managing user
     * @param postRepo repository for managing posts
     * @param postMapper helper for mapping between dto and entities
     */
    public PostService(UserRepository userRepo, PostRepository postRepo, PostMapper postMapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.postMapper = postMapper;
    }

    /**
     * Request global feed of posts
     * @return list of posts sorted in descending order by created date
     */
    public List<PostResponseDTO> getGlobalFeed() {
        List<Post> posts = postRepo.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(postMapper::toDTO)
                .toList();
    }

    /**
     * Create a new post on user's wall
     * @param userId userId of the user who creates the post
     * @param postDto the data transfer object containing the details required for creating the post
     * @return a data transfer object representing the saved post
     * @throws NoSuchElementException if no user exists with the given userId
     */
    public PostResponseDTO createPost(Long userId, PostRequestDTO postDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("Failed creating post! Could not find user with ID {}", userId);
                    return new NoSuchElementException("User with ID: " + userId + "was not found ");
                });

        Post post = postMapper.fromDTO(postDto);

        post.setUser(user);

        Post savedPost = postRepo.save(post);

        return postMapper.toDTO(savedPost);
    }

    /**
     * Get an existing post on user's wall
     * @param id post id of the post
     * @return post details
     * @throws ResponseStatusException if no post exists with the given postId
     */
    public PostResponseDTO getPostsById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Failed getting post! Could not find post with ID {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
                });

        return postMapper.toDTO(post);
    }

    /**
     * Update an existing post on user's wall
     * @param id post id of the post
     * @param dto post details that needs to be updated
     * @return updated post details
     * @throws ResponseStatusException if no post exists with the given postId
     */
    public PostResponseDTO updatePost(Long id, PostRequestDTO dto) {
        Post existingPost = postRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Failed updating post! Could not find post with ID {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
                });

        postMapper.fromDTO(existingPost, dto);

        Post updatedPost = postRepo.save(existingPost);
        return postMapper.toDTO(updatedPost);
    }

    /**
     * Delete an existing post on user's wall
     * @param id post id of the post
     * @throws ResponseStatusException if no post exists with the given postId
     */
    public void deletePost(Long id) {
        if (!postRepo.existsById(id)) {
            logger.warn("Failed deleting post! Could not find post with ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postRepo.deleteById(id);
    }
}