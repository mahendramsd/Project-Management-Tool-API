package com.msd.api.repositories;

import com.msd.api.domain.User;
import com.msd.api.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByStatus(Status active);

    List<User> findAllByStatus(Status status);
}
