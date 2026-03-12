package com.salindupawan.skill_mentor_backend.repository;

import com.salindupawan.skill_mentor_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByMentor_MentorId(Long mentorMentorId);
}
