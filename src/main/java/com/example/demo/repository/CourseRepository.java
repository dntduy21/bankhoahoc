package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByCategory_CategoryId(String categoryId);

    List<Course> findByCreator_UserId(String userId);

    @Query("SELECT c FROM Course c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :courseName, '%'))")
    List<Course> findByCourseNameContainingIgnoreCase(@Param("courseName") String courseName);

//    List<Course> findByCreator_UserId(String userId);
Optional<Course> findTopByCategory_CategoryIdOrderByCourseIdDesc(String categoryId);

}