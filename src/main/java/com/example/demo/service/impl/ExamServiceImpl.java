package com.example.demo.service.impl;

import com.example.demo.entity.PaperQuestion;
import com.example.demo.entity.Question;
import com.example.demo.entity.StudentAnswer;
import com.example.demo.mapper.PaperQuestionMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.StudentAnswerMapper;
import com.example.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;

    @Autowired
    private PaperQuestionMapper paperQuestionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public boolean submitAnswers(Long studentId, Long paperId, List<StudentAnswer> answers) {
        // Delete previous answers if resubmitting
        studentAnswerMapper.deleteByStudentAndPaper(studentId, paperId);

        for (StudentAnswer sa : answers) {
            sa.setStudentId(studentId);
            sa.setPaperId(paperId);
            sa.setGraded(false);

            // Auto-grade objective questions
            Question question = questionMapper.findById(sa.getQuestionId());
            if (question != null) {
                PaperQuestion pq = paperQuestionMapper.findByPaperAndQuestion(paperId, sa.getQuestionId());
                int questionScore = (pq != null) ? pq.getScore() : 0;

                if ("JUDGE".equals(question.getType()) || "SINGLE".equals(question.getType())) {
                    if (question.getAnswer().equals(sa.getAnswer())) {
                        sa.setIsCorrect(true);
                        sa.setScore(questionScore);
                    } else {
                        sa.setIsCorrect(false);
                        sa.setScore(0);
                    }
                    sa.setGraded(true);
                } else if ("MULTI".equals(question.getType())) {
                    // Multi-choice: compare sorted answers
                    String correctAnswer = sortString(question.getAnswer());
                    String studentAns = sa.getAnswer() != null ? sortString(sa.getAnswer()) : "";
                    if (correctAnswer.equals(studentAns)) {
                        sa.setIsCorrect(true);
                        sa.setScore(questionScore);
                    } else {
                        sa.setIsCorrect(false);
                        sa.setScore(0);
                    }
                    sa.setGraded(true);
                } else if ("ESSAY".equals(question.getType())) {
                    // Essay/subjective - needs manual grading by teacher
                    sa.setIsCorrect(null);
                    sa.setScore(0);
                    sa.setGraded(false);
                } else {
                    sa.setIsCorrect(false);
                    sa.setScore(0);
                    sa.setGraded(false);
                }
            }

            studentAnswerMapper.insert(sa);
        }
        return true;
    }

    private String sortString(String s) {
        if (s == null) return "";
        String[] parts = s.split(",");
        java.util.Arrays.sort(parts);
        return String.join(",", parts);
    }

    @Override
    public List<StudentAnswer> getStudentAnswers(Long studentId, Long paperId) {
        return studentAnswerMapper.findByStudentAndPaper(studentId, paperId);
    }

    @Override
    public boolean hasSubmitted(Long studentId, Long paperId) {
        return studentAnswerMapper.countByStudentAndPaper(studentId, paperId) > 0;
    }
}
