package se.jensen.niclas.springbootrestapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.dto.UserRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.UserResponseDTO;
import se.jensen.niclas.springbootrestapi.dto.UserWithPostsResponseDTO;
import se.jensen.niclas.springbootrestapi.mapper.PostMapper;
import se.jensen.niclas.springbootrestapi.mapper.UserMapper;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repo, UserMapper userMapper, PostMapper postMapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repo.findAll();
        return users.stream()
                .map(userMapper::toDTO)  // Mappa till DTO med streams API
                .toList();

    }

    public UserResponseDTO addUser(UserRequestDTO dto) {
        boolean exists = repo.existsByUsernameOrEmail(dto.username(), dto.email());
        if (exists) {
            logger.error("Failed adding user! Username ({}) or email ({}) already exists", dto.username(), dto.email());
            throw new IllegalArgumentException("User or email already exists");
        }
        User user = userMapper.fromDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = repo.save(user);
        return userMapper.toDTO(saved);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed getting user information! Could not find user with ID {}", id);
                    return new UsernameNotFoundException("User not found: " + id);
                });

        return userMapper.toDTO(user);
    }


    public UserWithPostsResponseDTO getUserWithPosts(Long id) {
        User user = repo.findUserWithPosts(id)
                .orElseThrow(() -> {
                    logger.error("Failed getting user with posts! Could not find user with ID {}", id);
                    return new NoSuchElementException("User not found with ID: " + id);
                });
        List<PostResponseDTO> postDtos = user.getPosts()
                .stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .map(postMapper::toDTO)
                .toList();

        UserResponseDTO userDto = userMapper.toDTO(user);

        return new UserWithPostsResponseDTO(userDto, postDtos);
    }


    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed updating user! Could not find user with ID {}", id);
                    return new NoSuchElementException("User with ID: " + id + " was not found");
                });
        userMapper.fromDto(existingUser, dto);
        User saved = repo.save(existingUser);

        return userMapper.toDTO(saved);
    }

    public void deleteUser(Long id) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed deleting user! Could not find user with ID {}", id);
                    return new NoSuchElementException("User with ID: " + id + " was not found");
                });
        repo.delete(existingUser);


    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Failed getting user! Could not find user with username {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        return userMapper.toDTO(user);

    }
}
