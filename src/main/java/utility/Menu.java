package utility;

import Model.*;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import service.card.CardServiceImpl;
import service.installment.InstallmentServiceImpl;
import service.loan.LoanServiceImpl;
import service.student.StudentServiceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


//@NoArgsConstructor
public class Menu {
    private int currentTermYear = 403;
    private int lastTermYear = 0;
    private int currentTermNumber = 2;
    private int lastTermNumber = 0;
    private String username = null;
    private String password = null;
    private Long loggedInUserId;
    private String loggedInUsername = null;
    private final StudentServiceImpl studentService = ApplicationContext.getStudentService();
    private final InstallmentServiceImpl installmentService = ApplicationContext.getInstallmentService();
    private final CardServiceImpl caredService = ApplicationContext.getCardService();
    private final LoanServiceImpl loanService = ApplicationContext.getLoanService();

    private final Scanner scanner = new Scanner(System.in);


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
            case 2 -> signUp();
            case 0 -> System.out.println("به امید دیدار مجدد");
            default -> System.out.println("ورودی نامعبر! لطفا عددی بین صفر تا دو وارد کنید ");
        }
    }

    public void signIn() {
        System.out.println("***   منوی ورود کابر   ***");

        do {
            System.out.println("لطفا نام کاربری خود را وارد کنید :");
            username = scanner.nextLine();
        } while (!Validation.isValidUsername(username));
        System.out.println("لطفا کلمه عبور خود را وارد کنید :");
        password = scanner.nextLine();

        if (studentService.authentication(username, password) != null) {
            loggedInUsername = username;
            loggedInUserId = studentService.authentication(username, password).getId();
            System.out.println(studentService.findById(loggedInUserId).getFirstName());
            System.out.println("عزیز!  خوش آمدید");
            studentMenu();
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

    public void studentMenu() {
        updateCurrentTerm();
        System.out.println(" ***   منوی کاربری دانشجو   ***");
        System.out.println("1- ثبت نام برای وام");
        System.out.println("2- بازپرداخت ");
        System.out.println("0- خروج از حساب کاربری");


        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 0 -> logOut();
            case 1 -> signUpForLoan();
//            case 2 -> employeeServiceDeleteStudent();


            default -> {
                System.out.println("ورودی نامعبر ! لطفا مجددا عدد خود را وارد کنید");
                studentMenu();
            }
        }
    }

    public void updateCurrentTerm() {
        Student student = studentService.findById(loggedInUserId);
        LocalDate loginDate = LocalDate.now();
        LocalDate inputDate;
        LocalDate mehrDate = LocalDate.of(student.getAppliedYear(), 7, 1);
        LocalDate bahmanDate = LocalDate.of(student.getAppliedYear(), 10, 1);
        if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
            inputDate = mehrDate;
        } else {
            inputDate = bahmanDate;
        }
        int term = Period.between(inputDate, loginDate).getYears() * 2;
        if (loginDate.getDayOfYear() >= 187 & loginDate.getDayOfYear() < 317)
            if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
                term++;
            } else {
                term = term + 2;
            }
        else {
            if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
                term = term + 2;
            } else {
                term++;
            }
        }
        student.setCurrentTerm(term);
        studentService.saveOrUpdate(student);
    }

    public Student getStudentFromInput() {


        System.out.println("Enter first name :");
        String firstName = scanner.next();
        System.out.println("Enter last name :");
        String lastName = scanner.next();
        System.out.println("Enter father Name :");
        String fatherName = scanner.next();
        System.out.println("Enter mother Name :");
        String motherName = scanner.next();
        System.out.println("Enter birth certificate number :");
        String birthCertificateNumber = scanner.next();
        System.out.println("Enter national Code :");
        String nationalCode = scanner.next();
        System.out.println("Enter date of birth (for ex dd/MM/yyyy :");
        String birthDateStr = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse(birthDateStr, formatter);
        System.out.println("Enter student code :");
        String studentCode = scanner.next();
        System.out.println("Enter University Name :");
        String universityName = scanner.next();
        System.out.println("Enter University Type :(DOLATI,\n" +
                "    GHEYRE_ENTEFAEI,\n" +
                "    PARDIS,\n" +
                "    ZARFIAT_MAZAD,\n" +
                "    PAYAM_NOOR,\n" +
                "    ELMI_KARBORDI,\n" +
                "    AZAD)");
        UniversityTypes universityType = UniversityTypes.valueOf(scanner.next().toUpperCase());
        System.out.println("Enter Applied Term : MEHR,\n" +
                "    BAHMAN");
        AppliedTerm appliedTerm = AppliedTerm.valueOf(scanner.next().toUpperCase());
        System.out.println("Enter Applied Year for (example 1403) :");
        Integer appliedYear = scanner.nextInt();
        System.out.println("Enter Edu stage(maghta tahsili) : KARDANI,\n" +
                "    KARSHENASI_NAPEYVASTEH,\n" +
                "    KARSHENASI_PEYVASTEH,\n" +
                "    KARSHENASI_ARSHAD_PEYVASTEH,\n" +
                "    KARSHENASI_ARSHAD_NAPEYVASTEH,\n" +
                "    DOCTORAYE_HERFEYI,\n" +
                "    DOCTORAYE_PEYVASTEH,\n" +
                "    DOCTORAYE_TAKHASOSI_NAPEYVASTEH");
        EduStages eduStage = EduStages.valueOf(scanner.next().toUpperCase());
        String password = generatePassword();


        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fatherName(fatherName)
                .motherName(motherName)
                .birthCertificateNumber(birthCertificateNumber)
                .nationalCode(nationalCode)
                .birthDate(birthdate)
                .studentCode(studentCode)
                .universityName(universityName)
                .universityType(universityType)
                .appliedYear(appliedYear)
                .appliedTerm(appliedTerm)
                .eduStage(eduStage)
                .username(nationalCode)
                .password(password)
                .build();
        calculateGraduationDate(student);
        if (student.getUniversityType() == UniversityTypes.DOLATI) {
            System.out.println("Enter applied Type :   ROOZANEH,\n" +
                    "    SHABANEH");
            AppliedTypes appliedType = AppliedTypes.valueOf(scanner.next().toUpperCase());
            student.setAppliedType(appliedType);
        }


        return student;
    }

    public String generatePassword() {
        String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String nums = "0123456789";
        String symbols = "@#$%&";

        String passSymbols = charsCaps + chars + nums + symbols;
        Random rnd = new Random();

        char[] password = new char[8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            password[i] = passSymbols.charAt(rnd.nextInt(passSymbols.length()));

        }
        return password.toString();
    }

    public void signUp() {
        Student student = getStudentFromInput();
        ApplicationContext.getStudentService().saveOrUpdate(student);
        if (studentService.isExistsByUsername(student.getUsername())) {
            System.out.println("ثبت نام با موفقیت انجام شد . برای ورود می توانید با نام کاربری و رمز عبور زیر اقدام فرمایید.");
            System.out.println("نام کاربری :");
            System.out.println(student.getUsername());
            System.out.println("کلمه عبور :");
            System.out.println(student.getPassword());
        }
        publicMenu();
    }

    public void signUpForLoan() {

        Boolean isEligibleEducationLoan = false;
        Boolean isEligibleTuitionLoan = false;
        Boolean isEligibleHousingLoan = false;
        Integer loanAmount = 0;

        Student student = studentService.findById(loggedInUserId);


        System.out.println("وام مورد نظر خود را انتخاب فرمائید :");
        System.out.println("1-وام تحصیلی");
        System.out.println("2-وام ودیعه مسکن ");
        System.out.println("3-وام شهریه");
        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number) {
            case 1 -> educationLoan();
            case 2 -> housingLoan();
            case 3 -> tuitionLoan();

            default -> {
                System.out.println("Fake input,please Enter Number");
                signUpForLoan();
            }

        }
        studentMenu();
    }

    public Card getCardFromInput() {
        System.out.println("Enter first four Card Number (****) **** **** **** :");
        String fourNumber1 = scanner.next();
        System.out.println("Enter 2th four Card Number **** (****) **** **** :");
        String fourNumber2 = scanner.next();
        System.out.println("Enter 3th four Card Number **** **** (****) **** :");
        String fourNumber3 = scanner.next();
        System.out.println("Enter last four Card Number **** **** **** (****) :");
        String fourNumber4 = scanner.next();
        System.out.println("Enter cvv2 :");
        String cvv2 = scanner.next();
        System.out.println("Enter Year of expire :");
        String expireYear = scanner.next();
        System.out.println("Enter Month of expire :");
        String expireMonth = scanner.next();

        return Card.builder()
                .fourNumber1(fourNumber1)
                .fourNumber2(fourNumber2)
                .fourNumber3(fourNumber3)
                .fourNumber4(fourNumber4)
                .cvv2(cvv2)
                .expireYear(expireYear)
                .expireMonth(expireMonth)
                .build();
    }

    public void educationLoan() {
        Integer amount = 0;
        Student student = studentService.findById(loggedInUserId);
        if (student.getEduStage().equals(EduStages.KARDANI) ||
                student.getEduStage().equals(EduStages.KARSHENASI_NAPEYVASTEH) ||
                student.getEduStage().equals(EduStages.KARSHENASI_PEYVASTEH)) {
            amount = 1900000;
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_PEYVASTEH) ||
                student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_NAPEYVASTEH) ||
                student.getEduStage().equals(EduStages.DOCTORAYE_PEYVASTEH) ||
                student.getEduStage().equals(EduStages.DOCTORAYE_HERFEYI)) {
            amount = 2250000;
        } else {
            amount = 2600000;
        }
        LocalDate getLoanDate = LocalDate.now();

        Card card = getCardFromInput();
        caredService.saveOrUpdate(card);
        Loan loan = Loan.builder()
                .student(student)
                .studentStage(student.getEduStage())
                .getLoanTerm(student.getCurrentTerm())
                .amount(amount)
                .getLoanDate(getLoanDate)
                .card(card)
                .loanType(LoanTypes.EDUCATION)
                .isSettlement(Boolean.FALSE)
                .build();
        loanService.saveOrUpdate(loan);


        //////////////////////////////////
        long[] installments = new long[60];
        for (int i = 0; i < 5; i++) {
            int baghimande = (5 - i) / 5 * amount;
            for (int j = 0; j < 12; j++) {
                installments[12 * i + j] = Math.round(((2 * i + 1) * 0.04 * amount / 12) + 0.04 * baghimande / 12);
            }
        }

        for (int i = 0; i < 60; i++) {
            Installment installment = Installment.builder()
                    .paymentStageNum(i + 1)
                    .dueDate(student.getExpireDate().plusMonths(i + 1))
                    .amount(Math.toIntExact(installments[i]))
                    .payedStatus(Boolean.FALSE)
                    .loan(loan)
                    .build();
            installmentService.saveOrUpdate(installment);

        }
        studentMenu();
    }

    public void housingLoan() {
        Card card = getCardFromInput();
        caredService.saveOrUpdate(card);
        Integer amount = 0;
        Student student = studentService.findById(loggedInUserId);
        System.out.println("شهر محل زندگی خود را وارد کنید");
        System.out.println("   TEHRAN,\n" +
                "    RASHT,\n" +
                "    ESFAHAN,\n" +
                "    TABRIZ,\n" +
                "    SHIRAZ,\n" +
                "    AHWAZ,\n" +
                "    QOM,\n" +
                "    MASHHAD,\n" +
                "    KARAJ,\n" +
                "    Other");
        City city = City.valueOf(scanner.next().toUpperCase());

        if (city.equals(City.TEHRAN)) {
            amount = 32000000;
        } else if (city.equals(City.AHWAZ) || city.equals(City.RASHT)
                || city.equals(City.TABRIZ) || city.equals(City.SHIRAZ)
                || city.equals(City.ESFAHAN) || city.equals(City.QOM)
                || city.equals(City.MASHHAD) || city.equals(City.KARAJ)) {
            amount = 26000000;
        } else {
            amount = 19500000;
        }
        LocalDate getLoanDate = LocalDate.now();

        Loan loan = Loan.builder()
                .student(student)
                .studentStage(student.getEduStage())
                .amount(amount)
                .getLoanDate(getLoanDate)
                .studentCity(city)
                .card(card)
                .isSettlement(Boolean.FALSE)
                .loanType(LoanTypes.HOUSING)
                .build();
        loanService.saveOrUpdate(loan);

        //////////////////////////////////
        long[] installments = new long[60];
        for (int i = 0; i < 5; i++) {
            int baghimande = (5 - i) / 5 * amount;
            for (int j = 0; j < 12; j++) {
                installments[12 * i + j] = Math.round(((2 * i + 1) * 0.04 * amount / 12) + 0.04 * baghimande / 12);
            }
        }

        for (int i = 0; i < 60; i++) {
            Installment installment = Installment.builder()
                    .paymentStageNum(i + 1)
                    .dueDate(student.getExpireDate().plusMonths(i + 1))
                    .amount(Math.toIntExact(installments[i]))
                    .payedStatus(Boolean.FALSE)
                    .loan(loan)
                    .build();
            installmentService.saveOrUpdate(installment);

        }

        studentMenu();
    }

    public void tuitionLoan() {
        Card card = getCardFromInput();
        Integer amount = 0;
        Student student = studentService.findById(loggedInUserId);
        if (student.getEduStage().equals(EduStages.KARDANI) ||
                student.getEduStage().equals(EduStages.KARSHENASI_NAPEYVASTEH) ||
                student.getEduStage().equals(EduStages.KARSHENASI_PEYVASTEH)) {
            amount = 1300000;
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_PEYVASTEH) ||
                student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_NAPEYVASTEH) ||
                student.getEduStage().equals(EduStages.DOCTORAYE_PEYVASTEH) ||
                student.getEduStage().equals(EduStages.DOCTORAYE_HERFEYI)) {
            amount = 2600000;
        } else {
            amount = 65000000;
        }
        LocalDate getLoanDate = LocalDate.now();

        Loan loan = Loan.builder()
                .student(student)
                .studentStage(student.getEduStage())
                .getLoanTerm(student.getCurrentTerm())
                .amount(amount)
                .getLoanDate(getLoanDate)
                .isSettlement(Boolean.FALSE)
                .loanType(LoanTypes.TUITION)
                .card(card)
                .build();
        loanService.saveOrUpdate(loan);

        //////////////////////////////////
        long[] installments = new long[60];
        for (int i = 0; i < 5; i++) {
            int baghimande = (5 - i) / 5 * amount;
            for (int j = 0; j < 12; j++) {
                installments[12 * i + j] = Math.round(((2 * i + 1) * 0.04 * amount / 12) + 0.04 * baghimande / 12);
            }
        }

        for (int i = 0; i < 60; i++) {
            Installment installment = Installment.builder()
                    .paymentStageNum(i + 1)
                    .dueDate(student.getExpireDate().plusMonths(i + 1))
                    .amount(Math.toIntExact(installments[i]))
                    .payedStatus(Boolean.FALSE)
                    .loan(loan)
                    .build();
            installmentService.saveOrUpdate(installment);

        }

        studentMenu();
    }


    public void calculateGraduationDate(Student student) {
        LocalDate inputDate;
        if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
            inputDate = LocalDate.of(student.getAppliedYear(), 7, 1);
        } else {
            inputDate = LocalDate.of(student.getAppliedYear(), 10, 1);
        }
        if (student.getEduStage().equals(EduStages.KARDANI) || student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_NAPEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(2));
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_PEYVASTEH) || student.getEduStage().equals(EduStages.KARSHENASI_NAPEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(4));
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_PEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(6));
        } else {
            student.setExpireDate(inputDate.plusYears(5));
        }
    }

