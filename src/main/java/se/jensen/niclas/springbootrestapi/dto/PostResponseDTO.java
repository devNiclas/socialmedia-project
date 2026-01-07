package se.jensen.niclas.springbootrestapi.dto;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String text, LocalDateTime createdAt) {
}
