package com.edu_assess.edu_assess.backend.dto;


import lombok.Data;
import java.util.List;

@Data
public class QuestionRequest {
    private String type;          // "MCQ" or "OPEN_ENDED"
    private String text;
    private List<String> options;
    private String correctAnswer;
    private Integer points;
}
