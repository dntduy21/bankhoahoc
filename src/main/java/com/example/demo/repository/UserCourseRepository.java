package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserCourse;
import com.example.demo.model.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
    List<UserCourse> findByUser_UserId(String userId);
    List<UserCourse> findByUser_UserIdAndCourse_CourseIdIn(String userId, List<String> courseIds);

    List<UserCourse> findById_CourseId(String courseId);

}
