package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findByEmail(String email);
    List<User> findByNameContaining(String keyword);

}
