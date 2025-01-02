package org.example.UserService.Repository;

import org.example.UserService.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserServiceRepo extends JpaRepository<Users,Long> {

    public Users findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    public List<Users> findByUsernameIgnoreCase(@Param("username") String username);
}
