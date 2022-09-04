import java.util.EventListener;
/*Tom Kondat 318275591
David Sharabi 315313981
*/
public interface CarListener extends EventListener{

	public void workComplete(CarEvent event);
	public void wash(CarEvent event);

}
