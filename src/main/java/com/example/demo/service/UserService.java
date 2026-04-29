package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    User findById(Long id);
    User findByUsername(String username);
    User findByPhone(String phone);
    List<User> search(String username, String realName, String role);
    List<User> findAllTeachers();
    List<User> findAll();
    boolean save(User user);
    boolean update(User user);
    boolean delete(Long id);
    boolean updatePassword(Long id, String oldPassword, String newPassword);
    int count();
}
