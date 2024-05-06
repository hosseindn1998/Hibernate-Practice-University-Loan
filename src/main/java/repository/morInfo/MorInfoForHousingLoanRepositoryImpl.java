package repository.morInfo;

import Model.Card;
import Model.MorInfoForHousingLoan;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MorInfoForHousingLoanRepositoryImpl extends BaseRepositoryImpl<MorInfoForHousingLoan,Long> implements MorInfoForHousingLoanRepository {


    public MorInfoForHousingLoanRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }



    @Override
    public Class<MorInfoForHousingLoan> getEntityClass() {
        return MorInfoForHousingLoan.class;
    }




}
