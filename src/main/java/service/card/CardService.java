package service.card;

import Model.Card;
import Model.Student;
import base.service.BaseService;

public interface CardService extends BaseService<Card,Long> {

    Boolean isExist(Card card);
}
