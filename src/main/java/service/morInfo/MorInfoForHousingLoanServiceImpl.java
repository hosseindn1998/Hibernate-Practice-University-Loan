package service.morInfo;

import Model.Card;
import Model.MorInfoForHousingLoan;
import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.card.CardRepository;
import repository.morInfo.MorInfoForHousingLoanRepository;

public class MorInfoForHousingLoanServiceImpl extends BaseServiceImpl<MorInfoForHousingLoan,Long, MorInfoForHousingLoanRepository>implements MorInfoForHousingLoanService {

    private final SessionFactory sessionFactory;
    public MorInfoForHousingLoanServiceImpl(MorInfoForHousingLoanRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }




}
