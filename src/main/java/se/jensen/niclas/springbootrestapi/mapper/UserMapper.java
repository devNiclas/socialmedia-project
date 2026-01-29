package se.jensen.niclas.springbootrestapi.mapper;

import org.springframework.stereotype.Component;
import se.jensen.niclas.springbootrestapi.dto.UserRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.UserResponseDTO;
import se.jensen.niclas.springbootrestapi.model.User;

/**
 *
 * A class that converts User objects to DTOs, and the other way around.
 *
 */

@Component
public class UserMapper {

    /**
     *
     * A method where UserRequestDTO is used to make a new User entity.
     *
     * @param dto information from API request
     * @return a User entity
     */

    public User fromDto(UserRequestDTO dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    /**
     *
     * Method that updates the User entity using the UserRequestDTO
     *
     * @param user is the User entity that we have
     * @param dto  is the information of the API
     * @return The User entity now updated
     */

    public User fromDto(User user, UserRequestDTO dto) {
        setUserValues(user, dto);
        return user;
    }

    /**
     *
     * A method that uses the UserRequestDTO to put information into a User entity.
     *
     * @param user the User we want to update
     * @param dto  the information we want to put into User
     */

    private void setUserValues(User user, UserRequestDTO dto) {
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setProfileImagePath(dto.profileImagePath());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        user.setBio(dto.bio());
        user.setDisplayName(dto.displayName());

    }

    /**
     *
     * A method where we finally convert the User entity into UserResponseDTO
     *
     * @param user our User entity
     * @return we get a UserResponseDTO with information about the User
     */

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImagePath());
        return dto;


    }


}
