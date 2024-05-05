package Model;

import base.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class Installment  extends BaseEntity<Long> {
    Integer paymentStageNum;
    LocalDate dueDate;
    Integer amount;
    Boolean payedStatus;
    LocalDate payDate;
    @ManyToOne(cascade = CascadeType.MERGE)
    Loan loan;
}
