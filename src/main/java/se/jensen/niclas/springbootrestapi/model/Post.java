package se.jensen.niclas.springbootrestapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a post in the system.
 * Each post has a unique ID, text content, creation timestamp, and is associated with a User.
 */
@Entity
public class Post {

    /**
     * Unique ID for the post. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Text content of the post. Cannot be empty.
     */
    @Column(nullable = false)
    private String text;

    /**
     * Timestamp of when the post was created. Set to the current time by default.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The user who created the post. Many posts can be associated with one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Constructor to create a Post with specified ID and text.
     *
     * @param id   the unique identifier of the post
     * @param text the content of the post
     */
    public Post(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * Constructor to create a Post with specified text.
     * The timestamp is automatically set to the current time.
     *
     * @param text the content of the post
     */
    public Post(String text) {
        this.text = text;

    }

    /**
     * Default constructor for JPA.
     */
    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
