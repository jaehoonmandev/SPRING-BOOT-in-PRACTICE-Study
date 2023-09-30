package com.example.springbootinpracticestudy.dto;


//Author과 Course 테이블에서 필요한 정보만 추출하기 위한 DTO 정의
public class AuthorCourseDto {

    private long id;
    private String authorName;
    private String courseName;
    private String description;

    public AuthorCourseDto(long id, String authorName, String courseName, String description) {
        this.id = id;
        this.authorName = authorName;
        this.courseName = courseName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}