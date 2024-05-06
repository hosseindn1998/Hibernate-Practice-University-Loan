package service.card;

import Model.Card;
import Model.Student;
import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.card.CardRepository;
import repository.student.StudentRepository;

public class CardServiceImpl extends BaseServiceImpl<Card,Long, CardRepository>implements CardService {

    private final SessionFactory sessionFactory;
    public CardServiceImpl(CardRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
        this.sessionFactory=sessionFactory;
    }
    @Override
    public Boolean  isExist(Card card) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Boolean result=repository.isExist(card);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            throw new NotFoundException(String.format("entity %s not found", card));
        }

    }



}
