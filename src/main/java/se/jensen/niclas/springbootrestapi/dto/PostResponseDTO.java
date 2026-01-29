package se.jensen.niclas.springbootrestapi.dto;

import java.time.LocalDateTime;

/**
 *
 * A record with information about a post, that came back from API
 *
 * @param id        the id of the post
 * @param text      the text inside the post
 * @param createdAt when the post was created
 * @param user      which user has created the post
 */

public record PostResponseDTO(Long id, String text, LocalDateTime createdAt, UserResponseDTO user) {
}
