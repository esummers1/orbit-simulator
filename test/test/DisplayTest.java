package test;

import main.Display;
import org.junit.jupiter.api.Test;

public class DisplayTest {

    @Test
    public void testTrimStringToLength_ShortString() {

        // GIVEN a String of length 5
        String string = "Hello";

        // WHEN I trim it to length 40
        string = Display.trimStringToLength(string, 40);

        // THEN I receive the original String
        assert(string.equals("Hello"));
    }

    @Test
    public void testTrimStringToLength_LongString() {

        // GIVEN a String of length 41
        String string = "Hello this is a test String ending now!!!";

        // WHEN I trim it to length 40
        string = Display.trimStringToLength(string, 40);

        // THEN I receive the first 37 characters of the String and three dots
        assert(string.equals("Hello this is a test String ending no..."));
    }

    @Test
    public void testDisplayDoubleAsScientific_LargeNumber() {

        // GIVEN a double of 3551574890
        double number = 3551574890d;

        // WHEN I render this number in scientific notation with two D.P.
        String scientificNumber = Display.displayDoubleAsScientific(number, 2);

        // THEN I receive a String of "3.55 x 10^9"
        assert(scientificNumber.equals("3.55 x 10^9"));
    }

    @Test
    public void testDisplayDoubleAsScientific_TinyNumber() {

        // GIVEN a double of 0.0004534
        double number = 0.0004534d;

        // WHEN I render this number in scientific notation with two D.P.
        String scientificNumber = Display.displayDoubleAsScientific(number, 2);

        // THEN I receive a String of "4.53 x 10^-4"
        assert(scientificNumber.equals("4.53 x 10^-4"));
    }

}
