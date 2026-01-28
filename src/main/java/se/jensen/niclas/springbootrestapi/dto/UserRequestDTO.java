package se.jensen.niclas.springbootrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * Record of a request when a user is registering their account
 *
 * @param username         username needs to have 3 - 30 characters
 * @param password         password needs to have 6 - 100 characters
 * @param email            the users own email
 * @param role             the users role
 * @param displayName      the users displayed name
 * @param bio              the bio that will show on the users profile
 * @param profileImagePath the path of the users profile image
 */

public record UserRequestDTO(

        @NotBlank
        @Size(min = 3, max = 30, message = "Minimum 3 characters, maximum 30 characters")
        String username,

        @NotBlank
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters long")
        String password,

        String email,

        String role,

        String displayName,

        String bio,

        String profileImagePath) {


}
