package com.edu_assess.edu_assess.backend.dto;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssessmentDTO {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private LocalDateTime createdAt;
    private String shareableLink;
    private List<QuestionDTO> questions;
}