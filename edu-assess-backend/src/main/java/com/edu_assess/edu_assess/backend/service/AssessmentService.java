package com.edu_assess.edu_assess.backend.service;


import com.edu_assess.edu_assess.backend.dto.AssessmentDTO;
import com.edu_assess.edu_assess.backend.dto.CreateAssessmentRequest;
import com.edu_assess.edu_assess.backend.dto.QuestionDTO;
import com.edu_assess.edu_assess.backend.entity.Assessment;
import com.edu_assess.edu_assess.backend.entity.Question;
import com.edu_assess.edu_assess.backend.entity.QuestionType;
import com.edu_assess.edu_assess.backend.entity.User;
import com.edu_assess.edu_assess.backend.repository.AssessmentRepository;
import com.edu_assess.edu_assess.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Assessment createAssessment(User teacher, CreateAssessmentRequest request) {
        Assessment assessment = Assessment.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .instructions(request.getInstructions())
                .createdBy(teacher)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shareableLink(UUID.randomUUID().toString())
                .build();
        assessment = assessmentRepository.save(assessment);

        List<Question> questions = request.getQuestions().stream()
                .map(q -> Question.builder()
                        .assessment(assessment)
                        .type(QuestionType.valueOf(q.getType()))
                        .text(q.getText())
                        .options(q.getOptions())
                        .correctAnswer(q.getCorrectAnswer())
                        .points(q.getPoints())
                        .build())
                .collect(Collectors.toList());
        questionRepository.saveAll(questions);

        return assessment;
    }

    public List<Assessment> getAssessmentsByTeacher(User teacher) {
        return assessmentRepository.findByCreatedBy(teacher);
    }

    public Assessment getAssessmentById(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    public AssessmentDTO mapToDTO(Assessment assessment) {
        AssessmentDTO dto = new AssessmentDTO();
        dto.setId(assessment.getId());
        dto.setTitle(assessment.getTitle());
        dto.setDescription(assessment.getDescription());
        dto.setInstructions(assessment.getInstructions());
        dto.setCreatedAt(assessment.getCreatedAt());
        dto.setShareableLink(assessment.getShareableLink());
        // questions mapping omitted for brevity
        return dto;
    }

    // Additional methods...
}
