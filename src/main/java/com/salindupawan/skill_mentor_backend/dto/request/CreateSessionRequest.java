package com.salindupawan.skill_mentor_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateSessionRequest {
    @NotNull(message = "Mentor id is required")
    private Long mentorId;

    @NotNull(message = "Subject id is required")
    private Long subjectId;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Can not book past date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;

    @NotNull(message = "Time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sessionStartTime;

    private String studentClerkId;
}
