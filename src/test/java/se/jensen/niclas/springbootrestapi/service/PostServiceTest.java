package se.jensen.niclas.springbootrestapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.mapper.PostMapper;
import se.jensen.niclas.springbootrestapi.model.Post;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.PostRepository;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Spy
    private PostMapper postMapper;

    @Test
    public void testCreatePost_shouldReturnCreatedPost() {
        // Arrange
        User user = new User();
        user.setUsername("testuser1");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Post post = new Post();
        post.setId(10L);
        post.setText("test post");
        post.setUser(user);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostRequestDTO postDto = new PostRequestDTO("test post");

        // Act
        PostResponseDTO createdPost = postService.createPost(1L, postDto);

        // Assert
        assertEquals("test post", createdPost.text());
    }

    @Test
    public void testGetPostById_shouldReturnPost() {
        // Arrange
        User user = new User();
        user.setUsername("testuser1");
        user.setId(1L);

        Post post = new Post();
        post.setText("test post");
        post.setId(10L);
        post.setUser(user);

        when(postRepository.findById(10L)).thenReturn(Optional.of(post));

        // Act
        PostResponseDTO existingPost = postService.getPostsById(10L);

        // Assert
        assertEquals("test post", existingPost.text());
    }

    @Test
    public void testUpdatePost_shouldReturnUpdatedPost() {
        // Arrange
        User user = new User();
        user.setUsername("testuser1");
        user.setId(1L);

        Post post = new Post();
        post.setText("old text");
        post.setId(10L);
        post.setUser(user);

        when(postRepository.findById(10L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostRequestDTO postDto = new PostRequestDTO("updated text");

        // Act
        PostResponseDTO updatedPost = postService.updatePost(10L, postDto);

        // Assert
        assertEquals("updated text", updatedPost.text());
    }

    @Test
    public void testDeletePost_shouldDeletePostFromRepository() {
        // Arrange
        Long postId = 1L;
        when(postRepository.existsById(postId)).thenReturn(true);

        // Act
        postService.deletePost(postId);

        // Assert
        verify(postRepository, times(1)).existsById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }
}
