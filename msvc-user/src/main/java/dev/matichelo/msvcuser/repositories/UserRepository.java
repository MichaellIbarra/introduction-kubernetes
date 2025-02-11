package dev.matichelo.msvcuser.repositories;

import dev.matichelo.msvcuser.models.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> byEmail(String email);

    boolean existsByEmail(String email);
}
