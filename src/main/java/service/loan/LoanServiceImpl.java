package service.loan;

import Model.Card;
import Model.Loan;
import Model.Student;
import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.card.CardRepository;
import repository.loan.LoanRepository;

public class LoanServiceImpl extends BaseServiceImpl<Loan,Long, LoanRepository>implements LoanService {

    private final SessionFactory sessionFactory;
    public LoanServiceImpl(LoanRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Boolean isExistEduLoanInTerm(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.isExistEduLoanInTerm(student);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Boolean.TRUE;
        }
    }

    @Override
    public Boolean isExistTuitionLoanInTerm(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.isExistTuitionLoanInTerm(student);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Boolean.TRUE;
        }
    }

    @Override
    public Boolean isExistHousingLoanInTerm(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.isExistHousingLoanInTerm(student);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Boolean.TRUE;
        }
    }
    @Override
    public Boolean wifeHousingLoanCheck(String wifeNationalCode) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.wifeHousingLoanCheck(wifeNationalCode);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Boolean.TRUE;
        }
    }
}
