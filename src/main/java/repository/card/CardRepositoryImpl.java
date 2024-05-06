package repository.card;

import Model.Card;
import Model.Student;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CardRepositoryImpl extends BaseRepositoryImpl<Card,Long> implements CardRepository {


    public CardRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Boolean isExist(Card card) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();
        Query<Card> query = session.createQuery("FROM Card c WHERE  c.fourNumber1 = :fourNumber1 and c.fourNumber2 = :fourNumber2 " +
                "and c.fourNumber3 = :fourNumber3 and c.fourNumber4 = :fourNumber4", Card.class);
        query.setParameter("fourNumber1", card.getFourNumber1());
        query.setParameter("fourNumber2", card.getFourNumber2());
        query.setParameter("fourNumber3", card.getFourNumber3());
        query.setParameter("fourNumber4", card.getFourNumber4());
        return query.list()!= null;
    }

    @Override
    public Class<Card> getEntityClass() {
        return Card.class;
    }




}
