package se.jensen.niclas.springbootrestapi.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.niclas.springbootrestapi.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of UserDetails to represent application users.
 * This class adapts the User entity to the UserDetails interface used by Spring Security.
 */
public class MyUserDetails implements UserDetails {
    private final User user;


    /**
     * Constructor to initialize MyUserDetails with a User entity.
     *
     * @param user the User entity
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return Long representing the user's primary key
     */
    public Long getId() {
        return user.getId();
    }


    /**
     * Gets the authorities granted to the user.
     * Spring Security uses this information for authorization decisions.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Getting the hashed password of the user.
     *
     * @return the hashed password
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * Getting the username of the user
     * that is used for authentication.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
