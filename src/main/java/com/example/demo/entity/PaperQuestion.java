package com.example.demo.entity;

public class PaperQuestion {
    private Long id;
    private Long paperId;
    private Long questionId;
    private Integer sortOrder;
    private Integer score;
    private Question question; // populated for display

    public PaperQuestion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPaperId() { return paperId; }
    public void setPaperId(Long paperId) { this.paperId = paperId; }
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
}
