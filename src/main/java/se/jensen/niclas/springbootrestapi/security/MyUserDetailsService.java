package se.jensen.niclas.springbootrestapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

/**
 * Service class that implements UserDetailsService to load user-specific data.
 * This class is used by Spring Security to retrieve user details during authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    /**
     * Constructor for MyUserDetailsService
     *
     * @param userRepo the repository used to fetch user data from the database
     */
    public MyUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Locates the user based on the username.
     * In this implementation, the user is fetched from the database
     * And then converted to a UserDetails object.
     *
     * @param username the username identifying the user whose data is required.
     * @return a UserDetails object that Spring Security uses for authentication and authorization.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found:" + username));
        return new MyUserDetails(user);

    }
}
