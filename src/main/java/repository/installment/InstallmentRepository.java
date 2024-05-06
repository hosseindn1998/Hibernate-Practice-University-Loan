package repository.installment;

import Model.Card;
import Model.Installment;
import base.repository.BaseRepository;

import java.util.List;

public interface InstallmentRepository extends BaseRepository<Installment, Long> {
    List<Installment> findNotPayedByLoanId(Integer loanId);

    List<Installment> findPayedByLoanId(Integer loanId);
}
