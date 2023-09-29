package com.example.springbootinpracticestudy.Repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean // 프록시 객체가 생성되지 않게
public interface BaseRepository<T, ID>  extends Repository<T, ID> {
    // 사용할 메서드만 표시하여 노출할 기능을 제한.
    <S extends T> S save(S entity);

    Iterable<T> findAll();

}
