package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(String email) {

        String regex = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";;

        Pattern pattern = Pattern.compile(regex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassword(String password) {

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

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
    public static boolean isValidphoneNumber(String username) {

        String regex = "^[1-9]\\d{2}-\\d{3}-\\d{4}\n" +
                "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}\n" +
                "^[1-9]\\d{2}\\s\\d{3}\\s\\d{4}\n" +
                "^[1-9]\\d{2}\\.\\d{3}\\.\\d{4}";

        Pattern pattern = Pattern.compile(regex);
        if (username == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
    public static boolean isValidNationalCode(String username) {

        String regex = "[0-9]{10}";

        Pattern pattern = Pattern.compile(regex);
        if (username == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
}
