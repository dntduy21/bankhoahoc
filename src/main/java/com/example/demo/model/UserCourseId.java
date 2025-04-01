package com.example.demo.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Embeddable
public class UserCourseId implements Serializable {
    private String userId;
    private String courseId;

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
