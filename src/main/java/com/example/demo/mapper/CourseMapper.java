package com.example.demo.mapper;

import com.example.demo.entity.Course;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT c.*, u.real_name as teacher_name FROM courses c LEFT JOIN users u ON c.teacher_id = u.id WHERE c.id = #{id}")
    Course findById(Long id);

    @Select("<script>" +
            "SELECT c.*, u.real_name as teacher_name FROM courses c LEFT JOIN users u ON c.teacher_id = u.id WHERE 1=1 " +
            "<if test='name != null and name != \"\"'>AND c.name LIKE CONCAT('%',#{name},'%')</if>" +
            "<if test='teacherName != null and teacherName != \"\"'>AND u.real_name LIKE CONCAT('%',#{teacherName},'%')</if>" +
            " ORDER BY c.id DESC" +
            "</script>")
    List<Course> search(@Param("name") String name, @Param("teacherName") String teacherName);

    @Select("SELECT c.*, u.real_name as teacher_name FROM courses c LEFT JOIN users u ON c.teacher_id = u.id WHERE c.teacher_id = #{teacherId} ORDER BY c.id DESC")
    List<Course> findByTeacherId(Long teacherId);

    @Select("SELECT c.*, u.real_name as teacher_name FROM courses c LEFT JOIN users u ON c.teacher_id = u.id ORDER BY c.id DESC")
    List<Course> findAll();

    @Insert("INSERT INTO courses(name, teacher_id, description) VALUES(#{name}, #{teacherId}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    @Update("UPDATE courses SET name=#{name}, teacher_id=#{teacherId}, description=#{description} WHERE id=#{id}")
    int update(Course course);

    @Delete("DELETE FROM courses WHERE id=#{id}")
    int deleteById(Long id);
}
