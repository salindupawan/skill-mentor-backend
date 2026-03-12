package com.salindupawan.skill_mentor_backend.controller;

import com.salindupawan.skill_mentor_backend.dto.request.CreateReviewRequest;
import com.salindupawan.skill_mentor_backend.dto.response.ReviewResponse;
import com.salindupawan.skill_mentor_backend.security.UserPrincipal;
import com.salindupawan.skill_mentor_backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController extends AbstractController{
    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponse submitNewReview(@RequestBody @Valid CreateReviewRequest request, Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        assert principal != null;
        request.setStudentClerkId(principal.getId());

        return reviewService.submitReview(request);
    }
}
