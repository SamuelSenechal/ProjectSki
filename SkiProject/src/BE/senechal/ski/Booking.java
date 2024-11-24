package BE.senechal.ski;

public class Booking {
    private Skier skier;
    private Lesson lesson;
    private boolean insurance;

    public Booking(Skier skier, Lesson lesson, boolean insurance) {
        this.skier = skier;
        this.lesson = lesson;
        this.insurance = insurance;
    }

    public Skier getSkier() {
        return skier;
    }

    public void setSkier(Skier skier) {
        this.skier = skier;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public boolean hasInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "skier=" + skier +
                ", lesson=" + lesson +
                ", insurance=" + insurance +
                '}';
    }
}

