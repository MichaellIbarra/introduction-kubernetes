package dev.matichelo.msvcuser.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
// , url = "${msvc.course.url}"
@FeignClient(name = "msvc-course")
public interface CourseClientRest {
    @DeleteMapping("/delete-courser-user/{id}")
    void deleteCourseUserById(@PathVariable Long id);
}
