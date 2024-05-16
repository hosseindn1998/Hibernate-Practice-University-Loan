package repository.installment;


import Model.Installment;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class InstallmentRepositoryImpl extends BaseRepositoryImpl<Installment,Long> implements InstallmentRepository {


    public InstallmentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Installment> findNotPayedByLoanId(Integer loanId) {
            Session session = SessionFactorySingleton.getInstance().getCurrentSession();
            Query<Installment> query = session.createQuery("FROM Installment I WHERE I.loan.id = :id AND I.payedStatus=false " , Installment.class);
            query.setParameter("id", loanId);
            return query.getResultList();
    }
    @Override
    public Installment paymentByPaymentLevel(Integer loanId,Integer paymentLevel) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Installment> query = session.createQuery("FROM Installment I WHERE I.loan.id = :id AND I.payedStatus=false And I.paymentStageNum= :paymentLevel" , Installment.class);
        query.setParameter("id", loanId);
        query.setParameter("paymentLevel", paymentLevel);
        return query.getSingleResult();
    }
    @Override
    public List<Installment> findPayedByLoanId(Integer loanId) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Installment> query = session.createQuery("FROM Installment I WHERE I.loan.id = :id AND I.payedStatus=true " , Installment.class);
        query.setParameter("id", loanId);
        return query.getResultList();
    }

    @Override
    public Class<Installment> getEntityClass() {
        return Installment.class;
    }




}
