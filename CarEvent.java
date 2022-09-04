import java.util.EventObject;
/*Tom Kondat 318275591
David Sharabi 315313981
*/
public class CarEvent extends EventObject {


	private Data data;
	private int id;
	private int carId;

	//Contractor
	public CarEvent (Object source, int carId) {
		super(source);
		this.carId = carId;
		this.id = 1;

	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
