package nl.tudelft.oopp.group54.util;

import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;

public class TextFormatterFactory {

    private static Pattern createPattern(TextFormatterType tft) {
        switch (tft) {
            case JOIN_ID:
                return Pattern.compile(".{0,40}");
            case TIME:
                return Pattern.compile("\\d{0,2}:?\\d{0,2}");
            case NAME:
                // It turns out that people can name themselves literally anything.
                //  To wit, this regex shouldn't exist. We could still use it
                //  to limit the size of the input at least. If your name is longer
                //  50 character, then use a nickname or something.
                //return Pattern.compile("^[a-zA-Z\\s]*");
                return Pattern.compile(".{0,50}");
            default:
        }
        throw new IllegalArgumentException("Unrecognized pattern given");
    }

    /**
     * Creates formatter.
     * @param tft a textformattertype
     * @return a textformatter object
     */
    public static TextFormatter<?> createFormatter(TextFormatterType tft) {
        return new TextFormatter<>(change -> {
            if (createPattern(tft).matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
    }

}
