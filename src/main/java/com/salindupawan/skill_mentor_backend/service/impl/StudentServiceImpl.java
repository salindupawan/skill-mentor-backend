package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateStudentRequest;
import com.salindupawan.skill_mentor_backend.dto.response.StudentResponse;
import com.salindupawan.skill_mentor_backend.entity.Student;
import com.salindupawan.skill_mentor_backend.repository.StudentRepository;
import com.salindupawan.skill_mentor_backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public void createStudent(CreateStudentRequest request) {
        Student student = Student.builder()
                .clerkId(request.getStudentClerkId())
                .lastName(request.getStudentLastName())
                .firstName(request.getStudentFirstName())
                .email(request.getStudentEmail())
                .profileImageUrl(request.getStudentProfileUrl())
                .build();

        studentRepository.save(student);
    }
}
