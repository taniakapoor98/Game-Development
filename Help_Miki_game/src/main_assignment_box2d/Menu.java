package main_assignment_box2d;

import java.io.IOException;
import java.awt.Rectangle;
public class Menu extends MainEngine{
	public static Rectangle playButton,quitButton ;
	public static boolean playRegion = false;
	public static boolean quitRegion = false;

	public Menu() throws IOException {
		super();
		playButton = new Rectangle(WIDTH/2+420,500,130,60);
		quitButton = new Rectangle(WIDTH/2+420,600,130,60);

	}
	

}
