package com.edu_assess.edu_assess.backend.dto;


import lombok.Data;
import java.util.List;

@Data
public class CreateAssessmentRequest {
    private String title;
    private String description;
    private String instructions;
    private List<QuestionRequest> questions;
}