package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // Kiểm tra trùng lặp theo username / email / phone
    boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);

    // Tìm user theo username & password (để login)
    Optional<User> findByUsernameAndPassword(String username, String password);

    // Tìm user theo username
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    Optional<User> findByPhone(String phone);

    Optional<User> findTopByRole_RoleIdOrderByUserIdDesc(String roleId);
//    @EntityGraph(attributePaths = {"coursesPurchased.course"})
//    Optional<User> findById(String userId);
}
