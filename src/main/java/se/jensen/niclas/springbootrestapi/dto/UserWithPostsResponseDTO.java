package se.jensen.niclas.springbootrestapi.dto;

import java.util.List;

/**
 *
 * Record of the user together with their own posts.
 *
 * @param user  user and their information
 * @param posts a list of posts (made by the user)
 */

public record UserWithPostsResponseDTO(
        UserResponseDTO user,
        List<PostResponseDTO> posts
) {
}
