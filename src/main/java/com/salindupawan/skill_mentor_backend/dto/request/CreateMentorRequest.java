package com.salindupawan.skill_mentor_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateMentorRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank( message = "Please provide a valid phone number")
    private String phoneNumber;

    @NotBlank(message = "Title is required")
    private String title; // e.g., "Senior Software Engineer"

    private String profession;
    private String company;

    @NotNull(message = "Experience years is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 60, message = "Experience years seems unrealistic")
    private Integer experienceYears;

    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;

    private Boolean isCertified = false; // Default to false

    @Min(value = 1970, message = "Start year must be after 1970")
    @Max(value = 2026, message = "Start year cannot be in the future")
    private Integer startYear;

    private String specialization;
}