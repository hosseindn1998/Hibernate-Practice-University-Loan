package service.loan;

import Model.Card;
import Model.Loan;
import Model.Student;
import base.service.BaseService;

public interface LoanService extends BaseService<Loan,Long> {
    Boolean isExistEduLoanInTerm(Student student);
    Boolean isExistTuitionLoanInTerm(Student student);
    Boolean isExistHousingLoanInTerm(Student student);
    Boolean wifeHousingLoanCheck(String wifeNationalCode);

}
