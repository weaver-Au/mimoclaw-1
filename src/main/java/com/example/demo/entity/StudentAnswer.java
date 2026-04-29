package com.example.demo.entity;

import java.time.LocalDateTime;

public class StudentAnswer {
    private Long id;
    private Long studentId;
    private Long paperId;
    private Long questionId;
    private String answer;
    private Boolean isCorrect;
    private Integer score;
    private Boolean graded;
    private LocalDateTime submittedAt;
    private String studentName;
    private String questionContent;
    private String correctAnswer;
    private String questionType;
    private Integer questionScore; // score from paper_questions

    public StudentAnswer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getPaperId() { return paperId; }
    public void setPaperId(Long paperId) { this.paperId = paperId; }
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Boolean getGraded() { return graded; }
    public void setGraded(Boolean graded) { this.graded = graded; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getQuestionContent() { return questionContent; }
    public void setQuestionContent(String questionContent) { this.questionContent = questionContent; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    public Integer getQuestionScore() { return questionScore; }
    public void setQuestionScore(Integer questionScore) { this.questionScore = questionScore; }
}
