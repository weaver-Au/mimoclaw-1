package com.example.demo.mapper;

import com.example.demo.entity.Question;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("SELECT q.*, c.name as course_name FROM questions q LEFT JOIN courses c ON q.course_id = c.id WHERE q.id = #{id}")
    Question findById(Long id);

    @Select("<script>" +
            "SELECT q.*, c.name as course_name FROM questions q LEFT JOIN courses c ON q.course_id = c.id WHERE 1=1 " +
            "<if test='courseId != null'>AND q.course_id = #{courseId}</if>" +
            "<if test='type != null and type != \"\"'>AND q.type = #{type}</if>" +
            "<if test='difficulty != null'>AND q.difficulty = #{difficulty}</if>" +
            "<if test='keyword != null and keyword != \"\"'>AND q.content LIKE CONCAT('%',#{keyword},'%')</if>" +
            " ORDER BY q.id DESC" +
            "</script>")
    List<Question> search(@Param("courseId") Long courseId, @Param("type") String type,
                          @Param("difficulty") Integer difficulty, @Param("keyword") String keyword);

    @Select("SELECT q.*, c.name as course_name FROM questions q LEFT JOIN courses c ON q.course_id = c.id WHERE q.creator_id = #{creatorId} ORDER BY q.id DESC")
    List<Question> findByCreatorId(Long creatorId);

    @Select("SELECT q.*, c.name as course_name FROM questions q LEFT JOIN courses c ON q.course_id = c.id ORDER BY q.id DESC")
    List<Question> findAll();

    @Insert("INSERT INTO questions(course_id, type, content, options, answer, analysis, difficulty, creator_id) " +
            "VALUES(#{courseId}, #{type}, #{content}, #{options}, #{answer}, #{analysis}, #{difficulty}, #{creatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Question question);

    @Update("UPDATE questions SET course_id=#{courseId}, type=#{type}, content=#{content}, options=#{options}, " +
            "answer=#{answer}, analysis=#{analysis}, difficulty=#{difficulty} WHERE id=#{id}")
    int update(Question question);

    @Delete("DELETE FROM questions WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT q.*, c.name as course_name FROM questions q LEFT JOIN courses c ON q.course_id = c.id WHERE q.course_id = #{courseId} ORDER BY q.id DESC")
    List<Question> findByCourseId(Long courseId);
}
