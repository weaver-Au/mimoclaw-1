package com.example.demo.service.impl;

import com.example.demo.entity.StudentAnswer;
import com.example.demo.mapper.StudentAnswerMapper;
import com.example.demo.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;

    @Override
    public List<StudentAnswer> getScoresByPaper(Long paperId) {
        return studentAnswerMapper.findStudentScoresByPaper(paperId);
    }

    @Override
    public List<StudentAnswer> getAnswersByStudentAndPaper(Long studentId, Long paperId) {
        return studentAnswerMapper.findByStudentAndPaper(studentId, paperId);
    }

    @Override
    public List<StudentAnswer> getStudentPapers(Long studentId) {
        return studentAnswerMapper.findPapersByStudent(studentId);
    }

    @Override
    public Map<String, Object> getPaperStatistics(Long paperId) {
        List<StudentAnswer> scores = studentAnswerMapper.findStudentScoresByPaper(paperId);
        Map<String, Object> stats = new HashMap<>();

        if (scores.isEmpty()) {
            stats.put("count", 0);
            stats.put("avgScore", 0);
            stats.put("maxScore", 0);
            stats.put("minScore", 0);
            stats.put("passRate", 0);
            return stats;
        }

        int total = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int passCount = 0;

        for (StudentAnswer sa : scores) {
            int score = sa.getScore() != null ? sa.getScore() : 0;
            total += score;
            max = Math.max(max, score);
            min = Math.min(min, score);
            if (score >= 60) passCount++;
        }

        stats.put("count", scores.size());
        stats.put("avgScore", Math.round((double) total / scores.size() * 100.0) / 100.0);
        stats.put("maxScore", max);
        stats.put("minScore", min);
        stats.put("passRate", Math.round((double) passCount / scores.size() * 10000.0) / 100.0);

        return stats;
    }

    @Override
    public List<StudentAnswer> getAllAnswersByPaper(Long paperId) {
        return studentAnswerMapper.findByPaperId(paperId);
    }
}
