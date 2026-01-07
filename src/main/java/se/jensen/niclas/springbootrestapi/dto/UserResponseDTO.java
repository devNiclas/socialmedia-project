package se.jensen.niclas.springbootrestapi.dto;

public record UserResponseDTO(Long id,
                              String username,
                              String role,
                              String email,
                              String displayName,
                              String bio,
                              String profileImagePath
) {
}
