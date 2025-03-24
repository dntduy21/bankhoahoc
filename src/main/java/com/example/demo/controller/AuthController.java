package com.example.demo.controller;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.UserRegistrationRequest;
import com.example.demo.DTO.UserResponse;
import com.example.demo.DTO.UserUpdateRequest;
import com.example.demo.model.Course;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserCourse;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserCourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCourseRepository courseRepository;

    // 1. Đăng ký
    @PostMapping("/DangKy")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            // Kiểm tra trùng lặp
            boolean isExist = userRepository.existsByUsernameOrEmailOrPhone(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPhone()
            );
            if (isExist) {
                return ResponseEntity.badRequest().body("Tài khoản đã tồn tại!");
            }

            // Tạo userId mới (giả sử logic đơn giản)
            String newUserId = "HV" + System.currentTimeMillis(); // HV + timestamp
            Role role = roleRepository.findById("HV").get();
            // Tạo user entity
            User user = new User();
            user.setUserId(newUserId);
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setRole(role); // Mặc định role HV

            // Lưu vào DB
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Đăng nhập
    @PostMapping("/DangNhap")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByUsernameAndPassword(
                    request.getUsername(),
                    request.getPassword()
            );

            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Tài khoản hoặc mật khẩu không đúng");
            }

            User user = userOpt.get();

            List<UserCourse> list1 = courseRepository.findByUser_UserId(user.getUserId());
            List<Course> list = getCoursesByUserId(list1);
            UserResponse rs = new UserResponse(user.getUserId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole(), list);

            return ResponseEntity.ok(rs);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public List<Course> getCoursesByUserId(List<UserCourse> userCourses) {
        // Chuyển đổi từ danh sách UserCourse sang danh sách Course
        return userCourses.stream()
                .map(UserCourse::getCourse)
                .collect(Collectors.toList());
    }
    // 3. Lấy thông tin tài khoản
    @GetMapping("/ThongTinTaiKhoan/{userId}")
    public ResponseEntity<?> getInfoUser(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();

        List<UserCourse> list1 = courseRepository.findByUser_UserId(user.getUserId());
        List<Course> list = getCoursesByUserId(list1);
        UserResponse rs = new UserResponse(user.getUserId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole(), list);

        return ResponseEntity.ok(rs);
    }

    // 4. Cập nhật thông tin tài khoản
    @PutMapping("/CapNhatTaiKhoan")
    public ResponseEntity<?> updateInfoUser(@RequestBody UserUpdateRequest request) {
        try {
            Optional<User> userOpt = userRepository.findById(request.getUserId());
            if (userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOpt.get();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setPassword(request.getPassword());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
