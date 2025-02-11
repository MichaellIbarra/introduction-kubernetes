package dev.matichelo.msvccourse.models.entity;

import dev.matichelo.msvccourse.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // CascadeType.ALL: si se elimina un curso, se eliminan todos los usuarios asociados a ese curso | orphanRemoval: si se elimina un usuario, se elimina el curso asociado a ese usuario
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<User> users;

    public Course() {
        // la importancia de inicializar las listas en el constructor es para evitar el NullPointerException en caso de que no se haya inicializado la lista
    courseUsers = new ArrayList<>();
    users = new ArrayList<>();
    }

    public void addCourseUser(CourseUser courseUser){
        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser){
        courseUsers.remove(courseUser);
    }


}
