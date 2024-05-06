package repository.loan;

import Model.Loan;
import Model.Student;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LoanRepositoryImpl extends BaseRepositoryImpl<Loan,Long> implements LoanRepository {

    @Override
    public Boolean isExistEduLoanInTerm(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.username = :username and l.loanType=0 and l.studentStage=studentStage and l.getLoanTerm=getLoanTerm" , Loan.class);
        query.setParameter("username", student.getUsername());
        query.setParameter("studentStage", student.getEduStage());
        query.setParameter("getLoanTerm", student.getCurrentTerm());
        return query.list()!= null;
    }
    @Override
    public Boolean isExistTuitionLoanInTerm(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.username = :username and l.loanType=1 and l.studentStage=studentStage and l.getLoanTerm=getLoanTerm" , Loan.class);
        query.setParameter("username", student.getUsername());
        query.setParameter("studentStage", student.getEduStage());
        query.setParameter("getLoanTerm", student.getCurrentTerm());
        return query.list()!= null;
    }
    @Override
    public Boolean isExistHousingLoanInTerm(Student student) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.username = :username and l.loanType=2 and l.studentStage=studentStage" , Loan.class);
        query.setParameter("username", student.getUsername());
        query.setParameter("studentStage", student.getEduStage());
        return query.list()!= null;
    }
    @Override
    public Boolean wifeHousingLoanCheck(String wifeNationalCode) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.username = :username and l.loanType=2" , Loan.class);
        query.setParameter("username", wifeNationalCode);
//        query.setParameter("studentStage", student.getEduStage());
        return query.list()!= null;
    }

    @Override
    public List<Loan> findLoansByStudentId(Integer studentId) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Loan> query = session.createQuery("FROM Loan l WHERE l.student.id = :id" , Loan.class);
        query.setParameter("id", studentId);
        return query.list();
    }

    public LoanRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Loan> getEntityClass() {
        return Loan.class;
    }


}
