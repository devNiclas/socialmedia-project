package se.jensen.niclas.springbootrestapi.service;

import org.springframework.stereotype.Service;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.PostRepository;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class PostService {
    UserRepository userRepo;
    PostRepository postRepo;

    public PostService(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    public PostResponseDTO createPost(Long userId, PostRequestDTO postDto) {
        Post post = new Post();
        post.setText(postDto.text());
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID: " + userId + " " + "was not found"));

        post.setUser(user);
        Post savedPost = postRepo.save(post);

        return new PostResponseDTO(savedPost.getId(), savedPost.getText(), savedPost.getCreatedAt());
    }
}