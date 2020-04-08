package com.ip2.myapp.backend.repository;

import com.ip2.myapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {


}
