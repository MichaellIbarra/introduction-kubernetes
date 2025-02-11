package dev.matichelo.msvccourse.services;

import dev.matichelo.msvccourse.clients.UserClientRest;
import dev.matichelo.msvccourse.models.User;
import dev.matichelo.msvccourse.models.entity.Course;
import dev.matichelo.msvccourse.models.entity.CourseUser;
import dev.matichelo.msvccourse.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CourseImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest userClientRest;


    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> listByIds(Long id, String token) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(cu -> cu.getUserId()).toList();
                System.out.println(ids);
                List<User> users = userClientRest.listByIds(ids, token);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUserByUserId(Long id) {
        courseRepository.deleteCourseUserByUserId(id);

    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            User userMsvc = userClientRest.findById(user.getId());
            System.out.println(userMsvc);
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course1.addCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> unassignUser(User user, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            User userMsvc = userClientRest.findById(user.getId());
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course1.removeCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            User userMsvc = userClientRest.save(user);
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course1.addCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
}
