package Repository.student;

import Model.Student;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Student,Long> implements StudentRepository {


    public StudentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    public Boolean isExistsByUsername(String username) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Student> query = session.createQuery("FROM Student s WHERE s.username = :username", Student.class);
        query.setParameter("username", username);
        return query.list()!= null;
    }

    @Override
    public Student authentication(String username, String password) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        return session.createQuery("FROM Student s WHERE s.username= :username AND s.password = :password", Student.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
    }


}
