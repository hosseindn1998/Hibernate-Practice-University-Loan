package service.installment;

import Model.Installment;
import base.service.BaseServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.installment.InstallmentRepository;

import java.util.List;

public class InstallmentServiceImpl extends BaseServiceImpl<Installment,Long, InstallmentRepository>implements InstallmentService {

    private final SessionFactory sessionFactory;
    public InstallmentServiceImpl(InstallmentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Installment paymentByPaymentLevel(Integer loanId, Integer paymentLevel) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Installment result=repository.paymentByPaymentLevel(loanId,paymentLevel);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Installment> findNotPayedByLoanId(Integer loanId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Installment> result=repository.findNotPayedByLoanId(loanId);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Installment> findPayedByLoanId(Integer loanId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Installment> result=repository.findPayedByLoanId(loanId);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
