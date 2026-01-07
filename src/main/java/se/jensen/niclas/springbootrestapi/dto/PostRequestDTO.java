package se.jensen.niclas.springbootrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(

        @NotBlank(message = "Text can not be empty")
        @Size(min = 2, max = 500, message = "Text in post must have at least 2 characters")
        String text) {
}
