package Repository.student;

import Model.Student;
import base.repository.BaseRepository;

public interface StudentRepository extends BaseRepository<Student,Long> {
    Boolean isExistsByUsername(String username);
    Student authentication(String username,String password);
}
