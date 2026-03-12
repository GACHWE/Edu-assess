package com.edu_assess.edu_assess.backend.repository;


import com.edu_assess.edu_assess.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}