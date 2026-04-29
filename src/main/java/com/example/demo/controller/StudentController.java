package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.ExamService;
import com.example.demo.service.PaperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private PaperService paperService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("examCount", paperService.findPublished().size());
        return "student/dashboard";
    }
}
