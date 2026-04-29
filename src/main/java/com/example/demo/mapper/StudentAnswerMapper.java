package com.example.demo.mapper;

import com.example.demo.entity.StudentAnswer;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentAnswerMapper {

    @Select("SELECT sa.*, u.real_name as student_name, q.content as question_content, " +
            "q.answer as correct_answer, q.type as question_type, pq.score as question_score " +
            "FROM student_answers sa " +
            "LEFT JOIN users u ON sa.student_id = u.id " +
            "LEFT JOIN questions q ON sa.question_id = q.id " +
            "LEFT JOIN paper_questions pq ON pq.paper_id = sa.paper_id AND pq.question_id = sa.question_id " +
            "WHERE sa.student_id = #{studentId} AND sa.paper_id = #{paperId}")
    List<StudentAnswer> findByStudentAndPaper(@Param("studentId") Long studentId, @Param("paperId") Long paperId);

    @Select("SELECT DISTINCT sa.student_id, u.real_name as student_name, " +
            "SUM(sa.score) as total_score " +
            "FROM student_answers sa " +
            "LEFT JOIN users u ON sa.student_id = u.id " +
            "WHERE sa.paper_id = #{paperId} " +
            "GROUP BY sa.student_id, u.real_name")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "score", column = "total_score")
    })
    List<StudentAnswer> findStudentScoresByPaper(Long paperId);

    @Select("SELECT sa.*, u.real_name as student_name, q.content as question_content, " +
            "q.answer as correct_answer, q.type as question_type, pq.score as question_score " +
            "FROM student_answers sa " +
            "LEFT JOIN users u ON sa.student_id = u.id " +
            "LEFT JOIN questions q ON sa.question_id = q.id " +
            "LEFT JOIN paper_questions pq ON pq.paper_id = sa.paper_id AND pq.question_id = sa.question_id " +
            "WHERE sa.paper_id = #{paperId} ORDER BY sa.student_id, sa.question_id")
    List<StudentAnswer> findByPaperId(Long paperId);

    @Select("SELECT DISTINCT sa.paper_id, p.name as paper_name, p.course_id, " +
            "SUM(sa.score) as total_score " +
            "FROM student_answers sa " +
            "LEFT JOIN papers p ON sa.paper_id = p.id " +
            "WHERE sa.student_id = #{studentId} " +
            "GROUP BY sa.paper_id, p.name, p.course_id")
    @Results({
            @Result(property = "paperId", column = "paper_id"),
            @Result(property = "paperName", column = "paper_name"),
            @Result(property = "score", column = "total_score")
    })
    List<StudentAnswer> findPapersByStudent(Long studentId);

    @Insert("INSERT INTO student_answers(student_id, paper_id, question_id, answer, is_correct, score, graded) " +
            "VALUES(#{studentId}, #{paperId}, #{questionId}, #{answer}, #{isCorrect}, #{score}, #{graded})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StudentAnswer sa);

    @Delete("DELETE FROM student_answers WHERE student_id = #{studentId} AND paper_id = #{paperId}")
    int deleteByStudentAndPaper(@Param("studentId") Long studentId, @Param("paperId") Long paperId);

    @Select("SELECT COUNT(*) FROM student_answers WHERE student_id = #{studentId} AND paper_id = #{paperId}")
    int countByStudentAndPaper(@Param("studentId") Long studentId, @Param("paperId") Long paperId);
}
