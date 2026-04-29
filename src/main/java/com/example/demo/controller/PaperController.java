package com.example.demo.controller;

import com.example.demo.entity.Paper;
import com.example.demo.entity.PaperQuestion;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.PaperService;
import com.example.demo.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) Long courseId,
                       HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("papers", paperService.search(name, courseId, user.getId(), null));
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        model.addAttribute("name", name);
        model.addAttribute("courseId", courseId);
        return "teacher/paper-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("paper", new Paper());
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        return "teacher/paper-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        Paper paper = paperService.findByIdWithQuestions(id);
        model.addAttribute("paper", paper);
        model.addAttribute("courses", courseService.findByTeacherId(user.getId()));
        return "teacher/paper-form";
    }

    @GetMapping("/selectQuestions/{id}")
    public String selectQuestions(@PathVariable Long id, Model model) {
        Paper paper = paperService.findByIdWithQuestions(id);
        model.addAttribute("paper", paper);
        model.addAttribute("allQuestions", questionService.findByCourseId(paper.getCourseId()));
        // Build a map of questionId -> score for existing paper questions
        java.util.Map<Long, Integer> selectedScores = new java.util.HashMap<>();
        if (paper.getPaperQuestions() != null) {
            for (com.example.demo.entity.PaperQuestion pq : paper.getPaperQuestions()) {
                selectedScores.put(pq.getQuestionId(), pq.getScore());
            }
        }
        model.addAttribute("selectedScores", selectedScores);
        return "teacher/select-questions";
    }

    @PostMapping("/save")
    public String save(Paper paper,
                       @RequestParam(required = false) Long[] questionIds,
                       @RequestParam(required = false) Integer[] questionScores,
                       HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loginUser");
        paper.setCreatorId(user.getId());

        List<PaperQuestion> pqs = new ArrayList<>();
        if (questionIds != null) {
            for (int i = 0; i < questionIds.length; i++) {
                PaperQuestion pq = new PaperQuestion();
                pq.setQuestionId(questionIds[i]);
                pq.setScore(questionScores != null && i < questionScores.length ? questionScores[i] : 1);
                pqs.add(pq);
            }
        }

        if (paper.getId() == null) {
            paper.setPublished(false);
            paperService.save(paper, pqs);
            ra.addFlashAttribute("success", "试卷创建成功");
        } else {
            paperService.update(paper, pqs);
            ra.addFlashAttribute("success", "试卷更新成功");
        }
        return "redirect:/teacher/papers";
    }

    @GetMapping("/publish/{id}")
    public String publish(@PathVariable Long id, RedirectAttributes ra) {
        paperService.publish(id, true);
        ra.addFlashAttribute("success", "试卷已发布");
        return "redirect:/teacher/papers";
    }

    @GetMapping("/unpublish/{id}")
    public String unpublish(@PathVariable Long id, RedirectAttributes ra) {
        paperService.publish(id, false);
        ra.addFlashAttribute("success", "试卷已取消发布");
        return "redirect:/teacher/papers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        paperService.delete(id);
        ra.addFlashAttribute("success", "试卷删除成功");
        return "redirect:/teacher/papers";
    }
}
