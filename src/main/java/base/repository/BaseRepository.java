package base.repository;

import base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> {

    T saveOrUpdate(T entity);

    Optional<T> findById(ID id);

    List<T> showAll(Class<T> entityClass);

    void delete(T entity);
}
