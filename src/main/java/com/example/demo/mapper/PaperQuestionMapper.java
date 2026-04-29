package com.example.demo.mapper;

import com.example.demo.entity.PaperQuestion;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PaperQuestionMapper {

    @Select("SELECT pq.*, q.content as question_content, q.type as question_type, q.options as question_options, " +
            "q.answer as question_answer, q.analysis as question_analysis, q.difficulty as question_difficulty " +
            "FROM paper_questions pq LEFT JOIN questions q ON pq.question_id = q.id WHERE pq.paper_id = #{paperId} ORDER BY pq.sort_order")
    @Results({
            @Result(property = "question.id", column = "question_id"),
            @Result(property = "question.content", column = "question_content"),
            @Result(property = "question.type", column = "question_type"),
            @Result(property = "question.options", column = "question_options"),
            @Result(property = "question.answer", column = "question_answer"),
            @Result(property = "question.analysis", column = "question_analysis"),
            @Result(property = "question.difficulty", column = "question_difficulty")
    })
    List<PaperQuestion> findByPaperId(Long paperId);

    @Insert("INSERT INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(#{paperId}, #{questionId}, #{sortOrder}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PaperQuestion pq);

    @Delete("DELETE FROM paper_questions WHERE paper_id = #{paperId}")
    int deleteByPaperId(Long paperId);

    @Select("SELECT * FROM paper_questions WHERE paper_id = #{paperId} AND question_id = #{questionId}")
    PaperQuestion findByPaperAndQuestion(@Param("paperId") Long paperId, @Param("questionId") Long questionId);
}
