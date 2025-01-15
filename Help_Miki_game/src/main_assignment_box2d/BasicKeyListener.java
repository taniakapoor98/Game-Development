package main_assignment_box2d;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeyListener extends KeyAdapter {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 * Significant changes applied:
	 */
	public static boolean rotateRightKeyPressed=false, rotateLeftKeyPressed=false, thrustKeyPressed, breakPressed=false, spacePressed=false; 

	public static void setRotateRightKeyPressed(boolean rotateRightKeyPressed) {
		BasicKeyListener.rotateRightKeyPressed = rotateRightKeyPressed;
	}

	public static void setRotateLeftKeyPressed(boolean rotateLeftKeyPressed) {
		BasicKeyListener.rotateLeftKeyPressed = rotateLeftKeyPressed;
	}

	public static boolean isRotateRightKeyPressed() {
		
		return rotateRightKeyPressed;
	}

	public static boolean isRotateLeftKeyPressed() {
		
		return rotateLeftKeyPressed;
	}

	public static boolean isThrustKeyPressed() {
		return thrustKeyPressed;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			thrustKeyPressed=true;
			break;
		case KeyEvent.VK_LEFT:
			rotateLeftKeyPressed=true;
			break;
		case KeyEvent.VK_RIGHT:
			rotateRightKeyPressed=true;
			break;
		case KeyEvent.VK_DOWN: // added
			breakPressed=true;
			break;
		case KeyEvent.VK_SPACE: // added
			spacePressed=true;
			break;
		}
	
	
	}

	public static boolean isSpacePressed() {
		return spacePressed;
	}

	public static void setSpacePressed(boolean spacePressed) {
		BasicKeyListener.spacePressed = spacePressed;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			thrustKeyPressed=false;
			break;
		case KeyEvent.VK_LEFT:
			rotateLeftKeyPressed=false;
			break;
		case KeyEvent.VK_RIGHT:
			rotateRightKeyPressed=false;
			break;
			
		case KeyEvent.VK_SPACE:
			spacePressed=false;
			break;
		}
	}

	public boolean isBreakPressed() {
		// TODO Auto-generated method stub
		return breakPressed;
	}
}
