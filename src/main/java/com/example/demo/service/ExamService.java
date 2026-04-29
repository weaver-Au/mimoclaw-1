package com.example.demo.service;

import com.example.demo.entity.StudentAnswer;
import java.util.List;
import java.util.Map;

public interface ExamService {
    boolean submitAnswers(Long studentId, Long paperId, List<StudentAnswer> answers);
    List<StudentAnswer> getStudentAnswers(Long studentId, Long paperId);
    boolean hasSubmitted(Long studentId, Long paperId);
}
