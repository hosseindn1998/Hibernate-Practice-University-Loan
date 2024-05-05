package Model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class Card  extends BaseEntity<Long> {
    @Pattern(regexp = "^[0-9]{4}$")
    String fourNumber1;
    @Pattern(regexp = "^[0-9]{4}$")
    String fourNumber2;
    @Pattern(regexp = "^[0-9]{4}$")
    String fourNumber3;
    @Pattern(regexp = "^[0-9]{4}$")
    String fourNumber4;
    @Pattern(regexp = "^[0-9]{3,4}$")
    String cvv2;
    @Pattern(regexp = "^[0-9]{2}$")
    String expireYear;
    @Pattern(regexp = "^[0-9]{2}$")
    String expireMonth;
    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    @ElementCollection
    List<Loan>loan=new ArrayList<>();
}
