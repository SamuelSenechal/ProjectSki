package BE.senechal.ski;

public class Accreditation {
	private int id;
    private String name;

    public Accreditation(int id, String name) {
    	this.id = id;
    	this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public int getId() {
		return id;
	}

	public void setId(int int1) {
		this.id = int1;
		
	}
}
