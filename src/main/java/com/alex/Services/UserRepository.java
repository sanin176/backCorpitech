package com.alex.Services;

import com.alex.Model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "SELECT u.* FROM User u WHERE u.login = :login LIMIT 1", nativeQuery = true)
    List<User> findUserByLogin(@Param("login") String login);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.updatedAt = :updatedAt, u.isActive = true  WHERE u.login = :login")
    void updateUserByLogin(@Param("login") String login, @Param("updatedAt") LocalDateTime updatedAt);
}
