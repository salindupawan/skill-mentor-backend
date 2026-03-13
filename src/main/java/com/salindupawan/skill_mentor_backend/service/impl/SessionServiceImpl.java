package com.salindupawan.skill_mentor_backend.service.impl;

import com.salindupawan.skill_mentor_backend.dto.request.CreateSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.request.PatchSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SessionResponse;
import com.salindupawan.skill_mentor_backend.entity.*;
import com.salindupawan.skill_mentor_backend.exception.ResourceNotFoundException;
import com.salindupawan.skill_mentor_backend.exception.SkillMentorException;
import com.salindupawan.skill_mentor_backend.repository.MentorRepository;
import com.salindupawan.skill_mentor_backend.repository.SessionRepository;
import com.salindupawan.skill_mentor_backend.repository.StudentRepository;
import com.salindupawan.skill_mentor_backend.repository.SubjectRepository;
import com.salindupawan.skill_mentor_backend.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    @Override
    public SessionResponse enrollToSession(CreateSessionRequest session) {
        Student student = studentRepository.findStudentByClerkId(session.getStudentClerkId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found", HttpStatus.NOT_FOUND));
        Mentor mentor = mentorRepository.findById(session.getMentorId())
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found", HttpStatus.NOT_FOUND));
        Subject subject = subjectRepository.findById(session.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found", HttpStatus.NOT_FOUND));

        if(sessionRepository.existsByMentor_MentorIdAndSessionDateAndStartTimeAndSessionStatusNot(mentor.getMentorId(),session.getSessionDate(),session.getSessionStartTime(),SessionStatus.REJECTED)){
            throw new SkillMentorException("Mentor is already booked in this time slot", HttpStatus.CONFLICT);
        }

        Session build = Session.builder()
                .student(student)
                .mentor(mentor)
                .subject(subject)
                .sessionDate(session.getSessionDate())
                .startTime(session.getSessionStartTime())
                .durationMinutes(60)
                .sessionStatus(SessionStatus.SCHEDULED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return map(sessionRepository.save(build));
    }

    @Override
    public List<SessionResponse> getAllSessions(Pageable pageable) {
        return sessionRepository.findAll(pageable).stream().map(this::map).toList();
    }

    @Override
    public List<SessionResponse> getMySessions(String clerkId, Pageable pageable) {
        return sessionRepository.findSessionsByStudent_ClerkId(clerkId, pageable).stream().map(this::map).toList();
    }

    @Override
    public SessionResponse updateSessionStatus(Long id, PatchSessionRequest request) {
        Session existingSession = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found", HttpStatus.NOT_FOUND));

        modelMapper.map(request, existingSession);

        return map(sessionRepository.save(existingSession));
    }

    @Transactional
    @Override
    public SessionResponse makePayment(Long id, MultipartFile file) {
        String url = fileStorageService.uploadFile(file);
        PatchSessionRequest request = PatchSessionRequest.builder()
                .paymentProofLink(url)
                .build();

        try {
            return updateSessionStatus(id, request);
        } catch (Exception e) {
            fileStorageService.deleteFile(url);
            throw new RuntimeException(e);
        }
    }

    private SessionResponse map(Session session) {
        return SessionResponse.builder()
                .sessionId(session.getSessionId())
                .sessionImageUrl(session.getSubject().getSubjectImageUrl())
                .sessionDate(session.getSessionDate())
                .mentorName(session.getMentor().getFirstName()+" "+session.getMentor().getLastName())
                .sessionTitle(session.getSubject().getSubjectName())
                .sessionStatus(session.getSessionStatus().name())
                .startTime(session.getStartTime())
                .build();


    }
}
