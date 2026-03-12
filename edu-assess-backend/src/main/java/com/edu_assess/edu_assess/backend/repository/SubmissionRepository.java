package com.edu_assess.edu_assess.backend.repository;


import com.edu_assess.edu_assess.backend.entity.Submission;
import com.edu_assess.edu_assess.backend.entity.User;
import com.edu_assess.edu_assess.backend.entity.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssessmentIdAndStatus(Long assessmentId, SubmissionStatus status);
    List<Submission> findByStudentAndStatus(User student, SubmissionStatus status);
    List<Submission> findByStudentIdAndStatus(Long studentId, SubmissionStatus status);
}