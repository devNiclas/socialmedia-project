package se.jensen.niclas.springbootrestapi.mapper;

import org.springframework.stereotype.Component;
import se.jensen.niclas.springbootrestapi.dto.PostRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.PostResponseDTO;
import se.jensen.niclas.springbootrestapi.model.Post;

/**
 * A class with a mapper that converts objects from Post to DTO's,
 * aswell DTO to post objects!
 */

@Component
public class PostMapper {

    private final UserMapper userMapper;

    /**
     *
     * A constructor which creates PostMapper
     *
     * @param userMapper is used to change the user's objects to DTO's.
     */

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     *
     * Method that is used for converting PostRequestDTO into a new Post
     *
     * @param dto is the data of PostRequest
     * @return a new post
     */

    public Post fromDTO(PostRequestDTO dto) {
        Post post = new Post();
        setPostValues(post, dto);
        return post;
    }

    /**
     *
     * A method that updates a post with data from PostRequestDTO
     *
     * @param post a post that we already have
     * @param dto  is the information we want to put in the existing post
     * @return an updated post
     */

    public Post fromDTO(Post post, PostRequestDTO dto) {
        setPostValues(post, dto);
        return post;

    }

    /**
     *
     * A method that copies the values from PostRequestDTO to Post
     *
     * @param post existing post
     * @param dto  the information we want to put into the post
     */

    private void setPostValues(Post post, PostRequestDTO dto) {
        post.setText(dto.text());
    }

    /**
     *
     * A method that converts Post into a PostResponseDTO
     *
     * @param post the Post we want to convert
     * @return PostResponseDTO with information from Post
     */

    public PostResponseDTO toDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getText(),
                post.getCreatedAt(),
                userMapper.toDTO(post.getUser())

        );
    }

}
