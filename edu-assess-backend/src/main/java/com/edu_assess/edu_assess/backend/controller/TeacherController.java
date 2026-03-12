package com.edu_assess.edu_assess.backend.controller;

import com.edu_assess.edu_assess.backend.dto.AssessmentDTO;
import com.edu_assess.edu_assess.backend.dto.CreateAssessmentRequest;
import com.edu_assess.edu_assess.backend.entity.User;
import com.edu_assess.edu_assess.backend.service.AssessmentService;
import com.edu_assess.edu_assess.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AssessmentService assessmentService;
    private final UserService userService;

    @PostMapping("/assessments")
    public ResponseEntity<AssessmentDTO> createAssessment(@RequestBody CreateAssessmentRequest request,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        User teacher = userService.findByEmail(userDetails.getUsername());
        var assessment = assessmentService.createAssessment(teacher, request);
        return ResponseEntity.ok(assessmentService.mapToDTO(assessment));
    }

    @GetMapping("/assessments")
    public List<AssessmentDTO> getMyAssessments(@AuthenticationPrincipal UserDetails userDetails) {
        User teacher = userService.findByEmail(userDetails.getUsername());
        return assessmentService.getAssessmentsByTeacher(teacher).stream()
                .map(assessmentService::mapToDTO)
                .toList();
    }

    // Additional endpoints...
}