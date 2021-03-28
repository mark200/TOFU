package nl.tudelft.oopp.group54.entities;

public enum LectureFeedbackCode {
    LECTURE_IS_TOO_FAST(1), LECTURE_IS_TOO_SLOW(2);

    private final int value;

    private LectureFeedbackCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
