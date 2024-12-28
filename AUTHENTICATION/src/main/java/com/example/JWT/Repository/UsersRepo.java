package com.example.JWT.Repository;

import com.example.JWT.Model.Users;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@EnableJpaRepositories
@RedisHash
public interface UsersRepo extends JpaRepository<Users,Integer> {

    Users findByEmail(String email);
}
