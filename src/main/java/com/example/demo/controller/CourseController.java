package com.example.demo.controller;

import com.example.demo.DTO.CourseResponse;
import com.example.demo.DTO.CreaterResponse;
import com.example.demo.DTO.RegistrationRequest;
import com.example.demo.model.*;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserCourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/QuanLyKhoaHoc")
@CrossOrigin(origins = "*")
public class CourseController {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/LayDanhSachKhoaHoc")
    public ResponseEntity<?> getCourse() {
        List<Course> listCourse = courseRepository.findAll();

        if (listCourse.isEmpty()) {
            return ResponseEntity.status(404).body("No course found");
        }

        List<CourseResponse> formattedCourses = listCourse.stream().map(course ->
                new CourseResponse(
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getPrice(),
                        course.getDescription(),
                        course.getImage(),
                        course.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        (course.getUpdateDate() != null)
                                ? course.getUpdateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                : "Chưa cập nhật",
                        new CreaterResponse(
                                course.getCreator().getUsername(),
                                course.getCreator().getFullName(),
                                course.getCreator().getRole().getRoleId(),
                                course.getCreator().getRole().getRoleName()
                        ),
                        course.getCategory().getCategoryName()
                )).collect(Collectors.toList());

        return ResponseEntity.ok().body(formattedCourses);
    }

