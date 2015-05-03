package seng302.group2.util;

/**
 * Small helper class to give string slicing functionality.
 * Created by btm38 on 3/05/15.
 */
public class StringSlice {

    /**
     * Given a sting s, slices s up to the given startIndex
     * @param s
     * @param startIndex
     * @return the sliced string
     */
    public static String slice_start(String s, int startIndex)
    {
        if (startIndex < 0) startIndex = s.length() + startIndex;
        return s.substring(startIndex);
    }


    /**
     *
     * @param s
     * @param endIndex
     * @return
     */
    public static String slice_end(String s, int endIndex)
    {
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(0, endIndex);
    }


    /**
     *
     * @param s
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String slice_range(String s, int startIndex, int endIndex)
    {
        if (startIndex < 0) startIndex = s.length() + startIndex;
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(startIndex, endIndex);
    }
}
