package com.salindupawan.skill_mentor_backend.service;

import com.salindupawan.skill_mentor_backend.dto.request.CreateMentorRequest;
import com.salindupawan.skill_mentor_backend.dto.response.MentorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MentorService {
    MentorResponse createMentor(CreateMentorRequest createMentorRequest, MultipartFile image);
    List<MentorResponse> getMentors();
    MentorResponse getMentorById(Long id);
}
