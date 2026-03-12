package com.edu_assess.edu_assess.backend.dto;


import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String type;
    private String text;
    private List<String> options;
    private Integer points;
    // Do not include correctAnswer for student-facing DTOs
}