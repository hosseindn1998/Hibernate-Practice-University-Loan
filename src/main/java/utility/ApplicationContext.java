package utility;

import Model.*;
import Repository.AssistantProfessor.AssistantProfessorRepositoryImpl;
import Repository.Career.CareerRepositoryImpl;
import Repository.CareerEnrollment.CareerEnrollmentRepositoryImpl;
import Repository.CareerLevel.CareerLevelRepositoryImpl;
import Repository.CareerStatus.CareerStatusRepositoryImpl;
import Repository.Course.CourseRepositoryImpl;
import Repository.CourseDependency.CourseDependencyRepositoryImpl;
import Repository.CourseEnrollment.CourseEnrollmentRepositoryImpl;
import Repository.CourseEnrollmentEvaluation.CourseEnrollmentEvaluationRepositoryImpl;
import Repository.CourseOccurence.CourseOccurenceRepositoryImpl;
import Repository.Department.DepartmentRepositoryImpl;
import Repository.Employee.EmployeeRepositoryImpl;
import Repository.EvaluationType.EvaluationTypeRepositoryImpl;
import Repository.Professor.ProfessorRepositoryImpl;
import Repository.ProfessorCourse.ProfessorCourseRepositoryImpl;
import Repository.Schedule.ScheduleRepositoryImpl;
import Repository.room.RoomRepositoryImpl;
import Repository.student.StudentRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.SessionFactory;
import service.AssistantProfessor.AssistantProfessorServiceImpl;
import service.Career.CareerServiceImpl;
import service.CareerEnrollment.CareerEnrollmentServiceImpl;
import service.CareerLevel.CareerLevelServiceImpl;
import service.CareerStatus.CareerStatusServiceImpl;
import service.Course.CourseServiceImpl;
import service.CourseDependency.CourseDependencyServiceImpl;
import service.CourseEnrollment.CourseEnrollmentServiceImpl;
import service.CourseEnrollmentEvaluation.CourseEnrollmentEvaluationServiceImpl;
import service.CourseOccurence.CourseOccurenceServiceImpl;
import service.Department.DepartmentServiceImpl;
import service.Employee.EmployeeServiceImpl;
import service.EvaluationType.EvaluationTypeServiceImpl;
import service.Professor.ProfessorServiceImpl;
import service.ProfessorCourse.ProfessorCourseServiceImpl;
import service.Schedule.ScheduleServiceImpl;
import service.room.RoomServiceImpl;
import service.student.StudentServiceImpl;

