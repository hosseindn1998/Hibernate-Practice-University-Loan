package repository.loan;

import Model.Card;
import Model.Loan;
import Model.Student;
import base.repository.BaseRepository;

public interface LoanRepository extends BaseRepository<Loan,Long> {
    Boolean isExistEduLoanInTerm(Student student);

}
