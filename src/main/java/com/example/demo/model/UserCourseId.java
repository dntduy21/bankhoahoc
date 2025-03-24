package com.example.demo.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class UserCourseId implements Serializable {
    private String userId;
    private String courseId;

    // Getter và Setter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // Nên override equals và hashCode cho composite key

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCourseId)) return false;

        UserCourseId that = (UserCourseId) o;

        if (!userId.equals(that.userId)) return false;
        return courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + courseId.hashCode();
        return result;
    }
}
