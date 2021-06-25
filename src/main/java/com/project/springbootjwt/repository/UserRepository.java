package com.project.springbootjwt.repository;

import com.project.springbootjwt.model.User;
import com.project.springbootjwt.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int id);

    @Query(value = "select id, name, username from user where id = :id and username = :username", nativeQuery = true)
    UserInformation findByIdAndUsername(@Param("id") int id,
                                        @Param("username") String username);

    @Query(value = "select id, name, username, created, updated from user", nativeQuery = true)
    List<UserInformation> findAllUserInformation();

}
