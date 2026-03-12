package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateReviewRequest;
import com.salindupawan.skill_mentor_backend.dto.response.ReviewResponse;
import com.salindupawan.skill_mentor_backend.entity.Mentor;
import com.salindupawan.skill_mentor_backend.entity.Review;
import com.salindupawan.skill_mentor_backend.entity.Student;
import com.salindupawan.skill_mentor_backend.exception.ResourceNotFoundException;
import com.salindupawan.skill_mentor_backend.repository.MentorRepository;
import com.salindupawan.skill_mentor_backend.repository.ReviewRepository;
import com.salindupawan.skill_mentor_backend.repository.StudentRepository;
import com.salindupawan.skill_mentor_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewResponse submitReview(CreateReviewRequest createReviewRequest) {
        Student student = studentRepository.findStudentByClerkId(createReviewRequest.getStudentClerkId()).orElseThrow(() -> new ResourceNotFoundException("Invalid student id", HttpStatus.NOT_FOUND));
        Mentor mentor = mentorRepository.findById(createReviewRequest.getMentorId()).orElseThrow(() -> new ResourceNotFoundException("Invalid mentor id", HttpStatus.NOT_FOUND));

        Review review = modelMapper.map(createReviewRequest, Review.class);
        review.setStudent(student);
        review.setMentor(mentor);

        return map(reviewRepository.save(review));
    }

    private ReviewResponse map(Review review) {
        return ReviewResponse.builder()
                .comment(review.getComment())
                .reviewDate(review.getCreatedAt())
                .rating(review.getRating())
                .reviewerFirstName(review.getStudent().getFirstName())
                .reviewerLastName(review.getStudent().getLastName())
                .reviewerProfileImageUrl(review.getStudent().getProfileImageUrl())
                .build();
    }
}
