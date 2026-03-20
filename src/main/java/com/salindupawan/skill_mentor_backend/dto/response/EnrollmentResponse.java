package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentResponse {
    private String subjectName;
    private Long count;
}
