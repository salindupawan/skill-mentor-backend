package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateMentorRequest;
import com.salindupawan.skill_mentor_backend.dto.response.MentorResponse;
import com.salindupawan.skill_mentor_backend.dto.response.ReviewResponse;
import com.salindupawan.skill_mentor_backend.dto.response.SubjectResponse;
import com.salindupawan.skill_mentor_backend.entity.Mentor;
import com.salindupawan.skill_mentor_backend.entity.Review;
import com.salindupawan.skill_mentor_backend.entity.SessionStatus;
import com.salindupawan.skill_mentor_backend.entity.Subject;
import com.salindupawan.skill_mentor_backend.exception.ResourceNotFoundException;
import com.salindupawan.skill_mentor_backend.repository.MentorRepository;
import com.salindupawan.skill_mentor_backend.repository.ReviewRepository;
import com.salindupawan.skill_mentor_backend.repository.SessionRepository;
import com.salindupawan.skill_mentor_backend.repository.SubjectRepository;
import com.salindupawan.skill_mentor_backend.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    private final ReviewRepository reviewRepository;
    private final SubjectRepository subjectRepository;
    private final SessionRepository sessionRepository;

    @Override
    public MentorResponse createMentor(CreateMentorRequest createMentorRequest, MultipartFile image) {
        Mentor mentor = modelMapper.map(createMentorRequest, Mentor.class);
        String url = fileStorageService.uploadFile(image);
        mentor.setProfileImageUrl(url);

        try {
            return modelMapper.map(mentorRepository.save(mentor), MentorResponse.class);

        } catch (Exception e) {
            fileStorageService.deleteFile(url);
            throw e;
        }
    }

    @Override
    public List<MentorResponse> getMentors() {
       return mentorRepository.findAll().stream().map(mentor -> {
           List<ReviewResponse> reviews = reviewRepository.findReviewsByMentor_MentorId(mentor.getMentorId())
                   .stream().map(this::map).toList();
           MentorResponse map = modelMapper.map(mentor, MentorResponse.class);
           map.setReviews(reviews);
           return map;
       }).toList();
    }

    @Override
    public MentorResponse getMentorById(Long id) {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mentor not found", HttpStatus.NOT_FOUND));
        MentorResponse resp = modelMapper.map(mentor, MentorResponse.class);

        List<ReviewResponse> reviews = reviewRepository.findReviewsByMentor_MentorId(mentor.getMentorId())
                .stream().map(this::map).toList();
        resp.setReviews(reviews);

        List<SubjectResponse> subjects = subjectRepository.findSubjectsByMentor_MentorId(mentor.getMentorId())
                .stream().map(s -> {
                    SubjectResponse map = modelMapper.map(s, SubjectResponse.class);
                    map.setNoOfEnrollments(sessionRepository.countSessionBySubject_SubjectIdAndSessionStatusNot(s.getSubjectId(), SessionStatus.REJECTED));
                    return map;
                }).toList();
        resp.setSubjects(subjects);
        resp.setTotalStudents(sessionRepository.countDistinctStudentsByMentorId(mentor.getMentorId()));

        return resp;
    }

    private ReviewResponse map(Review review) {
        return ReviewResponse.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewerFirstName(review.getStudent().getFirstName())
                .reviewerLastName(review.getStudent().getLastName())
                .reviewerProfileImageUrl(review.getStudent().getProfileImageUrl())
                .reviewDate(review.getCreatedAt())
                .build();
    }


}
