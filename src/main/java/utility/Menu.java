package utility;

import Model.*;
import lombok.NoArgsConstructor;
import service.AssistantProfessor.AssistantProfessorServiceImpl;
import service.Career.CareerServiceImpl;
import service.CareerEnrollment.CareerEnrollmentServiceImpl;
import service.CareerLevel.CareerLevelServiceImpl;
import service.CareerStatus.CareerStatusServiceImpl;
import service.Course.CourseServiceImpl;
import service.CourseDependency.CourseDependencyServiceImpl;
import service.CourseEnrollment.CourseEnrollmentServiceImpl;
import service.CourseEnrollmentEvaluation.CourseEnrollmentEvaluationServiceImpl;
import service.CourseOccurence.CourseOccurenceService;
import service.Department.DepartmentService;
import service.Employee.EmployeeService;
import service.EvaluationType.EvaluationTypeService;
import service.Professor.ProfessorService;
import service.ProfessorCourse.ProfessorCourseService;
import service.Schedule.ScheduleService;
import service.room.RoomService;
import service.student.StudentServiceImpl;

import java.util.List;
import java.util.Scanner;

//@NoArgsConstructor
public class Menu {
    private int currentTermYear = 403;
    private int lastTermYear = 0;
    private int currentTermNumber = 2;
    private int lastTermNumber = 0;
    private Long loggedInUserId;
    private String loggedInUsername = null;


    public void calculateLastTerm(int currentTermYear, int currentTermNumber) {
        if (currentTermNumber == 1) {
            lastTermYear = currentTermYear - 1;
            lastTermNumber = 2;
        } else {
            lastTermYear = currentTermYear;
            lastTermNumber = 1;
        }
    }



    public void publicMenu() {

        System.out.println("به سامانه جامع امور دانشجویی(سجاد) خوش آمدید");
        System.out.println("1-ورود کاربر");
        System.out.println("2-ثبت نام کابر");
        System.out.println("0-خروج از برنامه");

        int number = scanner.nextInt();
        scanner.nextLine();

        switch (number) {
            case 1 -> signIn();
//            case 2 -> signUp();
            case 0 -> System.out.println("به امید دیدار مجدد");
            default -> System.out.println("ورودی نامعبر! لطفا عددی بین صفر تا دو وارد کنید ");
        }
    }

    public void signIn() {
        System.out.println("***   منوی ورود کابر   ***");
        String username;
        String password;
        do {
            System.out.println("لطفا نام کاربری خود را وارد کنید :");
            username = scanner.nextLine();
        } while (!Validation.isValidUsername(username));
        System.out.println("لطفا کلمه عبور خود را وارد کنید :");
        password = scanner.nextLine();

        if (studentService.authentication(username, password) != null) {
            loggedInUsername = username;
            loggedInUserId = studentService.authentication(username, password).getId();
            Student student=studentService.authentication(username, password);
            student.setSid(loggedInUserId.intValue());
            studentService.saveOrUpdate(student);
            System.out.println(studentService.authentication(username, password));
            studentMunu();
        } else {
            System.out.println("ورودی نامعبر ! لطفا مجددا عدد خود را وارد کنید");
            signIn();
        }
    }


    public void logOut() {
        loggedInUserId = -1L;
        loggedInUsername = null;
        System.out.println("خروج از حساب کاربری با موفقیت انجام شد");
        publicMenu();
    }

