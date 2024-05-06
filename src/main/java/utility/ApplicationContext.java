package utility;


import Model.MorInfoForHousingLoan;
import repository.card.CardRepositoryImpl;
import repository.installment.InstallmentRepositoryImpl;
import repository.loan.LoanRepositoryImpl;
//import repository.moreinfo.MorInfoForHousingLoanRepositoryImpl;
import repository.morInfo.MorInfoForHousingLoanRepositoryImpl;
import repository.student.StudentRepositoryImpl;
import connection.SessionFactorySingleton;
import org.hibernate.SessionFactory;

import service.card.CardServiceImpl;
import service.installment.InstallmentServiceImpl;
import service.loan.LoanServiceImpl;

import service.morInfo.MorInfoForHousingLoanServiceImpl;
import service.student.StudentServiceImpl;

public class ApplicationContext {
    static final SessionFactory SESSION_FACTORY;
//////    static final Session SESSION;
    private static final StudentRepositoryImpl STUDENT_REPOSITORY;
    private static final StudentServiceImpl STUDENT_SERVICE;
    private static final CardRepositoryImpl CARD_REPOSITORY;
    private static final CardServiceImpl CARD_SERVICE;
    private static final InstallmentRepositoryImpl INSTALLMENT_REPOSITORY;
    private static final InstallmentServiceImpl INSTALLMENT_SERVICE;
    private static final LoanRepositoryImpl LOAN_REPOSITORY;
    private static final LoanServiceImpl LOAN_SERVICE;
    private static final MorInfoForHousingLoanRepositoryImpl MOR_INFO_FOR_HOUSING_LOAN_REPOSITORY;
    private static final MorInfoForHousingLoanServiceImpl MOR_INFO_FOR_HOUSING_LOAN_SERVICE;

//
//
    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();
        STUDENT_REPOSITORY = new StudentRepositoryImpl(SESSION_FACTORY);
        STUDENT_SERVICE = new StudentServiceImpl(STUDENT_REPOSITORY,SESSION_FACTORY);
        CARD_REPOSITORY = new CardRepositoryImpl(SESSION_FACTORY);
        CARD_SERVICE = new CardServiceImpl(CARD_REPOSITORY,SESSION_FACTORY);
        INSTALLMENT_REPOSITORY = new InstallmentRepositoryImpl(SESSION_FACTORY);
        INSTALLMENT_SERVICE = new InstallmentServiceImpl(INSTALLMENT_REPOSITORY,SESSION_FACTORY);
        LOAN_REPOSITORY = new LoanRepositoryImpl(SESSION_FACTORY);
        LOAN_SERVICE = new LoanServiceImpl(LOAN_REPOSITORY,SESSION_FACTORY);
        MOR_INFO_FOR_HOUSING_LOAN_REPOSITORY=new MorInfoForHousingLoanRepositoryImpl(SESSION_FACTORY);
        MOR_INFO_FOR_HOUSING_LOAN_SERVICE=new MorInfoForHousingLoanServiceImpl(MOR_INFO_FOR_HOUSING_LOAN_REPOSITORY,SESSION_FACTORY);

    }
//
    public static StudentServiceImpl getStudentService() {
        return STUDENT_SERVICE;
    }
//
    public static CardServiceImpl getCardService() {
        return CARD_SERVICE;
    }
//
    public static InstallmentServiceImpl getInstallmentService() {
        return INSTALLMENT_SERVICE;
    }
//
    public static LoanServiceImpl getLoanService() {
        return LOAN_SERVICE;
    }

    public static MorInfoForHousingLoanServiceImpl getMorInfoForHousingLoanService(){
        return MOR_INFO_FOR_HOUSING_LOAN_SERVICE;
    }

}
