import java.io.Serializable;

public class Customer implements Serializable{

	private String name;
	private String cellphoneNumber;
	
	public Customer(String name, String cellphoneNumber) {
		this.name = name;
		this.cellphoneNumber = cellphoneNumber;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + ", " + cellphoneNumber;
	}

}
