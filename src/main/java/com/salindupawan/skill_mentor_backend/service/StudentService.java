package com.salindupawan.skill_mentor_backend.service;

import com.salindupawan.skill_mentor_backend.dto.request.CreateStudentRequest;
import com.salindupawan.skill_mentor_backend.dto.response.StudentResponse;
import com.salindupawan.skill_mentor_backend.entity.Student;

public interface StudentService {
    void createStudent(CreateStudentRequest student);
}
