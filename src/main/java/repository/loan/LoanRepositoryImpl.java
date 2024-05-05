package repository.loan;

import Model.Loan;
import Model.Student;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class LoanRepositoryImpl extends BaseRepositoryImpl<Loan,Long> implements LoanRepository {

    @Override
    public Boolean isExistEduLoanInTerm(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.username = :username and l.loanType=0 and l.studentStage=studentStage" , Loan.class);
        query.setParameter("username", student.getUsername());
        query.setParameter("studentStage", student.getEduStage());
        return query.list()!= null;
    }

    public LoanRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Loan> getEntityClass() {
        return Loan.class;
    }


}
