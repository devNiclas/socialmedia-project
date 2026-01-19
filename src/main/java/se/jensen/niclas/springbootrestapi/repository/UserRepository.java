package se.jensen.niclas.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.jensen.niclas.springbootrestapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts p WHERE u.id = :id")
    Optional<User> findUserWithPosts(Long id);

    Optional<User> findByUsername(String username);
}
