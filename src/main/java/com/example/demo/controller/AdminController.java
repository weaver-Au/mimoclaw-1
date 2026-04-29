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
    public String userEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    @PostMapping("/users/save")
    public String userSave(User user, RedirectAttributes ra) {
        if (user.getId() == null) {
            if (userService.save(user)) {
                ra.addFlashAttribute("success", "用户添加成功");
            } else {
                ra.addFlashAttribute("error", "用户名已存在");
            }
        } else {
            userService.update(user);
            ra.addFlashAttribute("success", "用户更新成功");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable Long id, RedirectAttributes ra) {
        userService.delete(id);
        ra.addFlashAttribute("success", "用户删除成功");
        return "redirect:/admin/users";
    }
}
