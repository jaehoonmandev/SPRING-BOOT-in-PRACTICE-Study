package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Author;
import com.example.springbootinpracticestudy.dto.AuthorCourseDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    // AuthorCourseDto에 담을 수 있게 스키마 new 정의
    @Query("SELECT new com.example.springbootinpracticestudy.dto.AuthorCourseDto(c.id, a.name, c.name, c.description) from AUTHOR a, COURSE c, AUTHORS_COURSES ac where a.id = ac.authorId and c.id=ac.courseId and ac.authorId=?1")
    Iterable<AuthorCourseDto> getAuthorCourseInfo(long authorId);
}
