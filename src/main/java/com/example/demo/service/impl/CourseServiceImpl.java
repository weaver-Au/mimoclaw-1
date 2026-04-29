package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course findById(Long id) {
        return courseMapper.findById(id);
    }

    @Override
    public List<Course> search(String name, String teacherName) {
        return courseMapper.search(name, teacherName);
    }

    @Override
    public List<Course> findByTeacherId(Long teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public boolean save(Course course) {
        return courseMapper.insert(course) > 0;
    }

    @Override
    public boolean update(Course course) {
        return courseMapper.update(course) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return courseMapper.deleteById(id) > 0;
    }
}
