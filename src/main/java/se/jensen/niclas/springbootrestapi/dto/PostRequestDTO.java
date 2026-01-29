package se.jensen.niclas.springbootrestapi.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * A record for the request of making a new post
 *
 * @param text information that the message can't be empty
 */

public record PostRequestDTO(

        @NotBlank(message = "Text can not be empty")
        String text) {
}
