package com.example.demo.mapper;

import com.example.demo.entity.Paper;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PaperMapper {

    @Select("SELECT p.*, c.name as course_name, u.real_name as creator_name FROM papers p " +
            "LEFT JOIN courses c ON p.course_id = c.id LEFT JOIN users u ON p.creator_id = u.id WHERE p.id = #{id}")
    Paper findById(Long id);

    @Select("<script>" +
            "SELECT p.*, c.name as course_name, u.real_name as creator_name FROM papers p " +
            "LEFT JOIN courses c ON p.course_id = c.id LEFT JOIN users u ON p.creator_id = u.id WHERE 1=1 " +
            "<if test='name != null and name != \"\"'>AND p.name LIKE CONCAT('%',#{name},'%')</if>" +
            "<if test='courseId != null'>AND p.course_id = #{courseId}</if>" +
            "<if test='creatorId != null'>AND p.creator_id = #{creatorId}</if>" +
            "<if test='published != null'>AND p.published = #{published}</if>" +
            " ORDER BY p.id DESC" +
            "</script>")
    List<Paper> search(@Param("name") String name, @Param("courseId") Long courseId,
                       @Param("creatorId") Long creatorId, @Param("published") Boolean published);

    @Select("SELECT p.*, c.name as course_name, u.real_name as creator_name FROM papers p " +
            "LEFT JOIN courses c ON p.course_id = c.id LEFT JOIN users u ON p.creator_id = u.id " +
            "WHERE p.published = 1 AND (p.start_time IS NULL OR p.start_time <= NOW()) " +
            "AND (p.end_time IS NULL OR p.end_time >= NOW()) ORDER BY p.id DESC")
    List<Paper> findPublished();

    @Select("SELECT p.*, c.name as course_name, u.real_name as creator_name FROM papers p " +
            "LEFT JOIN courses c ON p.course_id = c.id LEFT JOIN users u ON p.creator_id = u.id " +
            "WHERE p.creator_id = #{creatorId} ORDER BY p.id DESC")
    List<Paper> findByCreatorId(Long creatorId);

    @Insert("INSERT INTO papers(name, course_id, creator_id, total_score, duration, start_time, end_time, published) " +
            "VALUES(#{name}, #{courseId}, #{creatorId}, #{totalScore}, #{duration}, #{startTime}, #{endTime}, #{published})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Paper paper);

    @Update("UPDATE papers SET name=#{name}, course_id=#{courseId}, total_score=#{totalScore}, " +
            "duration=#{duration}, start_time=#{startTime}, end_time=#{endTime}, published=#{published} WHERE id=#{id}")
    int update(Paper paper);

    @Update("UPDATE papers SET published=#{published} WHERE id=#{id}")
    int updatePublished(@Param("id") Long id, @Param("published") Boolean published);

    @Delete("DELETE FROM papers WHERE id=#{id}")
    int deleteById(Long id);
}
