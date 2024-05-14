package utility;

import Model.*;
import base.exception.NotFoundException;
import me.moallemi.persiandate.PersianDate;
import service.card.CardServiceImpl;
import service.installment.InstallmentServiceImpl;
import service.loan.LoanServiceImpl;
import service.morInfo.MorInfoForHousingLoanServiceImpl;
import service.student.StudentServiceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    private Long loggedInUserId;
    private final StudentServiceImpl studentService = ApplicationContext.getStudentService();
    private final InstallmentServiceImpl installmentService = ApplicationContext.getInstallmentService();
    private final CardServiceImpl caredService = ApplicationContext.getCardService();
    private final LoanServiceImpl loanService = ApplicationContext.getLoanService();
    private final MorInfoForHousingLoanServiceImpl morInfoForHousingLoanService = ApplicationContext.getMorInfoForHousingLoanService();

    private final Scanner scanner = new Scanner(System.in);

    private final PersianDate nowForExample = PersianDate.of(1399, 12, 1);


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
        String username;
        System.out.println("***   منوی ورود کابر   ***");
        do {
            System.out.println("لطفا نام کاربری خود را وارد کنید :");
            username = scanner.nextLine();
        } while (!Validation.isValidUsername(username));
        System.out.println("لطفا کلمه عبور خود را وارد کنید :");
        String password = scanner.nextLine();

        if (studentService.authentication(username, password) != null) {
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
        PersianDate loginDate = nowForExample;
        PersianDate inputDate;
        PersianDate mehrDate = PersianDate.of(student.getAppliedYear(), 7, 1);
        PersianDate bahmanDate = PersianDate.of(student.getAppliedYear(), 10, 1);
        if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
            inputDate = mehrDate;
        } else {
            inputDate = bahmanDate;
        }
        int term = Period.between(inputDate.toLocalDate(), loginDate.toLocalDate()).getYears() * 2;
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
        int input;
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
        String nationalCode;
        String firstName;
        String lastName;
        String fatherName;
        String motherName;
        String birthCertificateNumber;
        String birthDateStr;
        String studentCode;
        String universityName;
        Integer appliedYear;
        Boolean isMarried = null;
        Boolean liveInDorm = null;

        do {
            System.out.println("نام خود را وارد کنید(حرف اول بزرگ) :");
            firstName = scanner.next();
        } while (!Validation.isValidName(firstName));
        do {
            System.out.println("نام خانوادگی خود را وارد کنید :");
            lastName = scanner.next();
        } while (!Validation.isValidName(lastName));

        do {
            System.out.println("نام پدر خود را وارد کنید :");
            fatherName = scanner.next();
        } while (!Validation.isValidName(fatherName));

        do {
            System.out.println("نام مادر خود را وارد کنید :");
            motherName = scanner.next();
        } while (!Validation.isValidName(motherName));
        do {
            System.out.println("وضعیت تاهل : متاهل هستید؟ 1-بله 2-خیر");
            int number = getIntFromUser();
            switch (number) {
                case 1 -> isMarried = Boolean.TRUE;
                case 2 -> isMarried = Boolean.FALSE;
                default -> System.out.println("ورودی نامعتبر");
            }
        } while (isMarried == null);
        do {
            System.out.println("وضعیت سکونت: در خوابگاه دانشجویی زندگی می کنید؟ 1-بله 2-خیر");
            int number = getIntFromUser();
            switch (number) {
                case 1 -> liveInDorm = Boolean.TRUE;
                case 2 -> liveInDorm = Boolean.FALSE;
                default -> System.out.println("ورودی نامعتبر");
            }
        } while (liveInDorm == null);
        do {
            System.out.println("شماره شناسنامه خود را وارد کنید (متولدین بالای 1370 عدد صفر وارد کنید) :");
            birthCertificateNumber = scanner.next();
        } while (!Validation.isValidBirthCertificateNumber(birthCertificateNumber));
        if (Objects.equals(birthCertificateNumber, "0"))
            birthCertificateNumber = null;
        do {
            System.out.println("کد ملی خود را وارد کنید :");
            nationalCode = scanner.next();
        } while (!Validation.isValidIranianNationalCode(nationalCode));
        do {
            System.out.println("تاریخ تولد خود را وارد کنید (با این فرمت 04-05-1377 ");
            birthDateStr = scanner.next();
        } while (!Validation.isValidDate(birthDateStr));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthDateStr, formatter);
        while (birthdate.getYear() > LocalDate.now().getYear() ||
                (birthdate.getYear() == LocalDate.now().getYear() && birthdate.getDayOfYear() > LocalDate.now().getDayOfYear())) {
            System.out.println("تاریخ تولد نمی تواند بعد از امروز باشد");
            do {
                System.out.println("تاریخ تولد خود را وارد کنید (با این فرمت: 04-05-1377 )");
                birthDateStr = scanner.next();
            } while (!Validation.isValidDate(birthDateStr));
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthdate = LocalDate.parse(birthDateStr, formatter);
        }
        do {
            System.out.println("کد دانشجویی خود را وارد کنید :");
            studentCode = scanner.next();
        } while (!Validation.isValidStudentCode(studentCode));
        do {
            System.out.println("نام دانشگاه خود را وارد کنید :");
            universityName = scanner.next();
        } while (!Validation.isValidName(universityName));
        UniversityTypes universityType = getUniversityType();
        AppliedTerm appliedTerm = getAppliedTerm();
        do {
            System.out.println("سال ورود خود را به دانشگاه وارد کنید (مثلا 1403) :");
            appliedYear = getIntFromUser();
        } while (!(appliedYear > 1357 && appliedYear <= nowForExample.getYear()));
        EduStages eduStage = getEduStage();
        String password = generatePassword();
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fatherName(fatherName)
                .motherName(motherName)
                .isMarried(isMarried)
                .liveInDorm(liveInDorm)
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
            AppliedTypes appliedType = getAppliedType();
            student.setAppliedType(appliedType);
        }
        return student;
    }

    public AppliedTypes getAppliedType() {
        System.out.println("نوع قبولی خود را انتخاب کنید :");
        System.out.println("""
                1-ROOZANEH
                2-SHABANEH""");
        AppliedTypes appliedType = null;
        do {
            int number = getIntFromUser();
            switch (number) {
                case 1 -> appliedType = AppliedTypes.ROOZANEH;
                case 2 -> appliedType = AppliedTypes.SHABANEH;
                default -> System.out.println("ورودی نا معتبر");
            }
        } while (appliedType == null);
        return appliedType;
    }

    public AppliedTerm getAppliedTerm() {
        System.out.println("ترم قبولی خود را انتخاب کنید :");
        System.out.println("""
                1-MEHR
                2-BAHMAN""");
        int number = getIntFromUser();
        AppliedTerm appliedTerm = null;
        do {
            switch (number) {
                case 1 -> appliedTerm = AppliedTerm.MEHR;
                case 2 -> appliedTerm = AppliedTerm.BAHMAN;
                default -> System.out.println("ورودی نامعتبر");
            }
        } while (appliedTerm == null);
        return appliedTerm;
    }

    public UniversityTypes getUniversityType() {

        UniversityTypes universityType = null;
        do {
            System.out.println("نوع دانشگاه خود را انتخاب کنید :");
            System.out.println("""
                    1-DOLATI
                    2-GHEYRE_ENTEFAEI
                    3-PARDIS
                    4-ZARFIAT_MAZAD
                    5-PAYAM_NOOR
                    6-ELMI_KARBORDI
                    7-AZAD""");
            int number = getIntFromUser();
            switch (number) {
                case 1 -> universityType = UniversityTypes.DOLATI;
                case 2 -> universityType = UniversityTypes.GHEYRE_ENTEFAEI;
                case 3 -> universityType = UniversityTypes.PARDIS;
                case 4 -> universityType = UniversityTypes.ZARFIAT_MAZAD;
                case 5 -> universityType = UniversityTypes.PAYAM_NOOR;
                case 6 -> universityType = UniversityTypes.ELMI_KARBORDI;
                case 7 -> universityType = UniversityTypes.AZAD;

                default -> System.out.println("ورودی نامعتبر");
            }

        } while (universityType == null);
        return universityType;
    }

    public EduStages getEduStage() {
        System.out.println("مقطع تحصیلی خود را وارد کنید :");
        System.out.println("""
                1-KARDANI
                2-KARSHENASI_NAPEYVASTEH
                3-KARSHENASI_PEYVASTEH
                4-KARSHENASI_ARSHAD_PEYVASTEH
                5-KARSHENASI_ARSHAD_NAPEYVASTEH
                6-DOCTORAYE_HERFEYI
                7-DOCTORAYE_PEYVASTEH
                8-DOCTORAYE_TAKHASOSI_NAPEYVASTEH""");
        EduStages eduStage = null;
        do {
            int number = getIntFromUser();
            switch (number) {
                case 1 -> eduStage = EduStages.KARDANI;
                case 2 -> eduStage = EduStages.KARSHENASI_NAPEYVASTEH;
                case 3 -> eduStage = EduStages.KARSHENASI_PEYVASTEH;
                case 4 -> eduStage = EduStages.KARSHENASI_ARSHAD_PEYVASTEH;
                case 5 -> eduStage = EduStages.KARSHENASI_ARSHAD_NAPEYVASTEH;
                case 6 -> eduStage = EduStages.DOCTORAYE_HERFEYI;
                case 7 -> eduStage = EduStages.DOCTORAYE_PEYVASTEH;
                case 8 -> eduStage = EduStages.DOCTORAYE_TAKHASOSI_NAPEYVASTEH;
                default -> System.out.println("ورودی نامعتبر");

            }
        } while (eduStage == null);
        return eduStage;
    }

    public String generatePassword() {
        String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String nums = "0123456789";
        String symbols = "@#$%&";

        String passSymbols = charsCaps + chars + nums + symbols;
        Random rnd = new Random();

        char[] password = new char[8];
        for (int i = 0; i < 8; i++) {
            password[i] = passSymbols.charAt(rnd.nextInt(passSymbols.length()));

        }
        return Arrays.toString(password).substring(1, 9);
    }

    public void signUp() {
        Student student = getStudentFromInput();
        if (!studentService.isExistsByUsername(student.getUsername())) {
            ApplicationContext.getStudentService().saveOrUpdate(student);
            System.out.println("ثبت نام با موفقیت انجام شد . برای ورود می توانید با نام کاربری و رمز عبور زیر اقدام فرمایید.");
            System.out.println("نام کاربری :");
            System.out.println(student.getUsername());
            System.out.println("کلمه عبور :");
            System.out.println(student.getPassword());
        } else {
            System.out.println("شما قبلا ثبت نام کرده اید.لطفا با نام کاربری و رمز عبور خود وارد شوید");
        }
        publicMenu();
    }

    public Boolean isOpenGetLoan() {
        return (nowForExample.getMonthValue() == 8 && nowForExample.getDayOfMonth() <= 7)
                || (nowForExample.getMonthValue() == 10 && nowForExample.getDayOfMonth() >= 25)
                || nowForExample.getMonthValue() == 11 && nowForExample.getDayOfMonth() == 1;
    }


    public void signUpForLoan() {
        if (!isOpenGetLoan()) {
            System.out.println("خطا ! پنجره  ثبت نام وام در این تاریخ غیر فعال است");
            studentMenu();
        }
        if (isAfterGraduationDate()) {
            System.out.println("شما فارغ التحصیل شده اید");
            studentMenu();
        }
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
                System.out.println("ورودی نامعتبر");
                signUpForLoan();
            }

        }
        studentMenu();
    }

    public String getFourNumberFromInput() {
        String number;
        do {
            number = scanner.next();
            if (!Validation.isValidFourCardNumber(number))
                System.out.println("لطفا در وارد کردن اعداد دقت کنید");
        } while (!Validation.isValidFourCardNumber(number));
        return number;
    }

    public String getCvv2NumberFromInput() {
        String number;
        do {
            System.out.println("را وارد کنید cvv2 :");
            number = scanner.next();
        } while (!Validation.isValidCvv2Number(number));
        return number;
    }

    public String getExpireYearFromInput() {
        String number;
        do {
            System.out.println("سال را به صورت عدد دورقمی وارد کنید مثلا (01) :");
            number = scanner.next();
        } while (!Validation.isValidYearCard(number));
        return number;
    }

    public String getExpireMonthFromInput() {
        String number;
        do {
            System.out.println("ماه را به صورت عدد دورقمی وارد کنید مثلا (01) :");
            number = scanner.next();
        } while (!Validation.isValidMonthCard(number));
        return number;
    }

    public Card getCardFromInput() {
        String fourNumber1;
        String fourNumber2;
        String fourNumber3;
        String fourNumber4;
        String cvv2;
        String expireYear;
        String expireMonth;
        List<String> validCards = List.of("603799", "589463", "627353", "628023");
        do {
            do {
                System.out.println("چهار شماره اول را وارد کنید");
                System.out.println("(****) **** **** **** :");
                fourNumber1 = getFourNumberFromInput();
                System.out.println("چهار شماره دوم را وارد کنید");
                System.out.println("**** (****) **** **** :");
                fourNumber2 = getFourNumberFromInput();
                if (!validCards.contains(fourNumber1.concat(fourNumber2).substring(0, 6)))
                    System.out.println("کارت متعلق به بانک های ملی مسکن تجارت یا رفاه نیست لطفا مشخصات کارت مربوط به بانک های مذکور را وارد کنید ");
            } while (!validCards.contains(fourNumber1.concat(fourNumber2).substring(0, 6)));
            System.out.println("چهار شماره سوم را وارد کنید");
            System.out.println("**** **** (****) **** :");
            fourNumber3 = getFourNumberFromInput();
            System.out.println("چهار شماره آخر را وارد کنید");
            System.out.println(" **** **** **** (****) :");
            fourNumber4 = getFourNumberFromInput();
            cvv2 = getCvv2NumberFromInput();
            System.out.println("سال انقضای کارت را وارد کنید :");
            expireYear = getExpireYearFromInput();
            System.out.println("ماه انقضای کارت را وارد کنید :");
            expireMonth = getExpireMonthFromInput();
            if (PersianDate.of(Integer.parseInt("14".concat(expireYear)), Integer.parseInt(expireMonth), 1).toLocalDate()
                    .isBefore(nowForExample.toLocalDate())) {
                System.out.println("کارت شما منقضی شده است لطفا کارت دیگری وارد کنید");
            }
        } while (!PersianDate.of(Integer.parseInt(expireYear), Integer.parseInt(expireMonth), 1).toLocalDate()
                .isBefore(nowForExample.toLocalDate()));


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

    public void saveInstallment(Student student, Loan loan, Integer amount) {
        long[] installments = new long[60];
        int count;
        double amountDB = amount * 1.04;
        double instalmentPerYear = amountDB / 31;
        for (int i = 0; i < 5; i++) {
            count = (int) Math.pow(2, i);
            for (int j = 0; j < 12; j++) {
                installments[12 * i + j] = Math.round(count * instalmentPerYear / 12);

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
    }

    public void educationLoan() {
        int amount;
        Student student = studentService.findById(loggedInUserId);
        if (EduStages.level1.contains(student.getEduStage())) {
            amount = 1900000;
        } else if (EduStages.level2.contains(student.getEduStage())) {
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
                .currentPayLevel(1)
                .getLoanDate(getLoanDate)
                .card(card)
                .loanType(LoanTypes.EDUCATION)
                .isSettlement(Boolean.FALSE)
                .build();
        loanService.saveOrUpdate(loan);

        saveInstallment(student, loan, amount);
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
        Student student = studentService.findById(loggedInUserId);
        if (student.getIsMarried() == Boolean.FALSE) {
            System.out.println("این وام مخصوص افراد متاهل است لذا واجد شرایط دریافت این وام نیستید.");
            studentMenu();
        }
        if (student.getLiveInDorm() == Boolean.TRUE) {
            System.out.println("این وام به افراد خوابگاهی تعلق نمی گیرد");
            studentMenu();
        }
        MorInfoForHousingLoan morInfoForHousingLoan = getMorInfoForHousingLoan();
        if (loanService.wifeHousingLoanCheck(morInfoForHousingLoan.getWifeNationalCode())) {
            System.out.println("همسر شما قبلا از این وام استفاده کرده لذا شما واجد شرایط نمی باشید");
            studentMenu();
        }
        morInfoForHousingLoanService.saveOrUpdate(morInfoForHousingLoan);
        Card card = getCardFromInput();
        caredService.saveOrUpdate(card);

        int amount;


        City city = getCityFromInput();
        if (City.level1.contains(city)) {
            amount = 32000000;
        } else if (City.level2.contains(city)) {
            amount = 26000000;
        } else {
            amount = 19500000;
        }

        Loan loan = Loan.builder()
                .student(student)
                .studentStage(student.getEduStage())
                .amount(amount)
                .getLoanDate(nowForExample.toLocalDate())
                .getLoanTerm(student.getCurrentTerm())
                .studentCity(city)
                .currentPayLevel(1)
                .card(card)
                .morInfoForHousingLoan(morInfoForHousingLoan)
                .isSettlement(Boolean.FALSE)
                .loanType(LoanTypes.HOUSING)
                .build();
        loanService.saveOrUpdate(loan);
        saveInstallment(student, loan, amount);
        studentMenu();
    }

    public City getCityFromInput() {
        System.out.println("شهر محل زندگی خود را وارد کنید");
        System.out.println("""
                1-TEHRAN
                2-RASHT
                3-ESFAHAN
                4-TABRIZ
                5-SHIRAZ
                6-AHWAZ
                7-QOM
                8-MASHHAD
                9-KARAJ
                10-Other""");
        City city = null;
        do {
            int input = getIntFromUser();
            switch (input) {
                case 1 -> city = City.TEHRAN;
                case 2 -> city = City.RASHT;
                case 3 -> city = City.ESFAHAN;
                case 4 -> city = City.TABRIZ;
                case 5 -> city = City.SHIRAZ;
                case 6 -> city = City.AHWAZ;
                case 7 -> city = City.QOM;
                case 8 -> city = City.MASHHAD;
                case 9 -> city = City.KARAJ;
                case 10 -> city = City.Other;
                default -> System.out.println("ورودی نامعتبر");
            }
        } while (city == null);
        return city;
    }

    public void tuitionLoan() {
        Card card = getCardFromInput();
        caredService.saveOrUpdate(card);

        int amount;
        Student student = studentService.findById(loggedInUserId);
        if (EduStages.level1.contains(student.getEduStage())) {
            amount = 1300000;
        } else if (EduStages.level2.contains(student.getEduStage())) {
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
                .currentPayLevel(1)
                .isSettlement(Boolean.FALSE)
                .loanType(LoanTypes.TUITION)
                .card(card)
                .build();
        loanService.saveOrUpdate(loan);
        saveInstallment(student, loan, amount);
        studentMenu();
    }

    public void calculateGraduationDate(Student student) {
        LocalDate inputDate;
        if (student.getAppliedTerm().equals(AppliedTerm.MEHR)) {
            inputDate = LocalDate.of(student.getAppliedYear(), 7, 1);
        } else {
            inputDate = LocalDate.of(student.getAppliedYear(), 10, 1);
        }
        if (student.getEduStage().equals(EduStages.KARDANI) || student.getEduStage()
                .equals(EduStages.KARSHENASI_ARSHAD_NAPEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(2));
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_PEYVASTEH) || student.getEduStage()
                .equals(EduStages.KARSHENASI_NAPEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(4));
        } else if (student.getEduStage().equals(EduStages.KARSHENASI_ARSHAD_PEYVASTEH)) {
            student.setExpireDate(inputDate.plusYears(6));
        } else {
            student.setExpireDate(inputDate.plusYears(5));
        }
    }

    public Boolean isAfterGraduationDate() {
        LocalDate localDate = studentService.findById(loggedInUserId).getExpireDate();
        PersianDate nowPersianDate = PersianDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        return nowForExample.toLocalDate().isAfter(nowPersianDate.toLocalDate());
    }

    public void seeAndPeyInstallments() {
        if (!isAfterGraduationDate()) {
            System.out.println("بازپرداخت برای شما فعال نشده است . ");
            studentMenu();
        }
        System.out.println("برای مشاهده و پرداخت اقساط، وام مورد نظر خود را از لیست زیر انتخاب فرمائید :");
        List<Loan> loansByStudentId = loanService.findLoansByStudentId(loggedInUserId.intValue());
        if (loansByStudentId.isEmpty()) {
            System.out.println("هیچ وامی دریافت نکرده اید");
            studentMenu();
        }
        System.out.println("تاریخ دریافت وام -ترم دریافت وام-مبلغ وام-نوع وام-آی دی");
        for (Loan l : loansByStudentId) {
            System.out.print(l.getId() + "-" + l.getLoanType() + " " + l.getAmount() + " " + l.getGetLoanTerm()
                    + " " + PersianDate.ofLocalDate(l.getGetLoanDate()));
            System.out.println();
        }
        System.out.println("لطفا فقط شماره وام را وارد کنید");
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
                System.out.println("ورودی نامعتبر");
                seeAndPeyInstallments();
            }

        }
        seeAndPeyInstallments();
    }

    public void seeNotPayedInstallments(Integer loanId) {
        List<Installment> notPayedByLoanId = installmentService.findNotPayedByLoanId(loanId);
        if (notPayedByLoanId.isEmpty()) {
            System.out.println("وامی با این شماره یافت نشد لطفا در وارد کردن عدد دقت فرمایید");
            seeAndPeyInstallments();
        }
        System.out.println(" تاریخ سررسید - مبلغ قسط - مرحله پرداخت -شماره اقساط");
        for (Installment i : notPayedByLoanId) {
            System.out.println(i.getId() + " - " + i.getPaymentStageNum() + " - " + i.getAmount() + " - " + i.getDueDate());
        }
    }

    public void seePayedInstallments(Integer loanId) {
        List<Installment> payedByLoanId = installmentService.findPayedByLoanId(loanId);
        if (payedByLoanId.isEmpty()) {
            System.out.println("قسط پرداخت شده ای با این شماره وام یافت نشد لطفا درصورت اطمینان از پرداخت، شماره وام وارد شده را چک کنید");
            seeAndPeyInstallments();
        }
        for (Installment i : payedByLoanId) {
            System.out.println(i.getPaymentStageNum() + " - " + i.getPayDate());
        }

    }

    public void payInstallment(Integer loanId) {
        seeNotPayedInstallments(loanId);
        try {
            Installment installment = installmentService.findById(Long.valueOf(loanService.findById(Long.valueOf(loanId.toString()))
                    .getCurrentPayLevel()));
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
                Loan loan=loanService.findById(Long.valueOf(loanId.toString()));
                loan.setCurrentPayLevel(
                        loanService.findById(Long.valueOf(loanId.toString())).getCurrentPayLevel() + 1);
                loanService.saveOrUpdate(loan);
                studentMenu();
            } else {
                System.out.println("شماره کارت یا اطلاعات آن نامعتبر است لطفا کارت مربوط به این وام را به دقت وارد کنید");
                seeAndPeyInstallments();
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}

