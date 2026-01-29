package se.jensen.niclas.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.niclas.springbootrestapi.model.Post;

import java.util.List;

/**
 * A interface for Post entities
 */

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * A list that puts the posts in an newest-to-oldest order.
     *
     * @return the list
     */

    List<Post> findAllByOrderByCreatedAtDesc();
}
