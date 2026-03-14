package com.salindupawan.skill_mentor_backend.controller;

import com.salindupawan.skill_mentor_backend.dto.request.CreateStudentRequest;
import com.salindupawan.skill_mentor_backend.entity.Student;
import com.salindupawan.skill_mentor_backend.security.UserPrincipal;
import com.salindupawan.skill_mentor_backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController extends AbstractController{
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody CreateStudentRequest request, Authentication authentication) {
        assert authentication != null;
        UserPrincipal student = (UserPrincipal) authentication.getPrincipal();

        assert student != null;
        request.setEmail(student.getEmail());
        request.setFirstName(student.getFirstName());
        request.setLastName(student.getLastName());
        request.setClerkId(student.getId());

        System.out.println("controller"+request.toString());

        studentService.createStudent(request);
        return sendCreatedResponse("Student created successfully");
    }
}
