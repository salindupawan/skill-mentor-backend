package com.salindupawan.skill_mentor_backend.repository;

import com.salindupawan.skill_mentor_backend.dto.response.EnrollmentResponse;
import com.salindupawan.skill_mentor_backend.entity.PaymentStatus;
import com.salindupawan.skill_mentor_backend.entity.Session;
import com.salindupawan.skill_mentor_backend.entity.SessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT COUNT(DISTINCT s.student.studentId) FROM Session s WHERE s.mentor.mentorId = :mentorId")
    long countDistinctStudentsByMentorId(@Param("mentorId") Long mentorId);

    @Query("SELECT COUNT(DISTINCT s.student.studentId) FROM Session s WHERE s.subject.subjectId = :subjectId")
    long countDistinctStudentsBySubjectId(@Param("subjectId") Long subjectId);

    boolean existsByMentor_MentorIdAndSessionDateAndStartTimeAndSessionStatusNot(Long mentorMentorId, LocalDate sessionDate, LocalTime startTime, SessionStatus sessionStatus);

    Page<Session> findSessionsByStudent_ClerkId(String studentClerkId, Pageable pageable);
    
    long countSessionBySubject_SubjectIdAndSessionStatusNot(Long subjectSubjectId, SessionStatus sessionStatus);
    long countSessionsByPaymentStatus(PaymentStatus paymentStatus);
    List<Session> findTop5ByOrderByCreatedAtDesc();

    @Query("SELECT new com.salindupawan.skill_mentor_backend.dto.response.EnrollmentResponse(s.subject.subjectName, COUNT(s)) " +
            "FROM Session s " +
            "GROUP BY s.subject.subjectName " +
            "ORDER BY COUNT(s) DESC LIMIT 3")
    List<EnrollmentResponse> findTopPerformingSubjects();
}