    @GetMapping("/LayThongTinKhoaHocTheoMaKhoaHoc/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Khóa học không tồn tại");
        }

        Course course = courseOptional.get();

        CourseResponse formattedCourse = new CourseResponse(
                course.getCourseId(),
                course.getCourseName(),
                course.getPrice(),
                course.getDescription(),
                course.getImage(),
                course.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                (course.getUpdateDate() != null)
                        ? course.getUpdateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        : "Chưa cập nhật",
                new CreaterResponse(
                        course.getCreator().getUsername(),
                        course.getCreator().getFullName(),
                        course.getCreator().getRole().getRoleId(),
                        course.getCreator().getRole().getRoleName()
                ),
                (course.getCategory() != null) ? course.getCategory().getCategoryName() : null
        );

        return ResponseEntity.ok().body(formattedCourse);
    }

    // Lấy danh sách khóa học theo danh mục
    @GetMapping("/LayDanhSachKhoaHocTheoDanhMuc/{categoryId}")
    public ResponseEntity<?> getCourseByCategory(@PathVariable String categoryId) {
        List<Course> listCourse = courseRepository.findByCategory_CategoryId(categoryId);
        return ResponseEntity.ok().body(listCourse);
    }

    // Lấy danh sách khóa học theo tên khóa học
    @GetMapping("/LayDanhSachKhoaHocTheoTenKhoaHoc/{courseName}")
    public ResponseEntity<?> getCourseByName(@PathVariable String courseName) {
        List<Course> listCourse = courseRepository.findByCourseNameContainingIgnoreCase(courseName);
        return ResponseEntity.ok().body(listCourse);
    }

    // Lấy danh sách khóa học theo mã người dùng (teacher)
    @GetMapping("/LayDanhSachKhoaHocTheoMaGiangVien/{userId}")
    public ResponseEntity<?> getCourseByTeacher(@PathVariable String userId) {
        List<Course> listCourse = courseRepository.findByCreator_UserId(userId);
        List<CourseResponse> formattedCourses = listCourse.stream().map(course ->
                new CourseResponse(
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getPrice(),
                        course.getDescription(),
                        course.getImage(),
                        course.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        (course.getUpdateDate() != null)
                                ? course.getUpdateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                : "Chưa cập nhật",
                        new CreaterResponse(
                                course.getCreator().getUsername(),
                                course.getCreator().getFullName(),
                                course.getCreator().getRole().getRoleId(),
                                course.getCreator().getRole().getRoleName()
                        ),
                        course.getCategory() != null ? course.getCategory().getCategoryName() : null
                )).collect(Collectors.toList());
        return ResponseEntity.ok().body(formattedCourses);
    }

    // API: Lấy danh sách khóa học bởi học viên
    @GetMapping("/LayDanhSachKhoaHocTheoMaHocVien/{userId}")
    public ResponseEntity<?> getCourseByStudent(@PathVariable String userId) {
        List<UserCourse> studentCourses = userCourseRepository.findByUser_UserId(userId);

        if (studentCourses.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy khóa học nào cho học viên này");
        }

        List<CourseResponse> studentCoursesFormatted = studentCourses.stream().map(studentCourse ->
                new CourseResponse(
                        studentCourse.getCourse().getCourseId(),
                        studentCourse.getCourse().getCourseName(),
                        studentCourse.getCourse().getPrice(),
                        studentCourse.getCourse().getDescription(),
                        studentCourse.getCourse().getImage(),
                        studentCourse.getCourse().getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        (studentCourse.getCourse().getUpdateDate() != null)
                                ? studentCourse.getCourse().getUpdateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                : "Chưa cập nhật",
                        new CreaterResponse(
                                studentCourse.getCourse().getCreator().getUsername(),
                                studentCourse.getCourse().getCreator().getFullName(),
                                studentCourse.getCourse().getCreator().getRole().getRoleId(),
                                studentCourse.getCourse().getCreator().getRole().getRoleName()
                        ),
                        studentCourse.getCourse().getCategory().getCategoryName()
                )).collect(Collectors.toList());

        return ResponseEntity.ok().body(studentCoursesFormatted);
    }

    // API: Lấy danh sách khóa học thông qua từ khóa tìm kiếm
    @GetMapping("/LayDanhSachKhoaHocTheoTuKhoaTimKiem/{keyword}")
    public ResponseEntity<?> getCourseByKeyword(@PathVariable String keyword) {
        List<Course> listCourse = courseRepository.findByCourseNameContainingIgnoreCase(keyword);

        if (listCourse.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy khóa học nào với từ khóa này");
        }

        return ResponseEntity.ok().body(listCourse);
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/DangKyKhoaHoc")
    public ResponseEntity<?> addCoursesToStudent(@RequestBody RegistrationRequest request) {
        String username = request.getUsername();
        List<String> listCoursesId = request.getListCoursesId();

        // Kiểm tra học viên có tồn tại không
        Optional<User> studentOptional = userRepository.findByUsername(username);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy tài khoản");
        }
        User student = studentOptional.get();

        // Tìm các khóa học trong danh sách
        List<Course> courses = courseRepository.findAllById(listCoursesId);
        if (courses.size() != listCoursesId.size()) {
            return ResponseEntity.status(404).body("Một số khóa học trong danh sách không tồn tại");
        }

        // Lấy danh sách khóa học đã đăng ký
        List<UserCourse> existingEnrollments = userCourseRepository.findByUser_UserIdAndCourse_CourseIdIn(student.getUserId(), listCoursesId);
        List<String> existingCourseIds = existingEnrollments.stream()
                .map(enrollment -> enrollment.getId().getCourseId()) // Dùng getId().getCourseId()
                .collect(Collectors.toList());


        // Lọc ra các khóa học chưa được đăng ký
        List<String> newCoursesToEnroll = listCoursesId.stream()
                .filter(courseId -> !existingCourseIds.contains(courseId))
                .collect(Collectors.toList());

        if (newCoursesToEnroll.isEmpty()) {
            return ResponseEntity.status(400).body("Người dùng đã đăng ký tất cả các khóa học này");
        }

        List<UserCourse> newEnrollments = newCoursesToEnroll.stream().map(courseId -> {
            UserCourse enrollment = new UserCourse();

            // Tạo đối tượng UserCourseId và gán vào enrollment
            UserCourseId userCourseId = new UserCourseId();
            userCourseId.setUserId(student.getUserId());
            userCourseId.setCourseId(courseId);

            enrollment.setId(userCourseId); // Gán ID vào enrollment
            enrollment.setRegistrationDate(LocalDate.now());

            return enrollment;
        }).collect(Collectors.toList());


        userCourseRepository.saveAll(newEnrollments);

        return ResponseEntity.status(201).body("Đã mua các khóa học thành công");
    }


    @PostMapping("/ThemKhoaHoc")
    public ResponseEntity<?> addCourse(
            @RequestParam String courseName,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam String creatorAccount,
            @RequestParam String courseType,
            @RequestParam("file") MultipartFile file // Ảnh upload
    ) {
        try {
            // Kiểm tra người dùng
            Optional<User> userOpt = userRepository.findByUsername(creatorAccount);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Người dùng không tồn tại");
            }
            User userExist = userOpt.get();

            // Kiểm tra danh mục
            Optional<CourseCategory> catOpt = categoryRepository.findByCategoryName(courseType);
            if (catOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Danh mục khóa học không tồn tại");
            }
            CourseCategory categoryExist = catOpt.get();

            // Lấy khóa học cuối cùng theo categoryId
            String categoryId = categoryExist.getCategoryId();
            Optional<Course> lastCourseOpt = courseRepository.findTopByCategory_CategoryIdOrderByCourseIdDesc(categoryId);

            // Sinh mã khóa học
            String newCourseId;
            if (lastCourseOpt.isPresent()) {
                Course lastCourse = lastCourseOpt.get();
                int lastNumber = Integer.parseInt(lastCourse.getCourseId().substring(categoryId.length()));
                newCourseId = categoryId + String.format("%05d", lastNumber + 1);
            } else {
                newCourseId = categoryId + "00001";
            }

            // Kiểm tra tính hợp lệ của trường
            if (courseName.isEmpty() || description.isEmpty() || price < 1000 || courseType.isEmpty()) {
                return ResponseEntity.badRequest().body("Trường không hợp lệ hoặc giá < 1000");
            }

            // Lưu file ảnh
            String fileName = null;
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();

                // **Tạo thư mục nếu chưa có**
                File directory = new File(uploadPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                    System.out.println("Created directory: " + uploadPath);
                }

                String filePath = Paths.get(uploadPath, fileName).toString();
                System.out.println("Saving file to: " + filePath);
                file.transferTo(new File(filePath));
            } else {
                return ResponseEntity.badRequest().body("Không có file ảnh!");
            }

            // Tạo khóa học
            Course newCourse = new Course();
            newCourse.setCourseId(newCourseId);
            newCourse.setCourseName(courseName);
            newCourse.setDescription(description);
            newCourse.setImage(fileName);
            newCourse.setPrice(price);
            newCourse.setCreator(userExist);
            newCourse.setCategory(categoryExist);
            newCourse.setCreationDate(LocalDate.now());

            courseRepository.save(newCourse);

            // Trả về thông tin khóa học
            return ResponseEntity.ok("Thêm khóa học thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to create course: " + e.getMessage());
        }
    }

    @PutMapping("/CapNhatKhoaHoc")
    public ResponseEntity<?> updateCourse(
            @RequestParam String courseId,
            @RequestParam String courseName,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) {
        try {
            // Tìm khóa học
            Optional<Course> courseOpt = courseRepository.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Khóa học không tồn tại");
            }
            Course course = courseOpt.get();

            // Nếu có ảnh cũ, xóa
            if (course.getImage() != null && !course.getImage().isEmpty() && file != null && !file.isEmpty()) {
                String oldFilePath = uploadPath + course.getImage();
                FileUploadUtil.deleteUploadedFile(oldFilePath);
            }

            // Cập nhật ảnh mới
            String newImageName = course.getImage(); // Giữ ảnh cũ nếu không upload mới
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File destFile = new File(uploadPath + fileName);
                file.transferTo(destFile);
                newImageName = fileName;
            }


            // Cập nhật khóa học
            course.setCourseName(courseName);
            course.setDescription(description);
            course.setPrice(price);
            course.setImage(newImageName);
            course.setUpdateDate(LocalDate.now());

            courseRepository.save(course);

            return ResponseEntity.ok("Cập nhật khóa học thành công!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Lỗi file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật khóa học: " + e.getMessage());
        }
    }

    // Xóa khóa học
    @DeleteMapping("/XoaKhoaHoc/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        // Tìm khóa học
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Khóa học không tồn tại");
        }
        Course course = courseOpt.get();

        // Kiểm tra khóa học đã có học viên đăng ký chưa
        List<UserCourse> enrolledCourses = userCourseRepository.findById_CourseId(courseId);
        if (!enrolledCourses.isEmpty()) {
            return ResponseEntity.badRequest().body("Khóa học này đã có học viên, không thể xóa");
        }

        // Xóa khóa học
        courseRepository.deleteById(courseId);

        // Xóa file ảnh nếu có
        if (course.getImage() != null && !course.getImage().isEmpty()) {
            String filePath = uploadPath + course.getImage();
            try {
                FileUploadUtil.deleteUploadedFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileUploadUtil.deleteUploadedFile(filePath);
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Error deleting course image: " + e.getMessage());
            }
        }

        return ResponseEntity.ok("Xóa khóa học thành công!");
    }
}

