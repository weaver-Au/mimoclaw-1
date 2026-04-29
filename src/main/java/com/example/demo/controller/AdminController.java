package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.CourseService;
import com.example.demo.service.PaperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", userService.count());
        model.addAttribute("teacherCount", userService.findAllTeachers().size());
        model.addAttribute("courseCount", courseService.findAll().size());
        return "admin/dashboard";
    }

    // ---- User Management ----
    @GetMapping("/users")
    public String userList(@RequestParam(required = false) String username,
                           @RequestParam(required = false) String realName,
                           @RequestParam(required = false) String role,
                           Model model) {
        List<User> users = userService.search(username, realName, role);
        model.addAttribute("users", users);
        model.addAttribute("username", username);
        model.addAttribute("realName", realName);
        model.addAttribute("role", role);
        return "admin/user-list";
    }

    @GetMapping("/users/add")
    public String userAddForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    @GetMapping("/users/edit/{id}")
    public String userEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        User user = userService.findById(id);
        if (user == null) {
            ra.addFlashAttribute("error", "用户不存在");
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    @PostMapping("/users/save")
    public String userSave(User user, RedirectAttributes ra) {
        if (user.getId() == null) {
            // New user - password is required
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                ra.addFlashAttribute("error", "新用户必须设置密码");
                return "redirect:/admin/users/add";
            }
            if (userService.save(user)) {
                ra.addFlashAttribute("success", "用户添加成功");
            } else {
                ra.addFlashAttribute("error", "用户名已存在");
            }
        } else {
            // Existing user - if password is empty, keep old password
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                User existing = userService.findById(user.getId());
                if (existing == null) {
                    ra.addFlashAttribute("error", "用户不存在");
                    return "redirect:/admin/users";
                }
                user.setPassword(existing.getPassword());
            }
            userService.update(user);
            ra.addFlashAttribute("success", "用户更新成功");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("loginUser");
        if (currentUser.getId().equals(id)) {
            ra.addFlashAttribute("error", "不能删除当前登录的用户");
            return "redirect:/admin/users";
        }
        try {
            userService.delete(id);
            ra.addFlashAttribute("success", "用户删除成功");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "删除失败：该用户可能关联了课程或考试数据");
        }
        return "redirect:/admin/users";
    }
}
