package main_assignment_box2d;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import org.jbox2d.common.Vec2;



import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class mouse_listener2  implements MouseListener , MouseMotionListener {

	private static int startx, starty, endx,endy;
	private static int mx, my;

	private static boolean mouseButtonPressed = false, mouseButtonReleased = false;
	private static MainEngine game;
	private static BasicView_L3 view;
	
	
	public mouse_listener2(MainEngine game,BasicView_L3 view){
		this.game = game;
		this.view = view;


		
	}


	public static boolean isMouseButtonPressed() {
		return mouseButtonPressed;
	}
	public static Vec2 getWorldCoordinatesOfMousePointer() {
		return new Vec2(MainEngine.convertScreenXtoWorldX(endx), MainEngine.convertScreenYtoWorldY(endy));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		mouseButtonPressed=true;
		setMouseButtonReleased(false);
		if(Menu.quitRegion ||
				Menu.playRegion ||
			Mission.playRegion ||
			game.contRegion ||
			game.backRegion ) {
				AudioPlayer cheer = new AudioPlayer();
	    		cheer.playSound("click.wav");
	    		
			}
		
		if(game.layout==MainEngine.LayoutMode.MENU) {
// handling button clicks
			if(mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+550) {
				
				if(my >= 500 && my <= 560 && MainEngine.getMission_cnt()<=4){
					MainEngine.layout = MainEngine.LayoutMode.LEVEL1;
					((Menu)game).goToMission();

				}
				if(my >= 600 && my <= 660){
					System.exit(0);
				}
			}
			
		}

		else if(game.layout==MainEngine.LayoutMode.MISSION) {

			if(mx >= MainEngine.WIDTH/4+680 && mx <= MainEngine.WIDTH/4+810) {
				
				if(my >= 800 && my <= 860){
					((Mission)game).goToLevel();

				}
				
			}
			
		}
		else {

			if(game.success) {
				if(mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+580) {
					
					if(my >= 500 && my <= 560  && MainEngine.getMission_cnt()<=4){
						MainEngine.goToMission();

					}
					
				}
			}
			if(game.isGameOver) {
				if(mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+580) {
					
					if(my >= 500 && my <= 560){
						MainEngine.goToMenu();

					}
					
				}
			}
			
		}
		
		if(game.layout == MainEngine.LayoutMode.LEVEL2) {
		this.startx =  game.convertWorldXtoScreenX( game.whiteParticle.body.getPosition().x);
		this.starty = game.convertWorldYtoScreenY( game.whiteParticle.body.getPosition().y);}
		
//		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent e) {

	   if (mouseButtonPressed==true) {
		endx=e.getX();
	   endy=e.getY();

	   }
	   
	   

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mx=e.getX();
		my=e.getY();
		if(game.layout==MainEngine.LayoutMode.MENU && mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+550) {

			if(my >= 500 && my <= 560){
				e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));	
				Menu.playRegion = true;

			}
			else if(my >= 600 && my <= 660){
				e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
				Menu.quitRegion = true;
			}
			else {				Menu.playRegion = false;
			Menu.quitRegion = false;

				e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}
		}
		else if(game.layout==MainEngine.LayoutMode.MISSION) {
			if(mx >= MainEngine.WIDTH/4+680 && mx <= MainEngine.WIDTH/2+810) {
				
				if(my >= 800 && my <= 860){
					Mission.playRegion = true;
					e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));

				}
				else{Mission.playRegion = false;}

				
			}
			else { 
			e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			Mission.playRegion = false;
	};
			
		}
		else if(MainEngine.success || MainEngine.isGameOver) {
			if(mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+580) {
				
				if(my >= 500 && my <= 560){
					game.contRegion = true;
					game.backRegion = true;
					e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));

				}
				else{game.contRegion = false;
				game.backRegion = false;}

				
			}
			else { 
			e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			game.contRegion = false;
			game.backRegion = false;
	};
			
		}
		else { Menu.playRegion = false;
		e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		Menu.quitRegion = false;
		Mission.playRegion = false;
		game.contRegion = false;
		game.backRegion = false;
};
		if (mouseButtonPressed==true) {
		endx=e.getX();
		endy=e.getY();
//		repaint();
		}

		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(Menu.quitRegion ||
			Menu.playRegion ||
		Mission.playRegion ||
		game.contRegion ||
		game.backRegion ) {
			AudioPlayer cheer = new AudioPlayer();
    		cheer.playSound("click.wav");
    		
		}
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {

		if(!MainEngine.isPaused) {
		mouseButtonPressed = false;
		setMouseButtonReleased(true);}
		
		
	}
	public static int getStartx() {
		return startx;
	}

	public static void setStartx(int startx) {
		mouse_listener2.startx = startx;
	}

	public static int getStarty() {
		return starty;
	}

	public static void setStarty(int starty) {
		mouse_listener2.starty = starty;
	}

	public static int getEndx() {
		return endx;
	}

	public static void setEndx(int endx) {
		mouse_listener2.endx = endx;
	}

	public static int getEndy() {
		return endy;
	}

	public static void setEndy(int endy) {
		mouse_listener2.endy = endy;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mx=e.getX();
		my=e.getY();
		if(game.layout==MainEngine.LayoutMode.MENU && mx >= MainEngine.WIDTH/2+420 && mx <= MainEngine.WIDTH/2+550) {

			if(my >= 500 && my <= 560){
				e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));	
				Menu.playRegion = true;

			}
			if(my >= 600 && my <= 660){
				e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public static boolean isMouseButtonReleased() {
		return mouseButtonReleased;
	}

	public static void setMouseButtonReleased(boolean mouseButtonReleased) {
		mouse_listener2.mouseButtonReleased = mouseButtonReleased;
	}}
