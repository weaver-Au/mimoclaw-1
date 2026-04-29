package com.example.demo.service.impl;

import com.example.demo.entity.Paper;
import com.example.demo.entity.PaperQuestion;
import com.example.demo.entity.Question;
import com.example.demo.mapper.PaperMapper;
import com.example.demo.mapper.PaperQuestionMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperQuestionMapper paperQuestionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Paper findById(Long id) {
        return paperMapper.findById(id);
    }

    @Override
    public Paper findByIdWithQuestions(Long id) {
        Paper paper = paperMapper.findById(id);
        if (paper != null) {
            List<PaperQuestion> pqs = paperQuestionMapper.findByPaperId(id);
            List<Question> questions = new ArrayList<>();
            for (PaperQuestion pq : pqs) {
                if (pq.getQuestion() != null && pq.getQuestion().getId() != null) {
                    questions.add(pq.getQuestion());
                }
            }
            paper.setPaperQuestions(pqs);
            paper.setQuestions(questions);
        }
        return paper;
    }

    @Override
    public List<Paper> search(String name, Long courseId, Long creatorId, Boolean published) {
        return paperMapper.search(name, courseId, creatorId, published);
    }

    @Override
    public List<Paper> findPublished() {
        return paperMapper.findPublished();
    }

    @Override
    public List<Paper> findByCreatorId(Long creatorId) {
        return paperMapper.findByCreatorId(creatorId);
    }

    @Override
    @Transactional
    public boolean save(Paper paper, List<PaperQuestion> questions) {
        if (paperMapper.insert(paper) > 0) {
            if (questions != null) {
                for (int i = 0; i < questions.size(); i++) {
                    PaperQuestion pq = questions.get(i);
                    pq.setPaperId(paper.getId());
                    pq.setSortOrder(i + 1);
                    paperQuestionMapper.insert(pq);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Paper paper, List<PaperQuestion> questions) {
        if (paperMapper.update(paper) > 0) {
            paperQuestionMapper.deleteByPaperId(paper.getId());
            if (questions != null) {
                for (int i = 0; i < questions.size(); i++) {
                    PaperQuestion pq = questions.get(i);
                    pq.setPaperId(paper.getId());
                    pq.setSortOrder(i + 1);
                    paperQuestionMapper.insert(pq);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        paperQuestionMapper.deleteByPaperId(id);
        return paperMapper.deleteById(id) > 0;
    }

    @Override
    public boolean publish(Long id, boolean published) {
        return paperMapper.updatePublished(id, published) > 0;
    }
}
