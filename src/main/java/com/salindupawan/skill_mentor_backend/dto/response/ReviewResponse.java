package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String reviewerProfileImageUrl;
    private String comment;
    private Integer rating;
    private String reviewerFirstName;
    private String reviewerLastName;
    private Date reviewDate;

}
