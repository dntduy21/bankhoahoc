package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CourseCategory, String> {
    Optional<CourseCategory> findByCategoryName(String categoryName);
}
