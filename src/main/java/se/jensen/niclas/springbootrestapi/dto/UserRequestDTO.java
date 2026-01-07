package se.jensen.niclas.springbootrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
