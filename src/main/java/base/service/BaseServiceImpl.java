package base.service;

import base.entity.BaseEntity;
import base.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import base.repository.BaseRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BaseServiceImpl<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;
    private final SessionFactory sessionFactory;
//    ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
//    Validator validator = validatorFactory.getValidator();
//
//    @Override
//    public boolean validate(T entity) {
//        Set<ConstraintViolation<T>> violations = validator.validate(entity);
//        if (violations.isEmpty()) return true;
//        else {
//            System.out.println("Invalid user data found:");
//            for (ConstraintViolation<T> violation : violations) {
//                System.out.println(violation.getMessage());
//            }
//            return false;
//        }
//    }

    @Override
    public T saveOrUpdate(T entity) {
//        if(!validate(entity))
//            return null;
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            T t = repository.saveOrUpdate(entity);
            transaction.commit();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            assert transaction != null;
            transaction.rollback();
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            T foundEntity = repository.findById(id).get();
            session.getTransaction().commit();
            return foundEntity;
        } catch (Exception e) {
            throw new NotFoundException(String.format("entity with %s not found", id));
        }
    }

    @Override
    public void delete(T t) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            repository.delete(t);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
        }
    }

    public List<T> showAll(Class<T> entityClass) {

        List<T> foundEntities = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            foundEntities = repository.showAll(entityClass);
            session.getTransaction().commit();
            return foundEntities;
        } catch (Exception e) {
            if (foundEntities.isEmpty())
                throw new NotFoundException("not found anything");
            else {
                throw new NotFoundException("error in progress founding ...");
            }
        }
    }
}
