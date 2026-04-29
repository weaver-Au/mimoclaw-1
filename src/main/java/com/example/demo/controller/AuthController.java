package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String doLogin(@RequestParam String role,
                          @RequestParam(required = false) String loginType,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String password,
                          @RequestParam(required = false) String phone,
                          @RequestParam(required = false) String verifyCode,
                          HttpSession session,
                          RedirectAttributes ra) {
        User user = null;

        if ("phone".equals(loginType)) {
            // Phone login: verify code (hardcoded "123456" for demo)
            phone = phone != null ? phone.trim() : "";
            if (phone.isEmpty()) {
                ra.addFlashAttribute("error", "请输入手机号");
                return "redirect:/login";
            }
            if (!"123456".equals(verifyCode)) {
                ra.addFlashAttribute("error", "验证码错误");
                return "redirect:/login";
            }
            user = userService.findByPhone(phone);
            if (user == null) {
                ra.addFlashAttribute("error", "该手机号未注册");
                return "redirect:/login";
            }
            if (!user.getRole().equals(role)) {
                ra.addFlashAttribute("error", "角色不匹配");
                return "redirect:/login";
            }
        } else {
            // Password login
            username = username != null ? username.trim() : "";
            password = password != null ? password.trim() : "";
            if (username.isEmpty() || password.isEmpty()) {
                ra.addFlashAttribute("error", "请输入用户名和密码");
                return "redirect:/login";
            }
            user = userService.login(username, password);
            if (user == null) {
                ra.addFlashAttribute("error", "用户名或密码错误");
                return "redirect:/login";
            }
            if (!user.getRole().equals(role)) {
                ra.addFlashAttribute("error", "角色不匹配，请选择正确的角色");
                return "redirect:/login";
            }
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
        // Input validation
        if (username == null || username.trim().length() < 3 || username.trim().length() > 50) {
            ra.addFlashAttribute("error", "用户名长度需要3-50个字符");
            return "redirect:/register";
        }
        if (password == null || password.length() < 6 || password.length() > 100) {
            ra.addFlashAttribute("error", "密码长度需要6-100个字符");
            return "redirect:/register";
        }
        if (realName == null || realName.trim().isEmpty() || realName.trim().length() > 50) {
            ra.addFlashAttribute("error", "请输入真实姓名（不超过50个字符）");
            return "redirect:/register";
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setRealName(realName.trim());
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
