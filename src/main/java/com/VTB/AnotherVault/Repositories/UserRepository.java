package com.VTB.AnotherVault.Repositories;

import com.VTB.AnotherVault.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findById(long id);

    User findByEmail(String email);
    //List<User> findByStatus(String status);

}
