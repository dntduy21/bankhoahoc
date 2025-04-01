package com.example.demo.controller;

import com.example.demo.model.CourseCategory;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/QuanLyDanhMuc")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/LayDanhSachDanhMuc")
    public ResponseEntity<?> getAllCategories() {
        List<CourseCategory> categories = categoryRepository.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping("/ThemDanhMuc")
    public ResponseEntity<?> addCategory(@RequestBody CourseCategory category) {
        if (category.getCategoryId() == null || category.getCategoryId().isEmpty() || category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            return ResponseEntity.badRequest().body("categoryId và categoryName không được để trống");
        }
        if (categoryRepository.existsById(category.getCategoryId())) {
            return ResponseEntity.badRequest().body("Danh mục đã tồn tại");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok().body("Thêm danh mục thành công");
    }

    @DeleteMapping("/XoaDanhMuc/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId) {
        Optional<CourseCategory> categoryExist = categoryRepository.findById(categoryId);
        if (!categoryExist.isPresent()) {
            return ResponseEntity.badRequest().body("Danh mục không tồn tại");
        }
        if (!courseRepository.findByCategory_CategoryId(categoryId).isEmpty()) {
            return ResponseEntity.badRequest().body("Danh mục này đã có khóa học đăng ký, không thể xóa");
        }
        categoryRepository.deleteById(categoryId);
        return ResponseEntity.ok().body("Xóa danh mục thành công");
    }

    @PutMapping("/CapNhatDanhMuc")
    public ResponseEntity<?> updateCategory(@RequestBody CourseCategory category) {
        if (category.getCategoryId() == null || category.getCategoryName() == null) {
            return ResponseEntity.badRequest().body("categoryId và categoryName không được để trống");
        }
        if (!categoryRepository.existsById(category.getCategoryId())) {
            return ResponseEntity.badRequest().body("Danh mục không tồn tại");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok().body("Cập nhật danh mục thành công");
    }
}
