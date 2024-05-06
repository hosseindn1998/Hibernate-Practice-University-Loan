package Model;

import base.entity.BaseEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class MorInfoForHousingLoan extends BaseEntity<Long> {
    @OneToOne
    Loan loan;
    @Pattern(regexp = "^[0-9]{10}$")
    String wifeNationalCode;
    String wifeFirstName;
    String wifeLastName;
    String province;
    String city;
    String street;
    String subStreet;
    String alley;
    String plaque;
    String rentAgreeNum;

}
