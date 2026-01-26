package se.jensen.niclas.springbootrestapi.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class representing a user in the system.
 * Includes user details and a list of posts created by the user.
 */
@Entity
@Table(name = "app_user")
public class User {
    /**
     * A list of posts created by this user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    /**
     * Unique ID for the user. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * The display name of the user in the application.
     */
    @Column(name = "display_name")
    private String displayName;

    /**
     * the unique username of the user.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The password for the user account
     */
    @Column(nullable = false)
    private String password;

    /**
     * Email address of the user.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Biography or description of the user.
     */
    @Column(nullable = false)
    private String bio;

    /**
     * Users role in the application (example: USER, ADMIN)
     */
    @Column(nullable = false)
    private String role;

    /**
     * Path to the user's profile image.
     */
    @Column(name = "profile_image_path")
    private String profileImagePath;

    /**
     * Constructor for creating a User with all the main fields that are required.
     *
     * @param id       unique identifier of the user
     * @param username unique username used for login
     * @param password the hashed password of the user
     * @param role     the security role assigned to the user (example: USER, ADMIN)
     */
    public User(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Default constructor for JPA.
     */
    public User() {

    }

    /**
     * Constructor for creating a User with username, password and role.
     *
     * @param username unique username used for login
     * @param password the hashed password of the user
     * @param role     the security role assigned to the user (example: USER, ADMIN)
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
