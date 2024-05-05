package repository.installment;

import Model.Card;
import Model.Installment;
import base.repository.BaseRepositoryImpl;
import org.hibernate.SessionFactory;

public class InstallmentRepositoryImpl extends BaseRepositoryImpl<Installment,Long> implements InstallmentRepository {


    public InstallmentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Installment> getEntityClass() {
        return Installment.class;
    }




}
