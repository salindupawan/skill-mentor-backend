package com.salindupawan.skill_mentor_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSubjectRequest {
    @NotBlank(message = "Subject name is required")
    @Size(min = 3, max = 100, message = "Subject name must be between 3 and 100 characters")
    private String subjectName;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description should be between 10 and 500 characters")
    private String description;

    @NotNull(message = "A mentor must be assigned to this subject")
    private Long mentorId;
}
