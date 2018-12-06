package ha.thanh.oxfordmodule.requests;

public class LengthFilter {

    private static Integer min = null;
    private static Integer max = null;
    private static Integer value = null;

    private LengthFilter(Integer min, Integer max, Integer value) {
        LengthFilter.min = min;
        LengthFilter.max = max;
        LengthFilter.value = value;
    }

    public static LengthFilter atLeast(int min) {
        if (min < 1)
            throw new IllegalArgumentException("Minimum word length cannot be less than 1.");
        LengthFilter.min = min;
        LengthFilter.max = null;
        LengthFilter.value = null;
        return new LengthFilter(min, max, value);
    }

    public static LengthFilter atMost(int max) {
        LengthFilter.min = null;
        LengthFilter.max = max;
        LengthFilter.value = null;
        return new LengthFilter(min, max, value);
    }

    public static LengthFilter range(int min, int max) {
        if (max < min)
            throw new IllegalArgumentException("The maximum word length cannot be smaller than the minimum word length.");
        LengthFilter.min = min;
        LengthFilter.max = max;
        LengthFilter.value = null;
        return new LengthFilter(min, max, value);
    }

    public static LengthFilter exact(int value) {
        LengthFilter.min = null;
        LengthFilter.max = null;
        LengthFilter.value = value;
        return new LengthFilter(min, max, value);
    }

    public static LengthFilter setNoLengthFilter() {
        LengthFilter.min = null;
        LengthFilter.max = null;
        LengthFilter.value = null;
        return new LengthFilter(null, null, null);
    }

    @Override
    public String toString() {
        if (min == null && max == null && value == null)
            return "";
        return value == null ? ((min != null && max != null) ? ("word_length=>" + min + ",<" + max) : ((min == null) ? ("word_length=<" + max) : ("word_length=>" +
                min))) : "word_length=" + value;
    }

}