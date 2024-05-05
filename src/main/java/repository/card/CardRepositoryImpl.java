package repository.card;

import Model.Card;
import base.repository.BaseRepositoryImpl;
import org.hibernate.SessionFactory;

public class CardRepositoryImpl extends BaseRepositoryImpl<Card,Long> implements CardRepository {


    public CardRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Card> getEntityClass() {
        return Card.class;
    }




}
