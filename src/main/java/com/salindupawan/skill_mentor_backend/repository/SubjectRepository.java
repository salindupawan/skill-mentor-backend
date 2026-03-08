package com.salindupawan.skill_mentor_backend.repository;

import com.salindupawan.skill_mentor_backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findSubjectsByMentor_MentorId(Long mentorMentorId);
}
