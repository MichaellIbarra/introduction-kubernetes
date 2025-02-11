package dev.matichelo.msvcuser.services;

import dev.matichelo.msvcuser.clients.CourseClientRest;
import dev.matichelo.msvcuser.models.entity.User;
import dev.matichelo.msvcuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseClientRest courseClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listByIds(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        courseClientRest.deleteCourseUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> byEmail(String email) {
        return userRepository.byEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
