package com.salindupawan.skill_mentor_backend.controller;

import com.salindupawan.skill_mentor_backend.dto.request.CreateSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.request.PatchSessionRequest;
import com.salindupawan.skill_mentor_backend.dto.response.SessionResponse;
import com.salindupawan.skill_mentor_backend.entity.Session;
import com.salindupawan.skill_mentor_backend.security.UserPrincipal;
import com.salindupawan.skill_mentor_backend.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController extends AbstractController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionResponse> enrollToSession(@RequestBody CreateSessionRequest session, Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        assert principal != null;
        session.setStudentClerkId(principal.getId());

        SessionResponse sessionResponse = sessionService.enrollToSession(session);
        return sendCreatedResponse(sessionResponse);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions(Pageable pageable) {
     return sendOkResponse(sessionService.getAllSessions(pageable));
    }

    @GetMapping("/enrolled")
    public ResponseEntity<List<SessionResponse>> getMySessions(Pageable pageable, Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        assert principal != null;
        return sendOkResponse(sessionService.getMySessions(principal.getId(), pageable));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<SessionResponse> updateSession(@PathVariable Long id, @RequestBody PatchSessionRequest patchSessionRequest) {
        System.out.println("patchSessionRequest: " + patchSessionRequest.toString());
        return sendOkResponse(sessionService.updateSessionStatus(id, patchSessionRequest));
    }

    @PatchMapping(path = "/payment/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionResponse> makePayment(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(sessionService.makePayment(id, file));
    }
}
