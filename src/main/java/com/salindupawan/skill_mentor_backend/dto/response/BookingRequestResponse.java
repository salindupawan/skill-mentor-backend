package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestResponse {
    private String studentName;
    private String paymentStatus;
    private String subjectName;
}
