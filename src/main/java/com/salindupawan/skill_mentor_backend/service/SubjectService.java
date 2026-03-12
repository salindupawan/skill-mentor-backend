package com.salindupawan.skill_mentor_backend.service;


import com.salindupawan.skill_mentor_backend.dto.request.CreateSubjectRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SubjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubjectService {
    List<SubjectResponse> getSubjects();
    SubjectResponse createSubject(CreateSubjectRequest subject, MultipartFile image);
}
