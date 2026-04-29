package com.example.demo.service;

import com.example.demo.entity.StudentAnswer;
import java.util.List;
import java.util.Map;

public interface GradeService {
    List<StudentAnswer> getScoresByPaper(Long paperId);
    List<StudentAnswer> getAnswersByStudentAndPaper(Long studentId, Long paperId);
    List<StudentAnswer> getStudentPapers(Long studentId);
    Map<String, Object> getPaperStatistics(Long paperId);
    List<StudentAnswer> getAllAnswersByPaper(Long paperId);
    StudentAnswer findAnswerById(Long id);
    void updateAnswerScore(Long id, Integer score, Boolean graded, Boolean isCorrect);
}
