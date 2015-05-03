package seng302.group2.util;

/**
 * Small helper class to give string slicing functionality.
 * Created by btm38 on 3/05/15.
 */
public class StringSlice
{
    /**
     * Given a string, slices the string from the given startIndex.
     * @param string the string to be sliced.
     * @param startIndex int start slice index (inclusive).
     * @return the sliced string.
     */
    public static String slice_start(String string, int startIndex)
    {
        if (startIndex < 0)
        {
            startIndex = string.length() + startIndex;
        }
        return string.substring(startIndex);
    }


    /**
     * Given a string, slices the string up to the given endIndex.
     * @param string the string to be sliced.
     * @param endIndex int end slice index (exclusive).
     * @return the sliced string.
     */
    public static String slice_end(String string, int endIndex)
    {
        if (endIndex < 0)
        {
            endIndex = string.length() + endIndex;
        }
        return string.substring(0, endIndex);
    }


    /**
     * Given a string, slices the string from the startIndex to the endIndex.
     * @param string the string to be sliced.
     * @param startIndex int start slice index (inclusive).
     * @param endIndex int end slice index (exclusive).
     * @return the sliced string.
     */
    public static String slice_range(String string, int startIndex, int endIndex)
    {
        if (startIndex < 0)
        {
            startIndex = string.length() + startIndex;
        }
        if (endIndex < 0)
        {
            endIndex = string.length() + endIndex;
        }
        return string.substring(startIndex, endIndex);
    }
}