//
//    public void employeeServiceAddProfessor() {
//        Professor professor = getProfessorFromInput();
//        professorService.saveOrUpdate(professor);
//        employeeMunu();
//    }
//
//    public void employeeServiceDeleteProfessor() {
//        System.out.println("please enter id");
//        Integer id = scanner.nextInt();
//        Professor professor = professorService.findById(id.longValue());
//        professorService.delete(professor);
//        employeeMunu();
//    }
//
//    public void employeeServiceEditProfessor() {
//        System.out.println("please enter id");
//        Integer id = scanner.nextInt();
//        Professor professor = professorService.findById(id.longValue());
//        System.out.println("To edit fields please Enter Number 1  for first name ");
//        System.out.println("To edit fields please Enter Number 2  for last name ");
//        System.out.println("To edit fields please Enter Number 3  for national code ");
//        System.out.println("To edit fields please Enter Number 4  for password ");
//        System.out.println("To edit fields please Enter Number 5  for phone number ");
//        System.out.println("To edit fields please Enter Number 6  for username ");
//        System.out.println("To edit fields please Enter Number 7  for email ");
//        System.out.println("To edit fields please Enter Number 8  for date of birth ");
//        System.out.println("To edit fields please Enter Number 9  for hoqoq paye ");
//        System.out.println("To edit fields please Enter Number 10  for vahed mabna ");
//        System.out.println("To edit fields please Enter Number 11  for salary ");
//        System.out.println("To edit fields please Enter Number 12  for is heyat elmi ");
//
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        switch (number) {
//            case 1 -> professor.setFirstName(scanner.next());
//            case 2 -> professor.setLastName(scanner.next());
//            case 3 -> professor.setNationalCode(scanner.next());
//            case 4 -> professor.setPassword(scanner.next());
//            case 5 -> professor.setPhoneNumber(scanner.next());
//            case 6 -> professor.setUsername(scanner.next());
//            case 7 -> professor.setEmail(scanner.next());
//            case 8 -> professor.setDateOfBirth(scanner.next());
//            case 9 -> professor.setHoqoqPaye(scanner.nextInt());
//            case 10 -> professor.setVahedMabna(scanner.nextInt());
//            case 11 -> professor.setSalary(scanner.nextInt());
//            case 12 -> professor.setIsHeyatElmi(scanner.next());
//
//
//            default -> {
//                System.out.println("Fake input,please Enter Number");
//                employeeServiceEditProfessor();
//            }
//        }
//        professorService.saveOrUpdate(professor);
//        employeeMunu();
//    }
//
//    public void employeeServiceAddEmployee() {
//        Employee employee = getEmployeeFromInput();
//        employeeService.saveOrUpdate(employee);
//        employeeMunu();
//    }
//
//    public void employeeServiceDeleteEmployee() {
//        System.out.println(employeeService.showAll(Employee.class));
//        Integer id = scanner.nextInt();
//        Employee employee = employeeService.findById(id.longValue());
//        employeeService.delete(employee);
//        employeeMunu();
//    }
//
//    public void employeeServiceEditEmployee() {
//        System.out.println("please enter id");
//        Integer id = scanner.nextInt();
//        Employee employee = employeeService.findById(id.longValue());
//        System.out.println("To edit fields please Enter Number 1  for first name ");
//        System.out.println("To edit fields please Enter Number 2  for last name ");
//        System.out.println("To edit fields please Enter Number 3  for national code ");
//        System.out.println("To edit fields please Enter Number 4  for password ");
//        System.out.println("To edit fields please Enter Number 5  for phone number ");
//        System.out.println("To edit fields please Enter Number 6  for username ");
//        System.out.println("To edit fields please Enter Number 7  for email ");
//        System.out.println("To edit fields please Enter Number 8  for date of birth ");
//        System.out.println("To edit fields please Enter Number 9  for last Average ");
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        switch (number) {
//            case 1 -> employee.setFirstName(scanner.next());
//            case 2 -> employee.setLastName(scanner.next());
//            case 3 -> employee.setNationalCode(scanner.next());
//            case 4 -> employee.setPassword(scanner.next());
//            case 5 -> employee.setPhoneNumber(scanner.next());
//            case 6 -> employee.setUsername(scanner.next());
//            case 7 -> employee.setEmail(scanner.next());
//            case 8 -> employee.setDateOfBirth(scanner.next());
//            case 9 -> employee.setSalary(scanner.nextInt());
//
//
//            default -> {
//                System.out.println("Fake input,please Enter Number");
//                employeeServiceEditEmployee();
//            }
//
//        }
//        employeeService.saveOrUpdate(employee);
//        employeeMunu();
//    }
//
//    public void employeeServiceAddCourse() {
//        Course course = getCourseFromInput();
//        courseService.saveOrUpdate(course);
//        courseServices();
//    }
//
//    public void employeeServiceDeleteCourse() {
//        System.out.println("please enter id");
//        Integer id = scanner.nextInt();
//        Course course = courseService.findById(id.longValue());
//        courseService.delete(course);
//        courseServices();
//    }
//
//
//    public void employeeServiceEditCourse() {
//        System.out.println("please enter id");
//        Integer id = scanner.nextInt();
//        Course course = courseService.findById(id.longValue());
//        System.out.println("To edit fields please Enter Number 1  for course name ");
//        System.out.println("To edit fields please Enter Number 2  for unit ");
//        System.out.println("To edit fields please Enter Number 3  for course code ");
//        System.out.println("To edit fields please Enter Number 4  for year");
//
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        switch (number) {
//            case 1 -> course.setCourseName(scanner.next());
//            case 2 -> course.setUnit(scanner.nextInt());
//            case 3 -> course.setCourseCode(scanner.next());
//            case 4 -> course.setYear(scanner.nextInt());
//
//            default -> {
//                System.out.println("Fake input,please Enter Number");
//                employeeServiceAddCourse();
//            }
//
//        }
//        courseService.saveOrUpdate(course);
//        courseServices();
//    }
//
//    public void myServicesAsEmployee() {
//
//        System.out.println("Information = " + employeeService.findById(loggedInUserId));
//        System.out.println(" salary = " + getBaseSalary());
//        employeeMunu();
//    }
//
//
//    //////////////////////////////////////////////////////////////////////////
//
//    public void studentMunu() {
//        System.out.println(" ***   Student Menu   ***");
//        System.out.println("1- See My information");
//        System.out.println("2- See Course List");
//        System.out.println("3- Choose Course for Register");
//        System.out.println("4- See Registered Courses + Final score");
//        System.out.println("0- log out");
//
//
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        switch (number) {
//            case 1 -> myServicesAsStudent();
//            case 2 -> seeCoursesList();
//            case 3 -> studentEnrollmentServices();
//            case 4 -> seeFinalScores();
//            case 0 -> logOut();
//
//
//            default -> {
//                System.out.println("Fake input,please Enter Number");
//                studentMunu();
//            }
//
//        }
//    }
//
//
//
//    public void studentEnrollmentServices() {
//        Integer maxUnit = 20;
//
//        courseService.showAll(Course.class).forEach(System.out::println);
//        System.out.println("Choose Course_id that you want register ...");
//        int chossedCourseId = scanner.nextInt();
//        scanner.nextLine();
//
//        if (currentTermYear == 0 || currentTermNumber == 0) {
//            System.out.println("Term not found Please Tel Education for add new Term");
//            System.out.println("for continue enter any key");
//            scanner.nextLine();
//            studentMunu();
//        }
//        calculateLastTerm(currentTermYear, currentTermNumber);
//        if (courseEnrollmentService.isAverageMorThan18(loggedInUserId.intValue(), lastTermYear, lastTermNumber))
//            maxUnit = 24;
//
//        Integer unitStudent = courseOccurrenceService.findUnitStudent(loggedInUserId.intValue(), currentTermYear, currentTermNumber);
//        Integer choosedCourseUnit = courseService.findById(Long.valueOf(chossedCourseId)).getUnit();
//        if (choosedCourseUnit + unitStudent <= maxUnit) {
//            if (!courseEnrollmentService.isCoursePassed(loggedInUserId.intValue(), chossedCourseId)) {
//                if (!courseEnrollmentService.isEnrollment(loggedInUserId.intValue(), chossedCourseId)) {
//                    CourseEnrollment courseEnrollment = new CourseEnrollment();
//                    courseEnrollment.setStudent(studentService.findById(loggedInUserId));
//                    System.out.println("Choose Group id that you want register ...");
//                    int choosedCourseOccurrenceId = scanner.nextInt();
//                    scanner.nextLine();
//                    courseEnrollment.setCourseOccurrence(courseOccurrenceService.findById(Long.valueOf(choosedCourseOccurrenceId)));
//                    courseEnrollmentService.saveOrUpdate(courseEnrollment);
//                } else {
//                    System.out.println("Course is Exists in Registered Courses in This Term ");
//                    studentEnrollmentServices();
//                }
//            } else {
//                System.out.println("this course passed last terms");
//                studentEnrollmentServices();
//            }
//
//        } else {
//            System.out.println("your Max unit Courses for this Term reached" + "\n max unit=" + maxUnit +
//                    "registered =" + unitStudent + "unit of Course that you choosed =" + choosedCourseUnit);
//
//        }
//        studentMunu();
//    }

///////////////////////////////////////////////////////////////////////////////////


}

