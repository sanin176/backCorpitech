package com.alex.Services;

import com.alex.Model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "SELECT u.* FROM User u WHERE u.login = :login LIMIT 1", nativeQuery = true)
    List<User> findUserByLogin(@Param("login") String login);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.updatedAt = :updatedAt, u.isActive = true  WHERE u.login = :login")
    void updateUserByLogin(@Param("login") String login, @Param("updatedAt") LocalDate updatedAt);

    @Modifying
    @Transactional
    @Query(value = "SELECT u.id AS id, u.name AS name, u.login AS login, u.is_active AS isActive " +
            "FROM user u WHERE id = ?1", nativeQuery = true)
    List<UserServ> findByIdSomeField(int id);

    @Modifying
    @Transactional
    @Query(value = "SELECT u.id AS id, u.name AS name, u.login AS login, u.is_active AS isActive " +
            "FROM user u LIMIT ?1, ?2", nativeQuery = true)
    List<UserServ> findAllSomeField(int idFrom, int idTo);

    interface UserServ {
        int getId();
        String getName();
        String getLogin();
        boolean getIsActive();
    }

    @Modifying
    @Transactional
    @Query(value = "SELECT COUNT(*) AS numberRecordsUsers FROM user u", nativeQuery = true)
    List<NumberRecordsUsers> numberRecordsUsers();

    interface NumberRecordsUsers {
        int getNumberRecordsUsers();
    }
}
