package com.example.demo.mapper;

import com.example.demo.entity.PaperQuestion;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PaperQuestionMapper {

    List<PaperQuestion> findByPaperId(Long paperId);

    @Insert("INSERT INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(#{paperId}, #{questionId}, #{sortOrder}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PaperQuestion pq);

    @Delete("DELETE FROM paper_questions WHERE paper_id = #{paperId}")
    int deleteByPaperId(Long paperId);

    @Select("SELECT * FROM paper_questions WHERE paper_id = #{paperId} AND question_id = #{questionId}")
    PaperQuestion findByPaperAndQuestion(@Param("paperId") Long paperId, @Param("questionId") Long questionId);
}
