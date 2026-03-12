package com.edu_assess.edu_assess.backend.repository;


import com.edu_assess.edu_assess.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT COALESCE(SUM(a.pointsAwarded), 0) FROM Answer a WHERE a.submission.id = :submissionId")
    Integer sumPointsBySubmission(Long submissionId);
}