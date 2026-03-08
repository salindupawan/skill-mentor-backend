package com.salindupawan.skill_mentor_backend.repository;

import com.salindupawan.skill_mentor_backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT COUNT(DISTINCT s.student.studentId) FROM Session s WHERE s.mentor.mentorId = :mentorId")
    long countDistinctStudentsByMentorId(@Param("mentorId") Long mentorId);

    @Query("SELECT COUNT(DISTINCT s.student.studentId) FROM Session s WHERE s.subject.subjectId = :subjectId")
    long countDistinctStudentsBySubjectId(@Param("subjectId") Long subjectId);
}
