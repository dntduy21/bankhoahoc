package com.example.demo.controller;

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

import java.util.*;

@RestController
@RequestMapping("/QuanLyNguoiDung")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Lấy danh sách người dùng
    @GetMapping("/LayDanhSachNguoiDung")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> listUsers = userRepository.findAll();

            if (listUsers.isEmpty()) {
                return ResponseEntity.status(404).body("Không có người dùng nào cả");
            }

            return ResponseEntity.ok().body(listUsers);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Lỗi lấy dữ liệu người dùng: " + e.getMessage());
        }
    }

    // Thêm người dùng mới
    @PostMapping("/ThemNguoiDung")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            String fullName = body.get("fullName");
            String email = body.get("email");
            String phone = body.get("phone");
            String roleId = body.getOrDefault("roleId", "HV");

            // Kiểm tra các giá trị bắt buộc
            if (username == null || password == null || fullName == null || email == null || phone == null) {
                return ResponseEntity.status(400).body("Vui lòng điền đầy đủ thông tin");
            }

            // Kiểm tra trùng lặp
            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(400).body("Email đã tồn tại");
            }

            if (userRepository.findByUsername(username).isPresent()) {
                return ResponseEntity.status(400).body("Tài khoản đã tồn tại");
            }

            if (userRepository.findByPhone(phone).isPresent()) {
                return ResponseEntity.status(400).body("Số điện thoại đã tồn tại");
            }

            // Tạo mã người dùng mới
            Optional<User> lastUserOpt = userRepository.findTopByRole_RoleIdOrderByUserIdDesc(roleId);
            String newUserId = "HV" + System.currentTimeMillis();

            // Tìm vai trò trong database
            Optional<Role> roleOpt = roleRepository.findById(roleId);
            if (roleOpt.isEmpty()) {
                return ResponseEntity.status(400).body("Vai trò không hợp lệ");
            }

            // Tạo người dùng mới
            User newUser = new User();
            newUser.setUserId(newUserId);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setRole(roleOpt.get());

            userRepository.save(newUser);

            return ResponseEntity.ok().body("Thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Không thể tạo người dùng: " + e.getMessage());
        }
    }

    // Xóa người dùng
    @DeleteMapping("/XoaNguoiDung/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Tài khoản không tồn tại");
        }

        if (!userCourseRepository.findByUser_UserId(userId).isEmpty()) {
            return ResponseEntity.status(400).body("Người dùng này đã đăng ký khóa học, không thể xóa");
        }

        if (!courseRepository.findByCreator_UserId(userId).isEmpty()) {
            return ResponseEntity.status(400).body("Người dùng này đã tạo khóa học, không thể xóa");
        }

        userRepository.deleteById(userId);
        return ResponseEntity.ok("Xóa người dùng thành công!");
    }

    @PutMapping("/CapNhatThongTinNguoiDung")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String fullName = body.get("fullName");
            String password = body.get("password");
            String email = body.get("email");
            String phone = body.get("phone");
            String newRoleId = body.get("roleId");

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Tài khoản không tồn tại!");
            }

            User user = userOpt.get();
            String oldRoleId = user.getRole().getRoleId();

            // Kiểm tra nếu email, số điện thoại bị trùng
            if (!user.getEmail().equals(email) && userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(400).body("Email đã tồn tại");
            }
            if (!user.getPhone().equals(phone) && userRepository.findByPhone(phone).isPresent()) {
                return ResponseEntity.status(400).body("Số điện thoại đã tồn tại");
            }

            // Cập nhật thông tin
            user.setFullName(fullName);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);

            // Nếu vai trò thay đổi, cập nhật mã người dùng
            if (!oldRoleId.equals(newRoleId)) {
                Optional<Role> roleOpt = roleRepository.findById(newRoleId);
                if (roleOpt.isEmpty()) {
                    return ResponseEntity.status(400).body("Vai trò không hợp lệ");
                }
                user.setRole(roleOpt.get());

                // Tạo mã người dùng mới theo vai trò mới
                String newUserId = newRoleId + System.currentTimeMillis();
                user.setUserId(newUserId);
            }

            userRepository.save(user);
            return ResponseEntity.ok("Cập nhật người dùng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Không thể cập nhật người dùng: " + e.getMessage());
        }
    }


    // Lấy thông tin người dùng
    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Người dùng không tồn tại");
        }
        return ResponseEntity.ok(userOpt.get());
    }
}
