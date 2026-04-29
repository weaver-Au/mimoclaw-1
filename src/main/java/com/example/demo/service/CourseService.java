package com.example.demo.service;

import com.example.demo.entity.Course;
import java.util.List;

public interface CourseService {
    Course findById(Long id);
    List<Course> search(String name, String teacherName);
    List<Course> findByTeacherId(Long teacherId);
    List<Course> findAll();
    boolean save(Course course);
    boolean update(Course course);
    boolean delete(Long id);
}
