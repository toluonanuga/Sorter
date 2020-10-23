
/**
 * AppDriver class -	This class runs SaitMLS project. It does so by instantiating MainWindow and calling createMenuAndWindow()
 * 
 * @author 729380
 * @version 1
 */
public class AppDriver
{
	public static void main(String[] args)
	{
		MainWindow mainWind = new MainWindow();
		mainWind.createMenuAndWindow();
	}
}
