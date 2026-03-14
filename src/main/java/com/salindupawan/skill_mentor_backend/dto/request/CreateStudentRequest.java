package com.salindupawan.skill_mentor_backend.dto.request;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String profileImageUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String clerkId;
}
