package utility;

import Model.*;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import service.card.CardServiceImpl;
import service.installment.InstallmentServiceImpl;
import service.loan.LoanServiceImpl;
import service.morInfo.MorInfoForHousingLoanServiceImpl;
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
    private final MorInfoForHousingLoanServiceImpl morInfoForHousingLoanService = ApplicationContext.getMorInfoForHousingLoanService();

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


        int number = getIntFromUser();


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


        int number = getIntFromUser();

        switch (number) {
            case 0 -> logOut();
            case 1 -> signUpForLoan();
            case 2 -> seeAndPeyInstallments();


            default -> {
                System.out.println("ورودی نامعبر ! لطفا مجددا عدد خود را وارد کنید");
                studentMenu();
            }
        }
    }

    public void updateCurrentTerm() {
        Student student = studentService.findById(loggedInUserId);
        LocalDate loginDate = LocalDate.of(1403, 2, 21);
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

    public Integer getIntFromUser() {
        Integer input = null;
        while (true) {
            try {
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("ورودی باید از جنس عدد باشد لطفا مجددا سعی کنید");
            }
            scanner.nextLine();
        }
        scanner.nextLine();
        return input;
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
        Student student = studentService.findById(loggedInUserId);
        System.out.println("وام مورد نظر خود را انتخاب فرمائید :");
        System.out.println("1-وام تحصیلی");
        System.out.println("2-وام ودیعه مسکن ");
        System.out.println("3-وام شهریه");
        int number = getIntFromUser();
        switch (number) {
            case 1 -> {
                if (!loanService.isExistEduLoanInTerm(student)) educationLoan();
                else {
                    System.out.println("شما در این ترم این رام را دریافت کرده اید");
                }
            }
            case 2 -> {
                if (!loanService.isExistHousingLoanInTerm(student)) housingLoan();
                else {
                    System.out.println("شما در این مقطع این رام را دریافت کرده اید");
                }
            }
            case 3 -> {
                if (!loanService.isExistTuitionLoanInTerm(student)) tuitionLoan();
                else {
                    System.out.println("شما در این ترم این رام را دریافت کرده اید");
                }
            }

            default -> {
                System.out.println("Fake input,please Enter Number");
                signUpForLoan();
            }

        }
        studentMenu();
    }

    public Card getCardFromInput() {
        System.out.println("چهار شماره اول را وارد کنید (****) **** **** **** :");
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

        Card card = Card.builder()
                .fourNumber1(fourNumber1)
                .fourNumber2(fourNumber2)
                .fourNumber3(fourNumber3)
                .fourNumber4(fourNumber4)
                .cvv2(cvv2)
                .expireYear(expireYear)
                .expireMonth(expireMonth)
                .build();
        List<String> validCards = List.of("6037", "5894", "6273", "6280");

        while (!validCards.contains(card.getFourNumber1())) {
            System.out.println("کارت متعلق به بانک های ملی مسکن تجارت یا رفاه نیست لطفا مشخصات کارت مربوط به بانک های مذکور را وارد کنید ");
            card = getCardFromInput();
        }
        return card;
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

    public MorInfoForHousingLoan getMorInfoForHousingLoan() {
        System.out.println("لطفا کدملی همسر خود را وارد کنید :");
        String wifeNationalCode = scanner.next();
        System.out.println("لطفا نام همسر خود را وارد کنید :");
        String wifeFirstName = scanner.next();
        System.out.println("لطفا نام خانوادگی همسر خود را وارد کنید :");
        String wifeLastName = scanner.next();
        System.out.println("لطفا استان درج شده در اجاره نامه خود را وارد کنید :");
        String province = scanner.next();
        System.out.println("لطفا شهر درج شده در اجاره نامه خود را وارد کنید :");
        String city = scanner.next();
        System.out.println(" لطفا خیابان اصلی درج شده در اجاره نامه خود را وارد کنید :");
        String street = scanner.next();
        System.out.println("لطفا خیابان فرعی درج شده در اجاره نامه خود را وارد کنید :");
        String subStreet = scanner.next();
        System.out.println("لطفا کوچه درج شده در اجاره نامه خود را وارد کنید :");
        String alley = scanner.next();
        System.out.println("لطفا پلاک درج شده در اجاره نامه خود را وارد کنید :");
        String plaque = scanner.next();
        System.out.println("لطفا شماره اجاره نامه خود را وارد کنید :");
        String rentAgreeNum = scanner.next();
        return MorInfoForHousingLoan.builder()
                .wifeNationalCode(wifeNationalCode)
                .wifeFirstName(wifeFirstName)
                .wifeLastName(wifeLastName)
                .province(province)
                .city(city)
                .street(street)
                .subStreet(subStreet)
                .alley(alley)
                .plaque(plaque)
                .rentAgreeNum(rentAgreeNum)
                .build();
    }

    public void housingLoan() {
        MorInfoForHousingLoan morInfoForHousingLoan = getMorInfoForHousingLoan();
        if (!loanService.wifeHousingLoanCheck(morInfoForHousingLoan.getWifeNationalCode())) {
            System.out.println("همسر شما قبلا از این وام استفاده کرده لذا شما واجد شرایط نمی باشید");
            studentMenu();
        }
        morInfoForHousingLoanService.saveOrUpdate(morInfoForHousingLoan);
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
                .getLoanTerm(student.getCurrentTerm())
                .studentCity(city)
                .card(card)
                .morInfoForHousingLoan(morInfoForHousingLoan)
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
        caredService.saveOrUpdate(card);

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

///////////////////////////////////////////////////////////////////////////////////


    public void seeAndPeyInstallments() {
        System.out.println("برای مشاهده و پرداخت اقساط، وام مورد نظر خود را از لیست زیر انتخاب فرمائید :");
        List<Loan> loansByStudentId = loanService.findLoansByStudentId(loggedInUserId.intValue());
        System.out.println("تاریخ دریافت وام -ترم دریافت وام-مبلغ وام-نوع وام-آی دی");
        for (Loan l : loansByStudentId) {
            System.out.print(l.getId() + "-" + l.getLoanType() + " " + l.getAmount() + " " + l.getGetLoanTerm()
                    + " " + l.getGetLoanDate());
            System.out.println();
        }
        System.out.println("لطفا فقط آی دی وام را وارد کنید");
        Integer loanId = getIntFromUser();
        System.out.println("1-مشاهده اقساط پرداخت شده ");
        System.out.println("2-مشاهده اقساط پرداخت نشده ");
        System.out.println("3-پرداخت اقساط ");
        System.out.println("0-بازگشت");
        int number = getIntFromUser();
        switch (number) {
            case 0 -> studentMenu();
            case 1 -> seePayedInstallments(loanId);
            case 2 -> seeNotPayedInstallments(loanId);
            case 3 -> payInstallment(loanId);

            default -> {
                System.out.println("Fake input,please Enter Number");
                signUpForLoan();
            }

        }
        studentMenu();
    }

    public void seeNotPayedInstallments(Integer loanId) {
        List<Installment> notPayedByLoanId = installmentService.findNotPayedByLoanId(loanId);
        for (Installment i : notPayedByLoanId) {
            System.out.println(i.getPaymentStageNum() + " - " + i.getAmount() + " " + i.getDueDate());

        }
        seeAndPeyInstallments();
    }

    public void seePayedInstallments(Integer loanId) {
        List<Installment> payedByLoanId = installmentService.findPayedByLoanId(loanId);
        for (Installment i : payedByLoanId) {
            System.out.println(i.getPaymentStageNum() + " - " + i.getPayDate());
        }
        seeAndPeyInstallments();
    }

    public void payInstallment(Integer loanId) {
//        seeNotPayedInstallments(loanId);
        System.out.println("لطفا شماره قسط مورد نظر خود را برای پرداخت وارد کنید");
        Integer installmentId = getIntFromUser();
        Installment installment = installmentService.findById(Long.valueOf(installmentId.toString()));
        Card card = getCardFromInput();
        Card fetchCard = loanService.findById(Long.valueOf(loanId.toString())).getCard();
        if (
                fetchCard.getFourNumber1().equals(card.getFourNumber1()) &&
                        fetchCard.getFourNumber2().equals(card.getFourNumber2()) &&
                        fetchCard.getFourNumber3().equals(card.getFourNumber3()) &&
                        fetchCard.getFourNumber4().equals(card.getFourNumber4()) &&
                        fetchCard.getCvv2().equals(card.getCvv2()) &&
                        fetchCard.getExpireYear().equals(card.getExpireYear()) &&
                        fetchCard.getExpireMonth().equals(card.getExpireMonth())
        ) {
            installment.setPayedStatus(Boolean.TRUE);
            installmentService.saveOrUpdate(installment);
            System.out.println("پرداخت با موفقیت انجام شد");
            seeAndPeyInstallments();
        } else {
            System.out.println("شماره کارت نامعتبر است لطفا کارت مربوط به این وام را وارد کنید");
            seeAndPeyInstallments();
        }

    }

}

