package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.response.AnalyticsResponse;
import com.salindupawan.skill_mentor_backend.dto.response.BookingRequestResponse;
import com.salindupawan.skill_mentor_backend.dto.response.EnrollmentResponse;
import com.salindupawan.skill_mentor_backend.entity.PaymentStatus;
import com.salindupawan.skill_mentor_backend.entity.Session;
import com.salindupawan.skill_mentor_backend.repository.MentorRepository;
import com.salindupawan.skill_mentor_backend.repository.SessionRepository;
import com.salindupawan.skill_mentor_backend.repository.StudentRepository;
import com.salindupawan.skill_mentor_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final MentorRepository mentorRepository;

    @Override
    public AnalyticsResponse getAnalytics() {
        long studentCount = studentRepository.count();
        long mentorCount = mentorRepository.count();
        long sessionCount = sessionRepository.count();
        long pendingPayment = sessionRepository.countSessionsByPaymentStatus(PaymentStatus.PENDING);
        List<Session> recentBookings = sessionRepository.findTop5ByOrderByCreatedAtDesc();
        List<EnrollmentResponse> topPerformingSubjects = sessionRepository.findTopPerformingSubjects();

        return AnalyticsResponse.builder()
                .activeMentors((int) mentorCount)
                .pendingPayments((int) pendingPayment)
                .totalBookings((int) sessionCount)
                .totalStudents((int) studentCount)
                .topEnrollments(topPerformingSubjects)
                .recentBookings(recentBookings.stream().map(this::map).collect(Collectors.toList()))
                .build();

    }

    private BookingRequestResponse map(Session session) {
        return BookingRequestResponse.builder()
                .paymentStatus(session.getPaymentStatus().name())
                .studentName(session.getStudent().getFirstName()+" "+session.getStudent().getLastName())
                .subjectName(session.getSubject().getSubjectName())
                .build();
    }
}
