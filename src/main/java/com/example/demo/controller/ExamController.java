package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.ExamService;
import com.example.demo.service.PaperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student/exam")
public class ExamController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private ExamService examService;

    @GetMapping("/list")
    public String examList(Model model) {
        List<Paper> papers = paperService.findPublished();
        model.addAttribute("papers", papers);
        return "student/exam-list";
    }

    @GetMapping("/start/{paperId}")
    public String startExam(@PathVariable Long paperId, HttpSession session, Model model, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loginUser");

        // Check if already submitted
        if (examService.hasSubmitted(user.getId(), paperId)) {
            ra.addFlashAttribute("error", "您已提交过该试卷");
            return "redirect:/student/exam/list";
        }

        Paper paper = paperService.findByIdWithQuestions(paperId);
        if (paper == null || !paper.getPublished()) {
            ra.addFlashAttribute("error", "试卷不存在或未发布");
            return "redirect:/student/exam/list";
        }

        model.addAttribute("paper", paper);
        return "student/exam-take";
    }

    @PostMapping("/submit/{paperId}")
    public String submitExam(@PathVariable Long paperId,
                             @RequestParam("questionIds") Long[] questionIds,
                             @RequestParam("answers") String[] answers,
                             HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loginUser");

        if (examService.hasSubmitted(user.getId(), paperId)) {
            ra.addFlashAttribute("error", "您已提交过该试卷");
            return "redirect:/student/exam/list";
        }

        List<StudentAnswer> saList = new ArrayList<>();
        for (int i = 0; i < questionIds.length; i++) {
            StudentAnswer sa = new StudentAnswer();
            sa.setStudentId(user.getId());
            sa.setPaperId(paperId);
            sa.setQuestionId(questionIds[i]);
            sa.setAnswer(i < answers.length ? answers[i] : "");
            saList.add(sa);
        }

        examService.submitAnswers(user.getId(), paperId, saList);
        ra.addFlashAttribute("success", "试卷提交成功");
        return "redirect:/student/exam/result/" + paperId;
    }

    @GetMapping("/result/{paperId}")
    public String examResult(@PathVariable Long paperId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        List<StudentAnswer> answers = examService.getStudentAnswers(user.getId(), paperId);
        Paper paper = paperService.findById(paperId);

        int totalScore = 0;
        for (StudentAnswer sa : answers) {
            totalScore += sa.getScore() != null ? sa.getScore() : 0;
        }

        model.addAttribute("answers", answers);
        model.addAttribute("paper", paper);
        model.addAttribute("totalScore", totalScore);
        return "student/exam-result";
    }
}
