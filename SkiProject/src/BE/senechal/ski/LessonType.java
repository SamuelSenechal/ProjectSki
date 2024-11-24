package BE.senechal.ski;

public class LessonType {
    private int id;
    private String name;
    private double price;
    private int accreditationId;
    private String category;
    private String sportType;
    

    public LessonType(int id, String name, double price, int accreditationId, String category, String sportType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.accreditationId = accreditationId;
        this.category = category;
        this.sportType = sportType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (€" + price + ")";
    }

	public int getAccreditationId() {
		return accreditationId;
	}

	public String getSportType() {
		return sportType;
	}

	public Object getCategory() {
		return category;
	}
}
