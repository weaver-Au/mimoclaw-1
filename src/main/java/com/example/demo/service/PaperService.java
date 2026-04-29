package com.example.demo.service;

import com.example.demo.entity.Paper;
import com.example.demo.entity.PaperQuestion;
import java.util.List;

public interface PaperService {
    Paper findById(Long id);
    Paper findByIdWithQuestions(Long id);
    List<Paper> search(String name, Long courseId, Long creatorId, Boolean published);
    List<Paper> findPublished();
    List<Paper> findByCreatorId(Long creatorId);
    boolean save(Paper paper, List<PaperQuestion> questions);
    boolean update(Paper paper, List<PaperQuestion> questions);
    boolean delete(Long id);
    boolean publish(Long id, boolean published);
}
