package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Select("<script>" +
            "SELECT * FROM users WHERE 1=1 " +
            "<if test='username != null and username != \"\"'>AND username LIKE CONCAT('%',#{username},'%')</if>" +
            "<if test='realName != null and realName != \"\"'>AND real_name LIKE CONCAT('%',#{realName},'%')</if>" +
            "<if test='role != null and role != \"\"'>AND role = #{role}</if>" +
            " ORDER BY id DESC" +
            "</script>")
    List<User> search(@Param("username") String username, @Param("realName") String realName, @Param("role") String role);

    @Select("SELECT * FROM users WHERE role = 'TEACHER'")
    List<User> findAllTeachers();

    @Insert("INSERT INTO users(username, password, real_name, phone, class_name, role) " +
            "VALUES(#{username}, #{password}, #{realName}, #{phone}, #{className}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE users SET username=#{username}, real_name=#{realName}, phone=#{phone}, " +
            "class_name=#{className}, role=#{role} WHERE id=#{id}")
    int update(User user);

    @Update("UPDATE users SET password=#{password} WHERE id=#{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM users WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM users")
    int count();

    @Select("SELECT * FROM users")
    List<User> findAll();
}
