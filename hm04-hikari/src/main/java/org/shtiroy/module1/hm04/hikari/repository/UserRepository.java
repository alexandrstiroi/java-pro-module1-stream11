package org.shtiroy.module1.hm04.hikari.repository;

import org.shtiroy.module1.hm04.hikari.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username like %:name%")
    List<User> search(@Param("name") String name);

}
