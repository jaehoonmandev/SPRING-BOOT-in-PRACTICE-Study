package com.manning.sbip.ch10.model

import jakarta.persistence.*; // JPA 애너테이션을 사용할 수 있도록 임포트
import jakarta.validation.constraints.*; // 밸리데이션 애너테이션 임포트

@Entity
@Table(name = "Courses")
class Course(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    //kotlin은 var 키워드를 사용해서 변수를 선언.
    //변수의 타입은 변수명 뒤에 위치하며, 타입 뒤에 ?가 붙어 있으면 널값을 가질 수 있는 타입을 의미.
    //즉, 기본적으로는 널 값을 가질 수 없으며, 가지기위해서는 명시적으로 '?'을 붙인다.
    var id: Long? = 0,

    @Column(name = "NAME")
    @NotEmpty(message = "Course name field can't be empty")
    var name: String? = "",

    @Column(name = "CATEGORY")
    @NotEmpty(message = "Course category field can't be empty")
    var category: String? = "",

    @Column(name = "RATING")
    @Min(value = 1)
    @Max(value = 5)
    var rating : Int? = 0,

    @Column(name = "DESCRIPTION")
    @NotEmpty(message = "Course description field can't be empty")
    var description: String? = ""
)
