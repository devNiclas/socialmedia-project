package se.jensen.niclas.springbootrestapi.dto;

/**
 *
 * Record of user information that has been returned from the API
 *
 * @param id               users own id
 * @param username         users username
 * @param role             users role
 * @param email            users email
 * @param displayName      users display name
 * @param bio              users bio
 * @param profileImagePath users profile image path
 */

public record UserResponseDTO(Long id,
                              String username,
                              String role,
                              String email,
                              String displayName,
                              String bio,
                              String profileImagePath
) {
}
