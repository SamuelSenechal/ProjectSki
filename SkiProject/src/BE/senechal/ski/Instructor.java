package BE.senechal.ski;

import java.util.List;

public class Instructor extends Person {
    private List<Accreditation> accreditations;

    public Instructor(int id, String firstName, String lastName, List<Accreditation> accreditations) {
        super(id, firstName, lastName);
        this.accreditations = accreditations;
    }

    public List<Accreditation> getAccreditations() {
        return accreditations;
    }

    public void setAccreditations(List<Accreditation> accreditations) {
        this.accreditations = accreditations;
    }

    public boolean isAccredited(String lessonType) {
        return accreditations.stream().anyMatch(acc -> acc.getName().equals(lessonType));
    }
}

