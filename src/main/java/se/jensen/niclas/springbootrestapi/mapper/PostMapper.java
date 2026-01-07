package se.jensen.niclas.springbootrestapi.mapper;

import org.springframework.stereotype.Component;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;

@Component
public class PostMapper {
    public Post toPost(PostRequestDTO postRequestDTO) {
        Post post = new Post();
        post.setText(postRequestDTO.text());
        return post;
    }


    public PostResponseDTO toPostResponse(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getText(),
                post.getCreatedAt()

        );
    }

}
