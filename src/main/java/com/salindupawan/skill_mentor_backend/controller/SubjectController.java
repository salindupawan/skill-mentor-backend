package com.salindupawan.skill_mentor_backend.controller;

import com.salindupawan.skill_mentor_backend.dto.request.CreateSubjectRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SubjectResponse;
import com.salindupawan.skill_mentor_backend.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController extends AbstractController {
    private final SubjectService subjectService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createSubject(@RequestPart("details") @Valid CreateSubjectRequest subject, @RequestPart("image") MultipartFile image) {
        System.out.println(subject.toString());
        SubjectResponse subject1 = subjectService.createSubject(subject, image);
        System.out.println(subject1.toString());
    }

    @GetMapping
    public List<SubjectResponse> getSubjects() {
        return subjectService.getSubjects();
    }
}
