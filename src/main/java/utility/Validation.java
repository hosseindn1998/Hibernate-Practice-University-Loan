package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Validation {


    public static boolean isValidUsername(String username) {

        String regex = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$";

        Pattern pattern = Pattern.compile(regex);
        if (username == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }


    public static boolean isValidIranianNationalCode(String input) {
        if (!input.matches("^\\d{10}$"))
            return false;

        int check = Integer.parseInt(input.substring(9, 10));

        int sum = IntStream.range(0, 9)
                .map(x -> Integer.parseInt(input.substring(x, x + 1)) * (10 - x))
                .sum() % 11;

        return sum < 2 ? check == sum : check + sum == 11;
    }


    public static boolean isValidName(String name) {

        String regex = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$";

        Pattern pattern = Pattern.compile(regex);
        if (name == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    public static boolean isValidBirthCertificateNumber(String birthCertificateNumber) {

        String regex = "^[0-9]{1,5}$";

        Pattern pattern = Pattern.compile(regex);
        if (birthCertificateNumber == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(birthCertificateNumber);

        return matcher.matches();
    }
    public static boolean isValidFourCardNumber(String Number) {

        String regex = "^[0-9]{4}$";

        Pattern pattern = Pattern.compile(regex);
        if (Number == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(Number);

        return matcher.matches();
    }
    public static boolean isValidCvv2Number(String Cvv2Number) {

        String regex = "^[0-9]{3,4}$";

        Pattern pattern = Pattern.compile(regex);
        if (Cvv2Number == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(Cvv2Number);

        return matcher.matches();
    }

    public static boolean isValidDate(String date) {

        String regex = "^[1-4]\\d{3}-((0[1-6]-((3[0-1])|([1-2][0-9])|(0[1-9])))|((1[0-2]|(0[7-9]))-(30|([1-2][0-9])|(0[1-9]))))$";


        Pattern pattern = Pattern.compile(regex);
        if (date == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    public static boolean isValidStudentCode(String studentCode) {

        String regex = "^\\d{10}$";


        Pattern pattern = Pattern.compile(regex);
        if (studentCode == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(studentCode);

        return matcher.matches();
    }
    public static boolean isValidYearCard(String number) {

        String regex = "^\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        if (number == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }
    public static boolean isValidMonthCard(String number) {

        String regex = "^\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        if (number == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(number);
        if(matcher.matches()){
            return Integer.parseInt(number) >= 1 && Integer.parseInt(number) <= 12;
        }
        return matcher.matches();
    }

}


