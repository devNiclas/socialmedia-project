package se.jensen.niclas.springbootrestapi.dto;

import java.util.List;

public record UserWithPostsResponseDTO(
        UserResponseDTO user,
        List<PostResponseDTO> posts
) {
}
