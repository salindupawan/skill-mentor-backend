package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SubjectResponse {
    private Long subjectId;
    private String subjectName;
    private String description;
    private Long mentorId;
    private String mentorName;
    private String subjectImageUrl;
    private Long noOfEnrollments;
}
