import java.util.EventListener;

public interface CarListener extends EventListener{

	public void workComplete(CarEvent event);
	public void wash(CarEvent event);

}
