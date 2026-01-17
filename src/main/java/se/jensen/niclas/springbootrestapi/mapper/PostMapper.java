package se.jensen.niclas.springbootrestapi.mapper;

import org.springframework.stereotype.Component;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;

@Component
public class PostMapper {

    public Post fromDTO(PostRequestDTO dto) {
        Post post = new Post();
        setPostValues(post, dto);
        return post;
    }

    public Post fromDTO(Post post, PostRequestDTO dto) {
        setPostValues(post, dto);
        return post;

    }

    private void setPostValues(Post post, PostRequestDTO dto) {
        post.setText(dto.text());
    }


    public PostResponseDTO toDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getText(),
                post.getCreatedAt()

        );
    }

}
