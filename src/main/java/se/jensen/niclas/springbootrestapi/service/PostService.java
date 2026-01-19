package se.jensen.niclas.springbootrestapi.service;

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

@Service
public class PostService {
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final PostMapper postMapper;

    public PostService(UserRepository userRepo, PostRepository postRepo, PostMapper postMapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.postMapper = postMapper;
    }

    public List<PostResponseDTO> getGlobalFeed() {
        List<Post> posts = postRepo.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public PostResponseDTO createPost(Long userId, PostRequestDTO postDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + "was not found "));

        Post post = postMapper.fromDTO(postDto);

        post.setUser(user);

        Post savedPost = postRepo.save(post);

        return postMapper.toDTO(savedPost);
    }

    public PostResponseDTO getPostsById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        return postMapper.toDTO(post);


    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO dto) {
        Post existingPost = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        postMapper.fromDTO(existingPost, dto);

        Post updatedPost = postRepo.save(existingPost);
        return postMapper.toDTO(updatedPost);

    }

    public void deletePost(Long id) {
        if (!postRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postRepo.deleteById(id);
    }
}