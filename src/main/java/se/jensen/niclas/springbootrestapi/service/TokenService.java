package se.jensen.niclas.springbootrestapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import se.jensen.niclas.springbootrestapi.model.User;
import se.jensen.niclas.springbootrestapi.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * This class handles  JWT tokens
 */
@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    /**
     * Constructor for the Token service
     * @param jwtEncoder This is from Spring Security.It converts data to a signed JWT token
     * @param userRepository Used to fetch the user from the database.
     */
    public TokenService(JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    /**
     * This method generates a JWT token
     * @param authentication represents the currently logged-in user
     * @return JWT token as a string
     * @throws IllegalStateException if no user is found with the given username
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();  //Get current time

        String username = authentication.getName(); //get user name
        User user = userRepository.findByUsername(username) //fetch user from the database
                .orElseThrow(() -> {
                    logger.warn("Failed generating token! Could not find user with username {}", username);
                    return new IllegalStateException("User not found");
                });

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder() //This is the data inside the token.
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("userId", user.getId())
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();

    }
}
