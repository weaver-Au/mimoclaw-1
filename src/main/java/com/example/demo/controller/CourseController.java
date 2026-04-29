package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String teacherName,
                       Model model) {
        model.addAttribute("courses", courseService.search(name, teacherName));
        model.addAttribute("name", name);
        model.addAttribute("teacherName", teacherName);
        return "admin/course-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("teachers", userService.findAllTeachers());
        return "admin/course-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("teachers", userService.findAllTeachers());
        return "admin/course-form";
    }

    @PostMapping("/save")
    public String save(Course course, RedirectAttributes ra) {
        if (course.getId() == null) {
            courseService.save(course);
            ra.addFlashAttribute("success", "课程添加成功");
        } else {
            courseService.update(course);
            ra.addFlashAttribute("success", "课程更新成功");
        }
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        courseService.delete(id);
        ra.addFlashAttribute("success", "课程删除成功");
        return "redirect:/admin/courses";
    }
}
