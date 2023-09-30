package com.example.springbootinpracticestudy.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "AUTHOR")
@Table(name="AUTHORS")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String bio;

    /**
     * @ManyToMany로 다대다 관계 형성
     * 관계를 소유하는 소유자와 참조되기만하는 비소유자 관계가있다.
     * 일대다 관계를 형성할 때는 다 쪽이 소유하는게 효율적이다.
    */
    @ManyToMany

    /**
     * @JoinTable로 관계를 형성해준다. 현재는 Authors가 소유자이기 떄문에 JoinTable로 지정.
     * 비소유자는 @ManyToMany(mappedBy = "")로 소유자 쪽에 비소유자를 참조할 수 있도록 지정된 필드의 이름을 명시한다.
     * authors_courses 조인테이블로 정의, 지정하지 않을 시 소유자_비소유자의 테이블이 생성됨.
     */
    @JoinTable(name = "authors_courses",
            joinColumns = {@JoinColumn(name="author_id", referencedColumnName = "id", nullable = false, updatable = false)},//소유자
            inverseJoinColumns = {@JoinColumn(name="course_id", referencedColumnName = "id", nullable = false, updatable = false)//비소유자
    }
    )
    private Set<Course> courses = new HashSet<>();

    public Author() {}

    public Author(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
