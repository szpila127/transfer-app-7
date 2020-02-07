package com.transfer.app7.repository;

import com.transfer.app7.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
