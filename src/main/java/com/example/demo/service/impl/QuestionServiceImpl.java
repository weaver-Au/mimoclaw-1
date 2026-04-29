package com.example.demo.service.impl;

import com.example.demo.entity.Question;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Question findById(Long id) {
        return questionMapper.findById(id);
    }

    @Override
    public List<Question> search(Long courseId, String type, Integer difficulty, String keyword) {
        return questionMapper.search(courseId, type, difficulty, keyword);
    }

    @Override
    public List<Question> findByCreatorId(Long creatorId) {
        return questionMapper.findByCreatorId(creatorId);
    }

    @Override
    public List<Question> findByCourseId(Long courseId) {
        return questionMapper.findByCourseId(courseId);
    }

    @Override
    public List<Question> findAll() {
        return questionMapper.findAll();
    }

    @Override
    public boolean save(Question question) {
        return questionMapper.insert(question) > 0;
    }

    @Override
    public boolean update(Question question) {
        return questionMapper.update(question) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return questionMapper.deleteById(id) > 0;
    }
}
