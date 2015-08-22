package seng302.group2.util.conversion;

import javafx.scene.paint.Color;

/**
 * Created by Jordane on 22/08/2015.
 */
public class ColorUtils {
    /**
     * Converts a JavaFX colour into an html/web colour string (including opacity)
     * @param color The colour to convert
     * @return The web equivalent of the colour
     */
    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255),
                (int)(color.getOpacity() * 255));
    }
}
