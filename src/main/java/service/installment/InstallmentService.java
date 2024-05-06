package service.installment;

import Model.Card;
import Model.Installment;
import base.service.BaseService;

import java.util.List;

public interface InstallmentService extends BaseService<Installment,Long> {
    List<Installment> findNotPayedByLoanId(Integer loanId);
    List<Installment> findPayedByLoanId(Integer loanId);

}
