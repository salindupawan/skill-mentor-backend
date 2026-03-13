package com.salindupawan.skill_mentor_backend.dto.request;

import com.salindupawan.skill_mentor_backend.entity.PaymentStatus;
import com.salindupawan.skill_mentor_backend.entity.SessionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchSessionRequest {
    private String meetingLink;
    private SessionStatus sessionStatus;
    private PaymentStatus paymentStatus;
    private String sessionNotes;
    private String paymentProofLink;
}
