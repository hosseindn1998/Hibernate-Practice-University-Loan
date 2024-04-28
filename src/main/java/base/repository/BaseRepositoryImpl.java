package base.repository;

import base.entity.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseRepositoryImpl<T extends BaseEntity<ID>,ID extends Serializable>
        implements BaseRepository<T, ID> {

    private SessionFactory sessionFactory;

    @Override
    public T saveOrUpdate(T entity) {
        Session session = sessionFactory.getCurrentSession();
        if (entity.getId() == null)
            session.persist(entity);
        else
            entity = session.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(getEntityClass(), id));
    }


    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    public List<T> showAll(Class<T> entityClass) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);

        return getSession().createQuery(criteria).getResultList();
    }

    public abstract Class<T> getEntityClass();
}
