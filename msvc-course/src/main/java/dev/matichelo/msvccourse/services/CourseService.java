package dev.matichelo.msvccourse.services;

import dev.matichelo.msvccourse.models.User;
import dev.matichelo.msvccourse.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Optional<Course> listByIds(Long id, String token);
    Course save(Course course);
    void deleteById(Long id);
    void deleteCourseUserByUserId(Long id);
    Optional<User> assignUser(User user, Long courseId) ;
    Optional<User> unassignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
}
