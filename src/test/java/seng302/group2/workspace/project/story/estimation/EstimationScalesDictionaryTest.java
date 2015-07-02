package seng302.group2.workspace.project.story.estimation;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EstimationScalesDictionaryTest {
    EstimationScalesDictionary testDict = EstimationScalesDictionary.getEstimationScale();
    EstimationScalesDictionary testDict2 = EstimationScalesDictionary.getEstimationScale();
    Map<String, ArrayList<String>> estimationScaleDict = testDict.getEstimationScaleDict();

    @Test
    public void testGetEstimationScale() throws Exception {
        Assert.assertEquals(testDict, testDict2);
    }

    @Test
    public void testGetEstimationScaleDict() throws Exception {
        Map<String, ArrayList<String>> testEstimationScaleDict = testDict2.getEstimationScaleDict();

        Map<String, ArrayList<String>> compareEstimationScaleDict = new HashMap<>();

        ArrayList<String> valueList = new ArrayList<>(Arrays.asList("0", "0.5", "1", "2", "3", "5", "8", "13", "20",
                "40", "100", "inf", "?"));
        compareEstimationScaleDict.put("Fibonacci", valueList);

        valueList = new ArrayList<>(Arrays.asList("0", "Chihuahua", "Pug", "Bulldog", "Shiba Inu", "Border Collie",
                "Border Collie", "Siberian Husky", "German Shepherd", "Great Dane", "inf", "?"));
        compareEstimationScaleDict.put("Dog Breeds", valueList);

        valueList = new ArrayList<>(Arrays.asList("0", "2XS", "XS", "S", "M", "L", "XL",
                "2XL", "3XL", "4XL", "inf", "?"));
        compareEstimationScaleDict.put("T-Shirt Sizes", valueList);

        valueList = new ArrayList<>(Arrays.asList("0", "A", "B", "C", "D", "E", "F", "G", "H", "inf", "?"));
        compareEstimationScaleDict.put("Alphabet", valueList);

        Assert.assertEquals(testEstimationScaleDict, compareEstimationScaleDict);

    }

    @Test
    public void testCreateScales() throws Exception {
        ArrayList<String> AlphabetList = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"));
        testDict.createScales("Alphabet", AlphabetList);


        Assert.assertEquals(estimationScaleDict.get("Alphabet"), AlphabetList);
    }
}