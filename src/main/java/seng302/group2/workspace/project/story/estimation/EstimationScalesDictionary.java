package seng302.group2.workspace.project.story.estimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class containing a dictionary mapping the name of a story estimation scale to an
 * array of string representing the estimation values. Contains a method for creating new estimation
 * scales to use alongside the three default scales.
 * Created by drm127 and btm38 on 29/06/15.
 */
public class EstimationScalesDictionary {

    private static EstimationScalesDictionary scales;
    private Map<String, ArrayList<String>> estimationScaleDict = new HashMap<>();

    private static Map<DefaultValues, String> defaultValuesDict = new HashMap<>();

    public enum DefaultValues {
        NONE,
        ZERO,
        INFINITE,
        QUESTION
    }

    public static String infSymbol = "\u221E";


    /**
     * Initialises the default values dictionary for use in all estimation scales
     */
    static {
        defaultValuesDict = new HashMap<>();
        defaultValuesDict.put(DefaultValues.NONE, "-");
        defaultValuesDict.put(DefaultValues.ZERO, "0");
        defaultValuesDict.put(DefaultValues.INFINITE, infSymbol);
        defaultValuesDict.put(DefaultValues.QUESTION, "?");
    }


    /**
     * Looks up the default values dictionary to find the string value for the given default scale enum
     * @param value the value to return the string of
     * @return the string value for the given default scale enum
     */
    public static String getScaleValue(DefaultValues value) {
        return defaultValuesDict.get(value);
    }


    /**
     * Creates the default scales for use in the application
     */
    private void createDefaultScales() {
        ArrayList<String> valueList = new ArrayList<>(Arrays.asList("0.5", "1", "2", "3", "5", "8", "13", "20",
                "40", "100"));
        createScales("Fibonacci", valueList);

        valueList = new ArrayList<>(Arrays.asList("Chihuahua", "Pug", "Bulldog", "Shiba Inu",
                "Border Collie", "Siberian Husky", "German Shepherd", "Great Dane"));
        createScales("Dog Breeds", valueList);

        valueList = new ArrayList<>(Arrays.asList("2XS", "XS", "S", "M", "L", "XL", "2XL", "3XL", "4XL"));
        createScales("T-Shirt Sizes", valueList);
    }


    /**
     * The private constructor for the estimationScalesDictionary class. Is only accessible by itself,
     * to allow conformance to the singleton pattern.
     */
    private EstimationScalesDictionary() {
        createDefaultScales();
    }


    /**
     * The publicly accessible method for addition of the estimation scale dictionary to a workspace.
     *
     * @return The single instance of the estimationScalesDictionary.
     */
    public static EstimationScalesDictionary getEstimationScale() {
        if (scales == null) {
            scales = new EstimationScalesDictionary();
        }
        return scales;
    }

    /**
     * Returns a type of Map%lt;String%gt;%lt;String%gt;, ArrayList%lt;String%gt; which contains all the various estimation scales.
     *
     * @return estimation scale dictionary
     */
    public Map<String, ArrayList<String>> getEstimationScaleDict() {
        return this.estimationScaleDict;
    }

    /**
     * A method for the creation of new estimation scales, and their addition to the dictionary.
     * @param scaleName The name of the new scale.
     * @param scaleValues An arraylist of strings representing the values to be used in the new scale.
     */
    public void createScales(String scaleName, ArrayList<String> scaleValues) {
        scaleValues.add(0, defaultValuesDict.get(DefaultValues.NONE));
        scaleValues.add(1, defaultValuesDict.get(DefaultValues.ZERO));
        scaleValues.add(defaultValuesDict.get(DefaultValues.INFINITE));
        scaleValues.add(defaultValuesDict.get(DefaultValues.QUESTION));

        estimationScaleDict.put(scaleName, scaleValues);
    }

}
