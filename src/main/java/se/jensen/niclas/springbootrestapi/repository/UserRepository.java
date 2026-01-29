package se.jensen.niclas.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.jensen.niclas.springbootrestapi.model.User;

import java.util.Optional;

/**
 * Interface for User entities.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * A method to see if a username or email exists or not
     *
     * @param username the username
     * @param email    the email
     * @return true or false, depending on if we find the param we are looking for
     */

    boolean existsByUsernameOrEmail(String username, String email);

    /**
     * A method to find both a user (with the id) and their posts
     *
     * @param id the users id
     * @return the user and their posts, or that we didn't find it, if they dont exist
     */

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts p WHERE u.id = :id")
    Optional<User> findUserWithPosts(Long id);

    /**
     * A method to find the user by their username
     *
     * @param username the username
     * @return that we could find the user, or that we couldn't find the user
     */

    Optional<User> findByUsername(String username);
}
