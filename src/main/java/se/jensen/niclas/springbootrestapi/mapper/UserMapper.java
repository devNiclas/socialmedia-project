package se.jensen.niclas.springbootrestapi.mapper;

import org.springframework.stereotype.Component;
import se.jensen.niclas.springbootrestapi.dto.UserRequestDTO;
import se.jensen.niclas.springbootrestapi.dto.UserResponseDTO;
import se.jensen.niclas.springbootrestapi.model.User;

@Component
public class UserMapper {


    public User fromDto(UserRequestDTO dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    public User fromDto(User user, UserRequestDTO dto) {
        setUserValues(user, dto);
        return user;
    }

    private void setUserValues(User user, UserRequestDTO dto) {
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setProfileImagePath(dto.profileImagePath());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        user.setBio(dto.bio());
        user.setDisplayName(dto.displayName());

    }

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
