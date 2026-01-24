package se.jensen.niclas.springbootrestapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.niclas.springbootrestapi.dto.*;
import se.jensen.niclas.springbootrestapi.mapper.PostMapper;
import se.jensen.niclas.springbootrestapi.mapper.UserMapper;
import se.jensen.niclas.springbootrestapi.model.Post;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapper userMapper;

    @Spy
    private PostMapper postMapper;

    @Test
    public void testGetUserById() {
        // Arrange
        User user = new User();
        user.setUsername("testuser1");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO foundUser = userService.getUserById(1L);


        // Assert
        assertEquals("testuser1", foundUser.username());


    }

    @Test
    public void testGetAllUsers_shouldReturnAllUsers() {
        // Arrange

        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("testuser1@gmail.com");
        user1.setId(1L);

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("testuser2@gmail.com");
        user2.setId(2L);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponseDTO> allUsers = userService.getAllUsers();

        // Assert
        assertEquals("testuser1", allUsers.get(0).username());
        assertEquals("testuser2", allUsers.get(1).username());
    }

    @Test
    public void testAddUser_shouldReturnAddedUser() {
        // Arrange
        UserRequestDTO userDto = new UserRequestDTO("testuser1", "password", "testuser1@gmail.com", "USER", "Test User", "user description", "path to image");

        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@gmail.com");
        user.setId(1L);

        when(userRepository.existsByUsernameOrEmail(userDto.username(), userDto.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO addedUser = userService.addUser(userDto);

        // Assert
        assertEquals("testuser1", addedUser.username());
        assertEquals("testuser1@gmail.com", addedUser.email());
        assertEquals(1L, addedUser.id());
    }

    @Test
    public void testGetUserWithPosts_shouldReturnUserWithPosts() {
        // Arrange
        Post post1 = new Post();
        post1.setText("text 1");
        post1.setId(10L);

        Post post2 = new Post();
        post2.setText("text 2");
        post2.setId(20L);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@gmail.com");
        user.setPosts(posts);
        user.setId(1L);

        when(userRepository.findUserWithPosts(1L)).thenReturn(Optional.of(user));

        // Act
        UserWithPostsResponseDTO userWithPosts = userService.getUserWithPosts(1L);

        // Assert
        assertEquals("testuser1", userWithPosts.user().username());
        assertEquals("testuser1@gmail.com", userWithPosts.user().email());
        assertEquals("text 2", userWithPosts.posts().get(0).text());
        assertEquals("text 1", userWithPosts.posts().get(1).text());
    }

    @Test
    public void testUpdateUser_shouldReturnUpdatedUser() {
        // Arrange
        UserRequestDTO userDto = new UserRequestDTO("testuser1", "password", "testuser1@gmail.com", "USER", "Test User", "user description", "path to image");

        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@gmail.com");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO updatedUser = userService.updateUser(1L, userDto);

        // Assert
        assertEquals("testuser1", updatedUser.username());
        assertEquals("testuser1@gmail.com", updatedUser.email());
        assertEquals(1L, updatedUser.id());
    }

    @Test
    public void testDeleteUser_shouldDeleteUserFromRepository() {
        // Arrange
        Long userId = 1L;

        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@gmail.com");
        user.setId(1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testGetUserByUsername_shouldReturnUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser1");
        user.setId(1L);

        when(userRepository.findByUsername("testuser1")).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO foundUser = userService.getUserByUsername("testuser1");

        // Assert
        assertEquals(1L, foundUser.id());
    }
}
