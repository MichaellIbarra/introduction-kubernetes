package dev.matichelo.msvccourse.repositories;

import dev.matichelo.msvccourse.models.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    // @Modifying: para indicar que la consulta es de modificación o también se puede usar @Transactional para indicar que la consulta es de modificación
    @Modifying
    @Query("DELETE FROM CourseUser  cu WHERE cu.userId = :id")
    void deleteCourseUserByUserId(Long id);
}

