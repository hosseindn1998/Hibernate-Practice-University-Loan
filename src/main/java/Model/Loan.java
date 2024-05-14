package Model;


import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Builder
public class Loan extends BaseEntity<Long> {
    @ManyToOne
    Student student;
    @Enumerated(EnumType.ORDINAL)
    EduStages studentStage;
    Integer getLoanTerm;
    @Enumerated(EnumType.ORDINAL)
    LoanTypes loanType;
    @Enumerated(EnumType.ORDINAL)
    City studentCity;
    Integer amount;
    LocalDate getLoanDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "loan",cascade = CascadeType.MERGE)
    List<Installment> installment;
    Boolean isSettlement;
    Integer currentPayLevel;
    @ManyToOne
    Card card;
    @OneToOne
    MorInfoForHousingLoan morInfoForHousingLoan;

}
