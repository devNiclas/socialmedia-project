package se.jensen.niclas.springbootrestapi.service;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.niclas.springbootrestapi.dto.UserRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.UserResponseDTO;
import se.jensen.niclas.springbootrestapi.mapper.UserMapper;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repo.findAll();
        return users.stream()
                .map(userMapper::toDTO)  // Mappa till DTO med streams API
                .toList();

    }

    @PermitAll
    public UserResponseDTO addUser(UserRequestDTO dto) {
        boolean exists = repo.existsByUsernameOrEmail(dto.username(), dto.email());
        if (exists) {
            throw new IllegalArgumentException("Användarnamn eller email finns redan");
        }
        User user = userMapper.fromDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = repo.save(user);
        return userMapper.toDTO(saved);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användare hittades inte"));
        return userMapper.toDTO(user);
    }

//    public UserWithPostsResponseDTO getUserWithPosts(Long id) {
//        User user = repo.findUserWithPosts(id)
//                .orElseThrow(() -> new NoSuchElementException("Användare hittades inte med ID: " + id));
//        List<PostResponseDTO> posts = user.getPosts()
//                .stream()

    /// /                .map(p -> PostResponseDTO(
    /// /                        p.getId(),
    /// /                        p.getText(),
    /// /                        p.getCreatedAt()
    /// /
    /// /                ))
    /// /                .tolist();
    /// /    }


    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användare med ID: " + id + " hittades inte"));
        userMapper.fromDto(existingUser, dto);
        User saved = repo.save(existingUser);

        return userMapper.toDTO(saved);
    }

    public void deleteUser(Long id) {
        User existingUser = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användare med ID: " + id + " hittades inte"));
        repo.delete(existingUser);


    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Användare hittades inte:  " + username));

        return userMapper.toDTO(user);

    }
}