public class ApplicationContext {
//    static final SessionFactory SESSION_FACTORY;
//////    static final Session SESSION;
//    private static final StudentRepositoryImpl STUDENT_REPOSITORY;
//    private static final StudentServiceImpl STUDENT_SERVICE;
//    private static final ScheduleRepositoryImpl SCHEDULE_REPOSITORY;
//    private static final ScheduleServiceImpl SCHEDULE_SERVICE;
//    private static final RoomRepositoryImpl ROOM_REPOSITORY;
//    private static final RoomServiceImpl ROOM_SERVICE;
//    private static final ProfessorRepositoryImpl PROFESSOR_REPOSITORY;
//    private static final ProfessorServiceImpl PROFESSOR_SERVICE;
//    private static final EvaluationTypeRepositoryImpl EVALUATION_TYPE_REPOSITORY;
//    private static final EvaluationTypeServiceImpl EVALUATION_TYPE_SERVICE;
//    private static final CourseOccurenceRepositoryImpl COURSE_OCCURRENCE_REPOSITORY;
//    private static final CourseOccurenceServiceImpl COURSE_OCCURRENCE_SERVICE;
//    private static final CourseEnrollmentEvaluationRepositoryImpl COURSE_ENROLLMENT_EVALUATION_REPOSITORY;
//    private static final CourseEnrollmentEvaluationServiceImpl COURSE_ENROLLMENT_EVALUATION_SERVICE;
//    private static final CourseEnrollmentRepositoryImpl COURSE_ENROLLMENT_REPOSITORY;
//    private static final CourseEnrollmentServiceImpl COURSE_ENROLLMENT_SERVICE;
//    private static final CourseRepositoryImpl COURSE_REPOSITORY;
//    private static final CourseServiceImpl COURSE_SERVICE;
//    private static final CareerStatusRepositoryImpl CAREER_STATUS_REPOSITORY;
//    private static final CareerStatusServiceImpl CAREER_STATUS_SERVICE;
//    private static final CareerLevelRepositoryImpl CAREER_LEVEL_REPOSITORY;
//    private static final CareerLevelServiceImpl CAREER_LEVEL_SERVICE;
//    private static final CareerEnrollmentRepositoryImpl CAREER_ENROLLMENT_REPOSITORY;
//    private static final CareerEnrollmentServiceImpl CAREER_ENROLLMENT_SERVICE;
//    private static final CareerRepositoryImpl CAREER_REPOSITORY;
//    private static final CareerServiceImpl CAREER_SERVICE;
//    private static final EmployeeRepositoryImpl EMPLOYEE_REPOSITORY;
//    private static final EmployeeServiceImpl EMPLOYEE_SERVICE;
//    private static final ProfessorCourseRepositoryImpl PROFESSOR_COURSE_REPOSITORY;
//    private static final ProfessorCourseServiceImpl PROFESSOR_COURSE_SERVICE;
//    private static final DepartmentRepositoryImpl DEPARTMENT_REPOSITORY;
//    private static final DepartmentServiceImpl DEPARTMENT_SERVICE;
//    private static final CourseDependencyRepositoryImpl COURSE_DEPENDENCY_REPOSITORY;
//    private static final CourseDependencyServiceImpl COURSE_DEPENDENCY_SERVICE;
//    private static final AssistantProfessorRepositoryImpl ASSISTANT_PROFESSOR_REPOSITORY;
//    private static final AssistantProfessorServiceImpl ASSISTANT_PROFESSOR_SERVICE;
//
//
//    static {
//        SESSION_FACTORY = SessionFactorySingleton.getInstance();
////        SESSION = SESSION_FACTORY.openSession();
//        STUDENT_REPOSITORY = new StudentRepositoryImpl(SESSION_FACTORY);
//        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY,SESSION_FACTORY);
//        SCHEDULE_REPOSITORY = new ScheduleRepositoryImpl(SESSION_FACTORY);
//        SCHEDULE_SERVICE = new ScheduleServiceImpl(SCHEDULE_REPOSITORY,SESSION_FACTORY);
//        ROOM_REPOSITORY = new RoomRepositoryImpl(SESSION_FACTORY);
//        ROOM_SERVICE = new RoomServiceImpl(ROOM_REPOSITORY,SESSION_FACTORY);
//        PROFESSOR_REPOSITORY = new ProfessorRepositoryImpl(SESSION_FACTORY);
//        PROFESSOR_SERVICE = new ProfessorServiceImpl(PROFESSOR_REPOSITORY,SESSION_FACTORY);
//        EVALUATION_TYPE_REPOSITORY = new EvaluationTypeRepositoryImpl(SESSION_FACTORY);
//        EVALUATION_TYPE_SERVICE = new EvaluationTypeServiceImpl(EVALUATION_TYPE_REPOSITORY,SESSION_FACTORY);
//        COURSE_OCCURRENCE_REPOSITORY = new CourseOccurenceRepositoryImpl(SESSION_FACTORY);
//        COURSE_OCCURRENCE_SERVICE = new CourseOccurenceServiceImpl(COURSE_OCCURRENCE_REPOSITORY,SESSION_FACTORY);
//        COURSE_ENROLLMENT_EVALUATION_REPOSITORY = new CourseEnrollmentEvaluationRepositoryImpl(SESSION_FACTORY);
//        COURSE_ENROLLMENT_EVALUATION_SERVICE = new CourseEnrollmentEvaluationServiceImpl(COURSE_ENROLLMENT_EVALUATION_REPOSITORY,SESSION_FACTORY);
//        COURSE_ENROLLMENT_REPOSITORY = new CourseEnrollmentRepositoryImpl(SESSION_FACTORY);
//        COURSE_ENROLLMENT_SERVICE = new CourseEnrollmentServiceImpl(COURSE_ENROLLMENT_REPOSITORY,SESSION_FACTORY);
//        COURSE_REPOSITORY = new CourseRepositoryImpl(SESSION_FACTORY);
//        COURSE_SERVICE = new CourseServiceImpl(COURSE_REPOSITORY,SESSION_FACTORY);
//        CAREER_STATUS_REPOSITORY = new CareerStatusRepositoryImpl(SESSION_FACTORY);
//        CAREER_STATUS_SERVICE = new CareerStatusServiceImpl(CAREER_STATUS_REPOSITORY,SESSION_FACTORY);
//        CAREER_LEVEL_REPOSITORY = new CareerLevelRepositoryImpl(SESSION_FACTORY);
//        CAREER_LEVEL_SERVICE = new CareerLevelServiceImpl(CAREER_LEVEL_REPOSITORY,SESSION_FACTORY);
//        CAREER_ENROLLMENT_REPOSITORY = new CareerEnrollmentRepositoryImpl(SESSION_FACTORY);
//        CAREER_ENROLLMENT_SERVICE = new CareerEnrollmentServiceImpl(CAREER_ENROLLMENT_REPOSITORY,SESSION_FACTORY);
//        CAREER_REPOSITORY = new CareerRepositoryImpl(SESSION_FACTORY);
//        CAREER_SERVICE = new CareerServiceImpl(CAREER_REPOSITORY,SESSION_FACTORY);
//        EMPLOYEE_REPOSITORY = new EmployeeRepositoryImpl(SESSION_FACTORY);
//        EMPLOYEE_SERVICE = new EmployeeServiceImpl(EMPLOYEE_REPOSITORY,SESSION_FACTORY);
//        PROFESSOR_COURSE_REPOSITORY = new ProfessorCourseRepositoryImpl(SESSION_FACTORY);
//        PROFESSOR_COURSE_SERVICE = new ProfessorCourseServiceImpl(PROFESSOR_COURSE_REPOSITORY,SESSION_FACTORY);
//        DEPARTMENT_REPOSITORY = new DepartmentRepositoryImpl(SESSION_FACTORY);
//        DEPARTMENT_SERVICE = new DepartmentServiceImpl(DEPARTMENT_REPOSITORY,SESSION_FACTORY);
//        COURSE_DEPENDENCY_REPOSITORY = new CourseDependencyRepositoryImpl(SESSION_FACTORY);
//        COURSE_DEPENDENCY_SERVICE = new CourseDependencyServiceImpl(COURSE_DEPENDENCY_REPOSITORY,SESSION_FACTORY);
//        ASSISTANT_PROFESSOR_REPOSITORY = new AssistantProfessorRepositoryImpl(SESSION_FACTORY);
//        ASSISTANT_PROFESSOR_SERVICE = new AssistantProfessorServiceImpl(ASSISTANT_PROFESSOR_REPOSITORY,SESSION_FACTORY);
//    }
//
//    public static StudentServiceImpl getStudentService() {
//        return STUDENT_SERVICE;
//    }
//
//    public static ScheduleServiceImpl getScheduleService() {
//        return SCHEDULE_SERVICE;
//    }
//
//    public static RoomServiceImpl getRoomService() {
//        return ROOM_SERVICE;
//    }
//
//    public static ProfessorServiceImpl getProfessorService() {
//        return PROFESSOR_SERVICE;
//    }
//
//    public static EvaluationTypeServiceImpl getEvaluationTypeService() {
//        return EVALUATION_TYPE_SERVICE;
//    }
//
//    public static CourseOccurenceServiceImpl getCourseOccurrenceService() {
//        return COURSE_OCCURRENCE_SERVICE;
//    }
//
//    public static CourseEnrollmentEvaluationServiceImpl getCourseEnrollmentEvaluationService() {
//        return COURSE_ENROLLMENT_EVALUATION_SERVICE;
//    }
//
//    public static CourseEnrollmentServiceImpl getCourseEnrollmentService() {
//        return COURSE_ENROLLMENT_SERVICE;
//    }
//
//    public static CourseServiceImpl getCourseService() {
//        return COURSE_SERVICE;
//    }
//
//    public static CareerStatusServiceImpl getCareerStatusService() {
//        return CAREER_STATUS_SERVICE;
//    }
//
//    public static CareerLevelServiceImpl getCareerLevelService() {
//        return CAREER_LEVEL_SERVICE;
//    }
//
//    public static CareerEnrollmentServiceImpl getCareerEnrollmentService() {
//        return CAREER_ENROLLMENT_SERVICE;
//    }
//
//    public static CareerServiceImpl getCareerService() {
//        return CAREER_SERVICE;
//    }
//
//    public static EmployeeServiceImpl getEmployeeService() {
//        return EMPLOYEE_SERVICE;
//    }
//
//    public static ProfessorCourseServiceImpl getProfessorCourseService() {
//        return PROFESSOR_COURSE_SERVICE;
//    }
//
//    public static DepartmentServiceImpl getDepartmentService() {
//        return DEPARTMENT_SERVICE;
//    }
//
//    public static CourseDependencyServiceImpl getCourseDependencyService() {
//        return COURSE_DEPENDENCY_SERVICE;
//    }
//
//    public static AssistantProfessorServiceImpl getAssistantProfessorService() {
//        return ASSISTANT_PROFESSOR_SERVICE;
//    }
}
