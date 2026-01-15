package se.jensen.niclas.springbootrestapi.dto;

import jakarta.validation.constraints.NotBlank;

public record PostRequestDTO(

        @NotBlank(message = "Text can not be empty")
        String text) {
}
