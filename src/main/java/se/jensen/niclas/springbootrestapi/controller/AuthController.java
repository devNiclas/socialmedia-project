package se.jensen.niclas.springbootrestapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.niclas.springbootrestapi.dto.LoginRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.LoginResponseDTO;
import se.jensen.niclas.springbootrestapi.security.MyUserDetails;
import se.jensen.niclas.springbootrestapi.service.TokenService;

/**
 * This class authenticate user credentials and generate JWT
 * This act as the login end point
 */
@RestController
@RequestMapping("/request-token")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * @param authenticationManager verifies username & password
     * @param tokenService creates JWT after login
     */
    public AuthController(AuthenticationManager authenticationManager,
                          TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Authenticate the user with provided user credentials and generate JWT
     * @param loginRequest containing the username and password
     * @return responseEntity with LoginResponseDTO} with the JWT token and the user ID
     */
    @PostMapping
    public ResponseEntity<LoginResponseDTO> token( @RequestBody LoginRequestDTO loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        MyUserDetails details = (MyUserDetails) auth.getPrincipal(); //After login user details are saved

        String token = tokenService.generateToken(auth); // generates the JWT

        return ResponseEntity.ok(new LoginResponseDTO(token, details.getId()));

    }


}
