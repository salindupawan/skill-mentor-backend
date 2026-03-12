package com.salindupawan.skill_mentor_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateReviewRequest {
    private String studentClerkId;

    @NotBlank(message = "First name is required")
    @Size(max = 250, message = "First name cannot exceed 250 characters")
    private String comment;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    @NotNull(message = "Mentor id is required")
    private Long mentorId;
}
