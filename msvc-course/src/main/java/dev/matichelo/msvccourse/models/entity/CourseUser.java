package dev.matichelo.msvccourse.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    // está validación nos va a permitir comparar los userId de los objetos
    @Override
    public boolean equals(Object obj) {
        // la primera condición sirve para ver si los objetos son iguales en memoria
        if(this == obj) return true;
        // la segunda condición sirve para ver si el objeto es nulo o si no es de la misma clase
        if(!(obj instanceof CourseUser)) return false;
        //  la tercera es para crear un objeto de tipo CourseUser y comparar los userId
        CourseUser courseUser = (CourseUser) obj;
        // la cuarta es para comparar los userId
        return this.userId != null && this.userId.equals(courseUser.getUserId());
    }
}
