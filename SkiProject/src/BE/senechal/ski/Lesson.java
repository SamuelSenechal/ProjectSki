package BE.senechal.ski;

import java.util.Date;

public class Lesson {
    private int id;
    private int lessonTypeId;
    private int instructorId;
    private int minBooking;
    private int maxBooking;
    private boolean isMorning; // 1 = matin, 0 = après-midi
    private Date startDate;

    public Lesson(int id, int lessonTypeId, int instructorId, int minBooking, int maxBooking, boolean isMorning, Date startDate) {
        this.id = id;
        this.lessonTypeId = lessonTypeId;
        this.instructorId = instructorId;
        this.minBooking = minBooking;
        this.maxBooking = maxBooking;
        this.isMorning = isMorning;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonTypeId() {
        return lessonTypeId;
    }

    public void setLessonTypeId(int lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getMinBooking() {
        return minBooking;
    }

    public void setMinBooking(int minBooking) {
        this.minBooking = minBooking;
    }

    public int getMaxBooking() {
        return maxBooking;
    }

    public void setMaxBooking(int maxBooking) {
        this.maxBooking = maxBooking;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(boolean isMorning) {
        this.isMorning = isMorning;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
