package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String role,
                          HttpSession session,
                          RedirectAttributes ra) {
        User user = userService.login(username, password);
        if (user == null) {
            ra.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/login";
        }
        if (!user.getRole().equals(role)) {
            ra.addFlashAttribute("error", "角色不匹配，请选择正确的角色");
            return "redirect:/login";
        }
        session.setAttribute("loginUser", user);
        switch (user.getRole()) {
            case "ADMIN": return "redirect:/admin/dashboard";
            case "TEACHER": return "redirect:/teacher/dashboard";
            default: return "redirect:/student/dashboard";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String realName,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false) String className,
                             RedirectAttributes ra) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealName(realName);
        user.setPhone(phone);
        user.setClassName(className);
        user.setRole("STUDENT");

        if (userService.register(user)) {
            ra.addFlashAttribute("success", "注册成功，请登录");
            return "redirect:/login";
        } else {
            ra.addFlashAttribute("error", "用户名已存在");
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping({"/", "/index"})
    public String index(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) return "redirect:/login";
        switch (user.getRole()) {
            case "ADMIN": return "redirect:/admin/dashboard";
            case "TEACHER": return "redirect:/teacher/dashboard";
            default: return "redirect:/student/dashboard";
        }
    }
}

// Teacher and Student dashboard - added as supplementary
