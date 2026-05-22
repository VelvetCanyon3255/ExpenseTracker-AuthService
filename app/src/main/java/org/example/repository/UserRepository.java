package org.example.repository;

import org.apache.catalina.User;
import org.example.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);

}
