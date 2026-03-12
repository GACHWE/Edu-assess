package com.edu_assess.edu_assess.backend.repository;


import com.edu_assess.edu_assess.backend.entity.Assessment;
import com.edu_assess.edu_assess.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByCreatedBy(User teacher);
}