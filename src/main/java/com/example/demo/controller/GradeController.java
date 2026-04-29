package com.example.demo.controller;

import com.example.demo.entity.Paper;
import com.example.demo.entity.StudentAnswer;
import com.example.demo.entity.User;
import com.example.demo.service.GradeService;
import com.example.demo.service.PaperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private PaperService paperService;

    // Student grade viewing
    @GetMapping("/student/grades")
    public String studentGrades(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        List<StudentAnswer> papers = gradeService.getStudentPapers(user.getId());
        model.addAttribute("papers", papers);
        return "student/grade-list";
    }

    @GetMapping("/student/grades/{paperId}")
    public String studentGradeDetail(@PathVariable Long paperId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        List<StudentAnswer> answers = gradeService.getAnswersByStudentAndPaper(user.getId(), paperId);
        Paper paper = paperService.findById(paperId);

        int totalScore = 0;
        for (StudentAnswer sa : answers) {
            totalScore += sa.getScore() != null ? sa.getScore() : 0;
        }

        model.addAttribute("answers", answers);
        model.addAttribute("paper", paper);
        model.addAttribute("totalScore", totalScore);
        return "student/grade-detail";
    }

    // Teacher grading
    @GetMapping("/teacher/grading")
    public String teacherGradingList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        List<Paper> papers = paperService.findByCreatorId(user.getId());
        model.addAttribute("papers", papers);
        return "teacher/grading-list";
    }

    @GetMapping("/teacher/grading/{paperId}")
    public String teacherGradingDetail(@PathVariable Long paperId, Model model) {
        List<StudentAnswer> scores = gradeService.getScoresByPaper(paperId);
        Map<String, Object> stats = gradeService.getPaperStatistics(paperId);
        Paper paper = paperService.findById(paperId);

        model.addAttribute("scores", scores);
        model.addAttribute("stats", stats);
        model.addAttribute("paper", paper);
        return "teacher/grading-detail";
    }

    @GetMapping("/teacher/grading/{paperId}/student/{studentId}")
    public String teacherViewStudentPaper(@PathVariable Long paperId, @PathVariable Long studentId, Model model) {
        List<StudentAnswer> answers = gradeService.getAnswersByStudentAndPaper(studentId, paperId);
        Paper paper = paperService.findById(paperId);

        int totalScore = 0;
        for (StudentAnswer sa : answers) {
            totalScore += sa.getScore() != null ? sa.getScore() : 0;
        }

        model.addAttribute("answers", answers);
        model.addAttribute("paper", paper);
        model.addAttribute("totalScore", totalScore);
        return "teacher/student-paper";
    }

    // Admin grade viewing
    @GetMapping("/admin/grades")
    public String adminGrades(@RequestParam(required = false) Long paperId, Model model) {
        if (paperId != null) {
            List<StudentAnswer> scores = gradeService.getScoresByPaper(paperId);
            Map<String, Object> stats = gradeService.getPaperStatistics(paperId);
            model.addAttribute("scores", scores);
            model.addAttribute("stats", stats);
            model.addAttribute("selectedPaperId", paperId);
        }
        List<Paper> papers = paperService.search(null, null, null, null);
        model.addAttribute("papers", papers);
        return "admin/grade-list";
    }
}
