package BE.senechal.ski;

public class Skier extends Person {
    private int skierId;


    public Skier(int skierId, String firstName, String lastName) {
        super(skierId, firstName, lastName);
        this.skierId = skierId;
    }


    public int getSkierId() {
        return skierId;
    }

    public void setSkierId(int skierId) {
        this.skierId = skierId;
    }

    @Override
    public String toString() {
        return "Skier{" +
                "skierId=" + skierId +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                '}';
    }
}
