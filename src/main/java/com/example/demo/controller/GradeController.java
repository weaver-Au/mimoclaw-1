package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Paper;
import com.example.demo.entity.StudentAnswer;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
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

    @Autowired
    private CourseService courseService;

    // ========== Student grade viewing ==========
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

    // ========== Teacher grading ==========
    @GetMapping("/teacher/grading")
    public String teacherGradingList(@RequestParam(required = false) Long courseId,
                                     HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        List<Course> courses = courseService.findByTeacherId(user.getId());
        List<Paper> papers;
        if (courseId != null) {
            papers = paperService.search(null, courseId, user.getId(), null);
        } else {
            papers = paperService.findByCreatorId(user.getId());
        }
        model.addAttribute("papers", papers);
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourseId", courseId);
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

    // ========== Admin grade viewing ==========
    @GetMapping("/admin/grades")
    public String adminGrades(@RequestParam(required = false) Long paperId,
                              @RequestParam(required = false) Long courseId,
                              @RequestParam(required = false) String className,
                              Model model) {
        List<Paper> papers;
        if (courseId != null) {
            papers = paperService.search(null, courseId, null, null);
        } else {
            papers = paperService.search(null, null, null, null);
        }
        List<Course> courses = courseService.findAll();

        if (paperId != null) {
            List<StudentAnswer> scores = gradeService.getScoresByPaper(paperId);
            // Filter by className if specified
            if (className != null && !className.isEmpty()) {
                scores.removeIf(s -> s.getStudentName() == null || !s.getStudentName().contains(className));
            }
            Map<String, Object> stats = gradeService.getPaperStatistics(paperId);
            model.addAttribute("scores", scores);
            model.addAttribute("stats", stats);
            model.addAttribute("selectedPaperId", paperId);
        }

        model.addAttribute("papers", papers);
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourseId", courseId);
        model.addAttribute("className", className);
        return "admin/grade-list";
    }
}
