package service.student;

import Model.Student;
import base.service.BaseService;

public interface StudentService extends BaseService<Student,Long> {
    Boolean isExistsByUsername(String username);
    Student authentication(String username,String password);

}
