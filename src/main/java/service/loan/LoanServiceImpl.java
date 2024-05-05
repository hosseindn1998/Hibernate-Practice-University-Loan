package service.loan;

import Model.Card;
import Model.Loan;
import base.service.BaseServiceImpl;
import org.hibernate.SessionFactory;
import repository.card.CardRepository;
import repository.loan.LoanRepository;

public class LoanServiceImpl extends BaseServiceImpl<Loan,Long, LoanRepository>implements LoanService {

    private final SessionFactory sessionFactory;
    public LoanServiceImpl(LoanRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }



}
