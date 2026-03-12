package com.salindupawan.skill_mentor_backend.controller;

import com.salindupawan.skill_mentor_backend.dto.request.CreateMentorRequest;
import com.salindupawan.skill_mentor_backend.dto.response.MentorResponse;
import com.salindupawan.skill_mentor_backend.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mentors")
@RequiredArgsConstructor
public class MentorController extends AbstractController {
    private final MentorService mentorService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MentorResponse createMentor(@RequestPart("details") @Valid CreateMentorRequest createMentorRequest, @RequestPart("image") MultipartFile image) {
        return mentorService.createMentor(createMentorRequest, image);

    }

    @GetMapping
    public List<MentorResponse> getAllMentors() {
        return mentorService.getMentors();
    }

    @GetMapping(path = "/{mentor-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MentorResponse getMentorById(@PathVariable("mentor-id") Long mentorId) {
        return mentorService.getMentorById(mentorId);
    }
}
