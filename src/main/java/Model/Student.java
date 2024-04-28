package Model;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Table(name = "student")
public class Student extends BaseEntity {
    String firstName;
    String lastName;
    String fatherName;
    String motherName;
    String birthCertificateNumber;
    String nationalCode;
    Date birthDate;
    String studentCode;
    String universityName;
    String universityType;
    Integer appliedYear;
    String eduStage;
    String appliedType;
    String username;
    String password;


}
