package com.edu_assess.edu_assess.backend.repository;


import com.edu_assess.edu_assess.backend.entity.ParentStudent;
import com.edu_assess.edu_assess.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {
    List<ParentStudent> findByParent(User parent);
}