    public void studentServices() {
        System.out.println(" ***   منوی کاربری دانشجو   ***");
        System.out.println("1- ثبت نام برای وام");
        System.out.println("2- بازپرداخت ");
        System.out.println("0- خروج از حساب کاربری");


        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 0 -> publicMenu();
            case 1 -> employeeServiceAddStudent();
            case 2 -> employeeServiceDeleteStudent();


            default -> {
                System.out.println("ورودی نامعبر ! لطفا مجددا عدد خود را وارد کنید");
                studentServices();
            }
        }
    }

    public Student getStudentFromInput() {
        String machedUserName = "";
        String machedEmail = "";
        String machedPassword = "";

        do {
            System.out.println("Enter username :");
            machedUserName = scanner.next();
        } while (!Validation.isValidUsername(machedUserName));

        do {
            System.out.println("Enter email :");
            machedEmail = scanner.next();
        } while (!Validation.isValidEmail(machedEmail));

        do {
            System.out.println("Enter password :");
            machedPassword = scanner.next();
        } while (!Validation.isValidPassword(machedPassword));
        System.out.println("Enter first name :");
        String firstName = scanner.next();
        System.out.println("Enter last name :");
        String lastName = scanner.next();
        System.out.println("Enter national Code :");
        String nationalCode = scanner.next();
        System.out.println("Enter date of birth (for ex 1998-01-01 :");
        String dateOfBirth = scanner.next();
        System.out.println("phone number :");
        String phoneNumber = scanner.next();
        System.out.println("Enter student code :");
        String studentCode = scanner.next();
        Student student = new Student(firstName, lastName, nationalCode, dateOfBirth,
                machedEmail, phoneNumber, machedUserName, machedPassword, studentCode);

        return student;
    }

    public Course getCourseFromInput() {
        System.out.println("Enter course name :");
        String courseName = scanner.next();
        System.out.println("Enter unit :");
        int unit = scanner.nextInt();
        System.out.println("Enter Course code :");
        String courseCode = scanner.next();
        System.out.println("Enter year :");
        int year = scanner.nextInt();
        System.out.println("Enter national Code :");

        Course course = new Course(courseName, unit, courseCode, year);
        return course;
    }

    public Employee getEmployeeFromInput() {

        String machedUserName = "";
        String machedEmail = "";
        String machedPassword = "";

        do {
            System.out.println("Enter username :");
            machedUserName = scanner.next();
        } while (!Validation.isValidUsername(machedUserName));

        do {
            System.out.println("Enter email :");
            machedEmail = scanner.next();
        } while (!Validation.isValidEmail(machedEmail));

        do {
            System.out.println("Enter password :");
            machedPassword = scanner.next();
        } while (!Validation.isValidPassword(machedPassword));
        System.out.println("Enter first name :");
        String firstName = scanner.next();
        System.out.println("Enter last name :");
        String lastName = scanner.next();
        System.out.println("Enter national Code :");
        String nationalCode = scanner.next();
        System.out.println("Enter date of birth (for ex 1998-01-01 :");
        String dateOfBirth = scanner.next();
        System.out.println("phone number :");
        String phoneNumber = scanner.next();
        System.out.println("Enter salary :");
        int salary = scanner.nextInt();
        Employee employee = new Employee(firstName, lastName, nationalCode, dateOfBirth,
                machedEmail, phoneNumber, machedUserName, machedPassword, salary);
        return employee;
    }

    public Professor getProfessorFromInput() {
        String machedUserName = "";
        String machedEmail = "";
        String machedPassword = "";

        do {
            System.out.println("Enter username :");
            machedUserName = scanner.next();
        } while (!Validation.isValidUsername(machedUserName));

        do {
            System.out.println("Enter email :");
            machedEmail = scanner.next();
        } while (!Validation.isValidEmail(machedEmail));

        do {
            System.out.println("Enter password :");
            machedPassword = scanner.next();
        } while (!Validation.isValidPassword(machedPassword));
        System.out.println("Enter first name :");
        String firstName = scanner.next();
        System.out.println("Enter last name :");
        String lastName = scanner.next();
        System.out.println("Enter national Code :");
        String nationalCode = scanner.next();
        System.out.println("Enter date of birth (for ex 1998-01-01 :");
        String dateOfBirth = scanner.next();
        System.out.println("phone number :");
        String phoneNumber = scanner.next();
        System.out.println("Enter true if he@ elmi  Enter 'y' or 'n':");
        String isHeyatElmi = scanner.next();
        scanner.nextLine();
        System.out.println("Enter Base salary :");
        Integer salary = scanner.nextInt();
        System.out.println("Enter Unit per Term :");
        Integer unit = scanner.nextInt();
        System.out.println("Enter Total salary :");
        Integer totalSalary = scanner.nextInt();


        Professor professor = new Professor(firstName, lastName, nationalCode, dateOfBirth,
                machedEmail, phoneNumber, machedUserName, machedPassword, isHeyatElmi, salary, unit, totalSalary);

        return professor;
    }

    public void employeeServiceAddStudent() {
        Student student = getStudentFromInput();
        studentService.saveOrUpdate(student);
        employeeMunu();
    }

    public void employeeServiceDeleteStudent() {
        System.out.println(studentService.showAll(Student.class));
        Integer id = scanner.nextInt();
        Student student = studentService.findById(id.longValue());
        studentService.delete(student);
        employeeMunu();
    }

    public void employeeServiceEditStudent() {
        System.out.println(studentService.showAll(Student.class));
        Integer id = scanner.nextInt();
        Student student = studentService.findById(id.longValue());
        System.out.println("To edit fields please Enter Number 1  for first name ");
        System.out.println("To edit fields please Enter Number 2  for last name ");
        System.out.println("To edit fields please Enter Number 3  for national code ");
        System.out.println("To edit fields please Enter Number 4  for password ");
        System.out.println("To edit fields please Enter Number 5  for phone number ");
        System.out.println("To edit fields please Enter Number 6  for username ");
        System.out.println("To edit fields please Enter Number 7  for email ");
        System.out.println("To edit fields please Enter Number 8  for date of birth ");
        System.out.println("To edit fields please Enter Number 9  for last Average ");
        System.out.println("To edit fields please Enter Number 10  for Student code ");
        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> student.setFirstName(scanner.next());
            case 2 -> student.setLastName(scanner.next());
            case 3 -> student.setNationalCode(scanner.next());
            case 4 -> student.setPassword(scanner.next());
            case 5 -> student.setPhoneNumber(scanner.next());
            case 6 -> student.setUsername(scanner.next());
            case 7 -> student.setEmail(scanner.next());
            case 8 -> student.setDateOfBirth(scanner.next());
            case 9 -> student.setLastAverage(scanner.nextInt());
            case 10 -> student.setStudentCode(scanner.next());


            default -> {
                System.out.println("Fake input,please Enter Number");
                employeeServiceEditStudent();
            }

        }
        studentService.saveOrUpdate(student);
        employeeMunu();
    }

    public void employeeServiceAddProfessor() {
        Professor professor = getProfessorFromInput();
        professorService.saveOrUpdate(professor);
        employeeMunu();
    }

    public void employeeServiceDeleteProfessor() {
        System.out.println("please enter id");
        Integer id = scanner.nextInt();
        Professor professor = professorService.findById(id.longValue());
        professorService.delete(professor);
        employeeMunu();
    }

    public void employeeServiceEditProfessor() {
        System.out.println("please enter id");
        Integer id = scanner.nextInt();
        Professor professor = professorService.findById(id.longValue());
        System.out.println("To edit fields please Enter Number 1  for first name ");
        System.out.println("To edit fields please Enter Number 2  for last name ");
        System.out.println("To edit fields please Enter Number 3  for national code ");
        System.out.println("To edit fields please Enter Number 4  for password ");
        System.out.println("To edit fields please Enter Number 5  for phone number ");
        System.out.println("To edit fields please Enter Number 6  for username ");
        System.out.println("To edit fields please Enter Number 7  for email ");
        System.out.println("To edit fields please Enter Number 8  for date of birth ");
        System.out.println("To edit fields please Enter Number 9  for hoqoq paye ");
        System.out.println("To edit fields please Enter Number 10  for vahed mabna ");
        System.out.println("To edit fields please Enter Number 11  for salary ");
        System.out.println("To edit fields please Enter Number 12  for is heyat elmi ");

        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> professor.setFirstName(scanner.next());
            case 2 -> professor.setLastName(scanner.next());
            case 3 -> professor.setNationalCode(scanner.next());
            case 4 -> professor.setPassword(scanner.next());
            case 5 -> professor.setPhoneNumber(scanner.next());
            case 6 -> professor.setUsername(scanner.next());
            case 7 -> professor.setEmail(scanner.next());
            case 8 -> professor.setDateOfBirth(scanner.next());
            case 9 -> professor.setHoqoqPaye(scanner.nextInt());
            case 10 -> professor.setVahedMabna(scanner.nextInt());
            case 11 -> professor.setSalary(scanner.nextInt());
            case 12 -> professor.setIsHeyatElmi(scanner.next());


            default -> {
                System.out.println("Fake input,please Enter Number");
                employeeServiceEditProfessor();
            }
        }
        professorService.saveOrUpdate(professor);
        employeeMunu();
    }

    public void employeeServiceAddEmployee() {
        Employee employee = getEmployeeFromInput();
        employeeService.saveOrUpdate(employee);
        employeeMunu();
    }

    public void employeeServiceDeleteEmployee() {
        System.out.println(employeeService.showAll(Employee.class));
        Integer id = scanner.nextInt();
        Employee employee = employeeService.findById(id.longValue());
        employeeService.delete(employee);
        employeeMunu();
    }

    public void employeeServiceEditEmployee() {
        System.out.println("please enter id");
        Integer id = scanner.nextInt();
        Employee employee = employeeService.findById(id.longValue());
        System.out.println("To edit fields please Enter Number 1  for first name ");
        System.out.println("To edit fields please Enter Number 2  for last name ");
        System.out.println("To edit fields please Enter Number 3  for national code ");
        System.out.println("To edit fields please Enter Number 4  for password ");
        System.out.println("To edit fields please Enter Number 5  for phone number ");
        System.out.println("To edit fields please Enter Number 6  for username ");
        System.out.println("To edit fields please Enter Number 7  for email ");
        System.out.println("To edit fields please Enter Number 8  for date of birth ");
        System.out.println("To edit fields please Enter Number 9  for last Average ");
        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> employee.setFirstName(scanner.next());
            case 2 -> employee.setLastName(scanner.next());
            case 3 -> employee.setNationalCode(scanner.next());
            case 4 -> employee.setPassword(scanner.next());
            case 5 -> employee.setPhoneNumber(scanner.next());
            case 6 -> employee.setUsername(scanner.next());
            case 7 -> employee.setEmail(scanner.next());
            case 8 -> employee.setDateOfBirth(scanner.next());
            case 9 -> employee.setSalary(scanner.nextInt());


            default -> {
                System.out.println("Fake input,please Enter Number");
                employeeServiceEditEmployee();
            }

        }
        employeeService.saveOrUpdate(employee);
        employeeMunu();
    }

    public void employeeServiceAddCourse() {
        Course course = getCourseFromInput();
        courseService.saveOrUpdate(course);
        courseServices();
    }

    public void employeeServiceDeleteCourse() {
        System.out.println("please enter id");
        Integer id = scanner.nextInt();
        Course course = courseService.findById(id.longValue());
        courseService.delete(course);
        courseServices();
    }


    public void employeeServiceEditCourse() {
        System.out.println("please enter id");
        Integer id = scanner.nextInt();
        Course course = courseService.findById(id.longValue());
        System.out.println("To edit fields please Enter Number 1  for course name ");
        System.out.println("To edit fields please Enter Number 2  for unit ");
        System.out.println("To edit fields please Enter Number 3  for course code ");
        System.out.println("To edit fields please Enter Number 4  for year");

        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> course.setCourseName(scanner.next());
            case 2 -> course.setUnit(scanner.nextInt());
            case 3 -> course.setCourseCode(scanner.next());
            case 4 -> course.setYear(scanner.nextInt());

            default -> {
                System.out.println("Fake input,please Enter Number");
                employeeServiceAddCourse();
            }

        }
        courseService.saveOrUpdate(course);
        courseServices();
    }

    public void myServicesAsEmployee() {

        System.out.println("Information = " + employeeService.findById(loggedInUserId));
        System.out.println(" salary = " + getBaseSalary());
        employeeMunu();
    }


    //////////////////////////////////////////////////////////////////////////

    public void studentMunu() {
        System.out.println(" ***   Student Menu   ***");
        System.out.println("1- See My information");
        System.out.println("2- See Course List");
        System.out.println("3- Choose Course for Register");
        System.out.println("4- See Registered Courses + Final score");
        System.out.println("0- log out");


        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> myServicesAsStudent();
            case 2 -> seeCoursesList();
            case 3 -> studentEnrollmentServices();
            case 4 -> seeFinalScores();
            case 0 -> logOut();


            default -> {
                System.out.println("Fake input,please Enter Number");
                studentMunu();
            }

        }
    }



    public void studentEnrollmentServices() {
        Integer maxUnit = 20;

        courseService.showAll(Course.class).forEach(System.out::println);
        System.out.println("Choose Course_id that you want register ...");
        int chossedCourseId = scanner.nextInt();
        scanner.nextLine();

        if (currentTermYear == 0 || currentTermNumber == 0) {
            System.out.println("Term not found Please Tel Education for add new Term");
            System.out.println("for continue enter any key");
            scanner.nextLine();
            studentMunu();
        }
        calculateLastTerm(currentTermYear, currentTermNumber);
        if (courseEnrollmentService.isAverageMorThan18(loggedInUserId.intValue(), lastTermYear, lastTermNumber))
            maxUnit = 24;

        Integer unitStudent = courseOccurrenceService.findUnitStudent(loggedInUserId.intValue(), currentTermYear, currentTermNumber);
        Integer choosedCourseUnit = courseService.findById(Long.valueOf(chossedCourseId)).getUnit();
        if (choosedCourseUnit + unitStudent <= maxUnit) {
            if (!courseEnrollmentService.isCoursePassed(loggedInUserId.intValue(), chossedCourseId)) {
                if (!courseEnrollmentService.isEnrollment(loggedInUserId.intValue(), chossedCourseId)) {
                    CourseEnrollment courseEnrollment = new CourseEnrollment();
                    courseEnrollment.setStudent(studentService.findById(loggedInUserId));
                    System.out.println("Choose Group id that you want register ...");
                    int choosedCourseOccurrenceId = scanner.nextInt();
                    scanner.nextLine();
                    courseEnrollment.setCourseOccurrence(courseOccurrenceService.findById(Long.valueOf(choosedCourseOccurrenceId)));
                    courseEnrollmentService.saveOrUpdate(courseEnrollment);
                } else {
                    System.out.println("Course is Exists in Registered Courses in This Term ");
                    studentEnrollmentServices();
                }
            } else {
                System.out.println("this course passed last terms");
                studentEnrollmentServices();
            }

        } else {
            System.out.println("your Max unit Courses for this Term reached" + "\n max unit=" + maxUnit +
                    "registered =" + unitStudent + "unit of Course that you choosed =" + choosedCourseUnit);

        }
        studentMunu();
    }

///////////////////////////////////////////////////////////////////////////////////






    }
