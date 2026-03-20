package com.salindupawan.skill_mentor_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private Integer totalStudents;
    private Integer activeMentors;
    private Integer totalBookings;
    private Integer pendingPayments;
    private List<BookingRequestResponse> recentBookings;
    private List<EnrollmentResponse> topEnrollments;
}
