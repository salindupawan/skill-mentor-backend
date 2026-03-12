package com.salindupawan.skill_mentor_backend.service;

import com.salindupawan.skill_mentor_backend.dto.request.CreateReviewRequest;
import com.salindupawan.skill_mentor_backend.dto.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse submitReview(CreateReviewRequest createReviewRequest);
}
