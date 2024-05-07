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

    public static boolean isValidWebsite(String website) {

        String regex = "/((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)/\n";


        Pattern pattern = Pattern.compile(regex);
        if (website == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(website);

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

    public static boolean isValidphoneNumber(String phoneNumber) {

        String regex = "^[1-9]\\d{2}-\\d{3}-\\d{4}\n" +
                "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}\n" +
                "^[1-9]\\d{2}\\s\\d{3}\\s\\d{4}\n" +
                "^[1-9]\\d{2}\\.\\d{3}\\.\\d{4}";

        Pattern pattern = Pattern.compile(regex);
        if (phoneNumber == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
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

    }

//            (0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])


