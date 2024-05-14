package Model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
@Builder
@ToString
@Table(name = "student")
public class Student extends BaseEntity<Long> {
    String firstName;
    String lastName;
    String fatherName;
    String motherName;
    Boolean isMarried;
    Boolean liveInDorm;
    String birthCertificateNumber;
    @Pattern(regexp = "^[0-9]{10}$")
    String nationalCode;
    LocalDate birthDate;
    String studentCode;
    String universityName;
    @Enumerated(EnumType.ORDINAL)
    UniversityTypes universityType;
    Integer appliedYear;
    AppliedTerm appliedTerm;
    Integer currentTerm;
    LocalDate expireDate;
    @Enumerated(EnumType.ORDINAL)
    EduStages eduStage;
    @Enumerated(EnumType.ORDINAL)
    AppliedTypes appliedType;
    @Pattern(regexp = "^[0-9]{10}$")
    String username;
    @Pattern(regexp = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$")
    String password;
    @ElementCollection
    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.MERGE)
    List<Loan> loan;


}
