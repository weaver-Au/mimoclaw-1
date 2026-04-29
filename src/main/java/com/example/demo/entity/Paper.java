package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Paper {
    private Long id;
    private String name;
    private Long courseId;
    private Long creatorId;
    private Integer totalScore;
    private Integer duration; // minutes
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean published;
    private LocalDateTime createdAt;
    private String courseName;
    private String creatorName;
    private List<Question> questions; // populated for exam
    private List<PaperQuestion> paperQuestions; // populated for exam

    public Paper() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    public List<PaperQuestion> getPaperQuestions() { return paperQuestions; }
    public void setPaperQuestions(List<PaperQuestion> paperQuestions) { this.paperQuestions = paperQuestions; }
}
