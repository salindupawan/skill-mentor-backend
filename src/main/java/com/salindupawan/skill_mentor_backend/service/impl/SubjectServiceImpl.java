package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateSubjectRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SubjectResponse;
import com.salindupawan.skill_mentor_backend.entity.Mentor;
import com.salindupawan.skill_mentor_backend.entity.Subject;
import com.salindupawan.skill_mentor_backend.exception.ResourceNotFoundException;
import com.salindupawan.skill_mentor_backend.repository.MentorRepository;
import com.salindupawan.skill_mentor_backend.repository.SubjectRepository;
import com.salindupawan.skill_mentor_backend.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final ModelMapper modelMapper;
    private final SubjectRepository subjectRepository;
    private final MentorRepository mentorRepository;
    private final FileStorageService fileStorageService;

    @Override
    public List<SubjectResponse> getSubjects() {
        List<Subject> all = subjectRepository.findAll();

        return all.stream().map(this::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SubjectResponse createSubject(CreateSubjectRequest req, MultipartFile image) {
        Subject subject = modelMapper.map(req, Subject.class);
        Mentor mentor = mentorRepository.findById(req.getMentorId()).orElseThrow(() -> new ResourceNotFoundException("Mentor id not found", HttpStatus.NOT_FOUND));

        subject.setMentor(mentor);

        String url = fileStorageService.uploadFile(image);
        subject.setSubjectImageUrl(url);

        try{
        return modelMapper.map(subjectRepository.save(subject), SubjectResponse.class);

        }catch (Exception e){
            fileStorageService.deleteFile(url);
            throw e;
        }

    }

    private SubjectResponse map(Subject subject) {
        SubjectResponse map = modelMapper.map(subject, SubjectResponse.class);

        map.setMentorId(subject.getMentor().getMentorId());
        map.setMentorName(subject.getMentor().getFirstName() + " " + subject.getMentor().getLastName());
        return map;
    }
}
