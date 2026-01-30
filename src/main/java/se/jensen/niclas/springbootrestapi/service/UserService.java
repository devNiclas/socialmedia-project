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

/**
 * This class contains the business logic for performing CRUD operations related to user
 */
@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * @param repo interface for executing database queries on user table
     * @param userMapper used for converting  user Dto to model and model to Dto
     * @param postMapper used for converting  post Dto to model and model to Dto
     * @param passwordEncoder use to encrypt user password before storing to database
     */
    public UserService(UserRepository repo, UserMapper userMapper, PostMapper postMapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all users from the system
     * This method is restricted to users with the ADMIN role
     * @return List of all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repo.findAll();
        return users.stream()
                .map(userMapper::toDTO)  // Mappa till DTO med streams API
                .toList();

    }

    /**
     * Register new user
     * @param dto contain information need to create a new user
     * @return created user
     * @throws IllegalArgumentException if user or email already exists
     */
    public UserResponseDTO addUser(UserRequestDTO dto) {
        boolean exists = repo.existsByUsernameOrEmail(dto.username(), dto.email());
        if (exists) {
            logger.warn("Failed adding user! Username ({}) or email ({}) already exists", dto.username(), dto.email());
            throw new IllegalArgumentException("User or email already exists");
        }
        User user = userMapper.fromDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = repo.save(user);
        return userMapper.toDTO(saved);

    }

    /**
     * This method is restricted to users with the ADMIN role
     * @param id user ID
     * @return UserResponseDTO
     * @throws UsernameNotFoundException if could not find user with ID
     */
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Failed getting user information! Could not find user with ID {}", id);
                    return new UsernameNotFoundException("User not found: " + id);
                });

        return userMapper.toDTO(user);
    }


    /**
     *
     * Retrieves a user together with all posts created by that user
     * @param id user ID
     * @return UserWithPostsResponseDTO containing the user data and a list of their posts
     * @throws NoSuchElementException if no user exists with the given ID
     */
    public UserWithPostsResponseDTO getUserWithPosts(Long id) {
        User user = repo.findUserWithPosts(id)
                .orElseThrow(() -> {
                    logger.warn("Failed getting user with posts! Could not find user with ID {}", id);
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


    /**
     *
     *  Updates an existing user's information
     *  @param id user Id
     * @param dto this contains information tobe updated
     * @returnupdated user as a UserResponseDTo
     * @throws NoSuchElementException if no user with the given ID is found
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Failed updating user! Could not find user with ID {}", id);
                    return new NoSuchElementException("User with ID: " + id + " was not found");
                });
        userMapper.fromDto(existingUser, dto);
        User saved = repo.save(existingUser);

        return userMapper.toDTO(saved);
    }

    /**
     * Used to delete user with a given ID
     * @param id user ID
     * @throws NoSuchElementException if no user with the given ID is found
     */
    public void deleteUser(Long id) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Failed deleting user! Could not find user with ID {}", id);
                    return new NoSuchElementException("User with ID: " + id + " was not found");
                });
        repo.delete(existingUser);


    }

    /**
     * @param username
     * @return USER NAME OF THE USER
     * @throws  UsernameNotFoundException if no user with the given user name is found
     */
    public UserResponseDTO getUserByUsername(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("Failed getting user! Could not find user with username {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        return userMapper.toDTO(user);

    }
}
