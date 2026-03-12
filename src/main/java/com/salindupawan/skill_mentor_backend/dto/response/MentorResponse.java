package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MentorResponse {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String title;
        private String profession;
        private String company;
        private Integer experienceYears;
        private String bio;
        private String profileImageUrl;
        private Boolean isCertified;
        private Integer startYear;
        private String specialization;
        private Long totalStudents = 0L;
        private List<ReviewResponse> reviews = new ArrayList<>();
        private List<SubjectResponse> subjects = new ArrayList<>();
}
