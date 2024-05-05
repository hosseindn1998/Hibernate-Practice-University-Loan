package base.service;

import base.entity.BaseEntity;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    T saveOrUpdate(T entity);
//    boolean validate(T entity);

    T findById(ID id);


    void delete(T t);
}
