package com.salindupawan.skill_mentor_backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private Long sessionId;
    private String mentorName;
    private String sessionStatus;
    private String sessionTitle;
    private String sessionImageUrl;
    private LocalDate sessionDate;
    private LocalTime startTime;
}
