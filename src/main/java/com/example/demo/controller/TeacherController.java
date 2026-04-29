package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.PaperService;
import com.example.demo.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("questionCount", questionService.findByCreatorId(user.getId()).size());
        model.addAttribute("paperCount", paperService.findByCreatorId(user.getId()).size());
        model.addAttribute("courseCount", courseService.findByTeacherId(user.getId()).size());
        return "teacher/dashboard";
    }
}
