package service.installment;

import Model.Card;
import Model.Installment;
import base.service.BaseServiceImpl;
import org.hibernate.SessionFactory;
import repository.card.CardRepository;
import repository.installment.InstallmentRepository;

public class InstallmentServiceImpl extends BaseServiceImpl<Installment,Long, InstallmentRepository>implements InstallmentService {

    private final SessionFactory sessionFactory;
    public InstallmentServiceImpl(InstallmentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }



}
