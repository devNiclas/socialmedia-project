package se.jensen.niclas.springbootrestapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.niclas.springbootrestapi.dto.UserResponseDTO;
import se.jensen.niclas.springbootrestapi.mapper.UserMapper;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

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
}
