package com.salindupawan.skill_mentor_backend.repository;

import com.salindupawan.skill_mentor_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByClerkId(String clerkId);

    boolean existsStudentByClerkId(String clerkId);
}
