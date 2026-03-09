package com.salindupawan.skill_mentor_backend.dto.request;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String studentProfileUrl;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;
    private String studentClerkId;
}
