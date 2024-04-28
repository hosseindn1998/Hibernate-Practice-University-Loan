package Service.student;

import Model.Student;
import Repository.student.StudentRepository;
import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StudentServiceImpl extends BaseServiceImpl<Student,Long,StudentRepository>implements StudentService {

    private final SessionFactory sessionFactory;
    public StudentServiceImpl(StudentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Boolean  isExistsByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.isExistsByUsername(username);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            throw new NotFoundException(String.format("entity with %s not found", username));
        }

    }

    @Override
    public Student authentication(String username, String password) {
        Transaction transaction=null;

        try(Session session=sessionFactory.getCurrentSession()) {
                transaction=session.beginTransaction();
           Student student = repository.authentication(username, password);
                transaction.commit();
                return student ;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return null;
            }

    }

}
