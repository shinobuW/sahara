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
public class estimationScalesDictionary {

    private static estimationScalesDictionary scales;
    private Map<String, ArrayList<String>> estimationScaleDict = new HashMap<>();

    /**
     * The private constructor for the estimationScalesDictionary class. Is only accessible by itself,
     * to allow conformance to the singleton pattern.
     */
    private estimationScalesDictionary() {
        ArrayList<String> valueList = new ArrayList<>(Arrays.asList("0.5", "1", "2", "3", "5", "8", "13", "20",
                "40", "100"));
        createScales("Fibonacci", valueList);
        valueList = new ArrayList<>(Arrays.asList("Chihuahua", "Pug", "Bulldog", "Shiba Inu", "Border Collie",
                "Border Collie", "Siberian Husky", "German Shepherd", "Great Dane"));
        createScales("Dog Breeds", valueList);
        valueList = new ArrayList<>(Arrays.asList("2XS", "XS", "S", "M", "L", "XL", "2XL", "3XL", "4XL"));
        createScales("T-Shirt Sizes", valueList);
    }

    /**
     * The publicly accessible method for addition of the estimation scale dictionary to a workspace.
     * @return The single instance of the estimationScalesDictionary.
     */
    public static estimationScalesDictionary addScales() {
        if (scales == null) {
            scales = new estimationScalesDictionary();
        }
        return scales;
    }

    /**
     * A method for the creation of new estimation scales, and their addition to the dictionary.
     * @param scaleName The name of the new scale.
     * @param scaleValues An arraylist of strings representing the values to be used in the new scale.
     */
    public void createScales(String scaleName, ArrayList<String> scaleValues) {
        scaleValues.add(0, "0");
        scaleValues.add("inf");
        scaleValues.add("?");

        System.out.println(scaleValues);
        estimationScaleDict.put(scaleName, scaleValues);
    }

}
