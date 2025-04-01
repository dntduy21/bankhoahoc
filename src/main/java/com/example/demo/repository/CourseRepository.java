package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByCategory_CategoryId(String categoryId);

    List<Course> findByCreator_UserId(String userId);

    List<Course> findByCourseNameContainingIgnoreCase(String courseName);

    Optional<Course> findTopByCategory_CategoryIdOrderByCourseIdDesc(String categoryId);

}