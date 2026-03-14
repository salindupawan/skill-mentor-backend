package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateStudentRequest;
import com.salindupawan.skill_mentor_backend.dto.response.StudentResponse;
import com.salindupawan.skill_mentor_backend.entity.Student;
import com.salindupawan.skill_mentor_backend.repository.StudentRepository;
import com.salindupawan.skill_mentor_backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createStudent(CreateStudentRequest request) {
        if(studentRepository.existsStudentByClerkId(request.getClerkId())){
            Student student = studentRepository.findStudentByClerkId(request.getClerkId()).orElseThrow(() -> new RuntimeException("Student not found"));
            modelMapper.map(request, student);
            studentRepository.save(student);
            return;
        }
        Student student = Student.builder()
                .clerkId(request.getClerkId())
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .profileImageUrl(request.getProfileImageUrl())
                .build();
        studentRepository.save(student);
    }
}
