package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    //@Query 로 메서드 수행 역할 직접 지정
    // @Query 를 사용하면 네이티브 쿼리를 이용하여 DB의 기능을 좀 더 자유롭게 사용가능하며, JOIN 과 같은 데이터베이스 특화된 기능을 활용할 수 있다.
    @Query("select c from Course c where c.category=?1")
    Iterable<Course> findAllByCategory(String category);

    //인자를 ?1 ?2 가 아닌 @Param으로 지정한 값을 :[value] 로 넘겨주는 Named Parameter
    @Query("select c from Course c where c.category=:category and c.rating >:rating")
    Iterable<Course> findAllByCategoryAndRatingGreaterThan(@Param("category") String category, @Param("rating") int rating);

    //nativeQuery로 JPQL이 아닌 네이티브 쿼리문 사용 가능.
    @Query(value = "select * from COURSE where rating=?1", nativeQuery = true)
    Iterable<Course> findAllByRating(int rating);

    @Modifying // @Query와 같이 조회하는 동작이 아닌 수정한다는 작업을 알려준다.
    @Transactional // 데이터의 변경이 일어나니 트랜잭션을 지정.
    @Query("update Course c set c.rating=:rating where c.name=:name")
    int updateCourseRatingByName(@Param("rating") int rating, @Param("name") String name);
}