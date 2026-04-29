package com.example.demo.service;

import com.example.demo.entity.Question;
import java.util.List;

public interface QuestionService {
    Question findById(Long id);
    List<Question> search(Long courseId, String type, Integer difficulty, String keyword);
    List<Question> findByCreatorId(Long creatorId);
    List<Question> findByCourseId(Long courseId);
    List<Question> findAll();
    boolean save(Question question);
    boolean update(Question question);
    boolean delete(Long id);
}
