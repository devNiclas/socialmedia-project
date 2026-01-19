package se.jensen.niclas.springbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.niclas.springbootrestapi.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
