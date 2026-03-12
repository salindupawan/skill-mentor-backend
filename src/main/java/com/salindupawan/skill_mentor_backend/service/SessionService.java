package com.salindupawan.skill_mentor_backend.service;

import com.salindupawan.skill_mentor_backend.dto.request.CreateSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.request.PatchSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SessionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface SessionService {
    SessionResponse enrollToSession(CreateSessionRequest session);
    PagedModel<SessionResponse> getAllSessions(Pageable pageable);
    PagedModel<SessionResponse> getMySessions(String clerkId, Pageable pageable);
    SessionResponse updateSessionStatus(Long id, PatchSessionRequest request);
}
