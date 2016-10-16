package bg.piggybank.model;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {

    private String str;
    private int randInt;
    private StringBuilder builder;
    private List<Integer> list;

    public PasswordGenerator() {
        this.list = new ArrayList<>();
        this.builder = new StringBuilder();
        buildPassword();
    }

    private void buildPassword() {

        //Add ASCII numbers of characters commonly acceptable in passwords
        for (int i = 33; i < 127; i++) {
            list.add(i);
        }

        //Remove characters /, \, and " as they're not commonly accepted
        list.remove(new Integer(34));
        list.remove(new Integer(47));
        list.remove(new Integer(92));

        /*Randomise over the ASCII numbers and append respective character
          values into a StringBuilder*/
        for (int i = 0; i < 10; i++) {
            randInt = list.get(new SecureRandom().nextInt(91));
            builder.append((char) randInt);
        }

        str = builder.toString();
    }

    public String generatePassword() {
        return str;
    }
}
