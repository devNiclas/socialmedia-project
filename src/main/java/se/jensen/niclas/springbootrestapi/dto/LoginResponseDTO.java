package se.jensen.niclas.springbootrestapi.dto;

/**
 * A record that represents the response after the user has logged in.
 *
 * @param token  is used for the users authentication
 * @param userId the users unique id
 */

public record LoginResponseDTO(String token, Long userId) {
}
