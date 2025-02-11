package dev.matichelo.msvcuser.services;

import dev.matichelo.msvcuser.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> byEmail(String email);
    boolean existsByEmail(String email);

    List<User> listByIds(Iterable<Long> ids);
}
