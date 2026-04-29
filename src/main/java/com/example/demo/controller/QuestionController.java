package com.example.demo.controller;

import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teacher/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public String list(@RequestParam(required = false) Long courseId,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) Integer difficulty,
                       @RequestParam(required = false) String keyword,
                       HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("questions", questionService.search(courseId, type, difficulty, keyword));
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        model.addAttribute("courseId", courseId);
        model.addAttribute("type", type);
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("keyword", keyword);
        return "teacher/question-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("question", new Question());
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        return "teacher/question-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("question", questionService.findById(id));
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        return "teacher/question-form";
    }

    @PostMapping("/save")
    public String save(Question question, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loginUser");
        if (question.getId() == null) {
            question.setCreatorId(user.getId());
            questionService.save(question);
            ra.addFlashAttribute("success", "试题添加成功");
        } else {
            questionService.update(question);
            ra.addFlashAttribute("success", "试题更新成功");
        }
        return "redirect:/teacher/questions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            questionService.delete(id);
            ra.addFlashAttribute("success", "试题删除成功");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "删除失败：该试题可能已关联到试卷");
        }
        return "redirect:/teacher/questions";
    }
}
