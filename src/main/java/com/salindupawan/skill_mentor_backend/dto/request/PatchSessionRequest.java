package com.salindupawan.skill_mentor_backend.dto.request;

import com.salindupawan.skill_mentor_backend.entity.PaymentStatus;
import com.salindupawan.skill_mentor_backend.entity.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchSessionRequest {
    private String meetingLink;
    private SessionStatus sessionStatus;
    private PaymentStatus paymentStatus;
    private String sessionNotes;
    private String paymentProofLink;
}
