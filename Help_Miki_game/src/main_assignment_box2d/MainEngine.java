package main_assignment_box2d;

import java.io.File;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class MainEngine extends Canvas {

	protected static boolean isPaused = true;
	public static boolean contRegion = false;
	public static boolean backRegion = false;
	private static Thread theThread;
	public static JEasyFrame frame;
	public static AudioPlayer music;
	public static int score = 0;
	public static BasicKeyListener key_listener;
	public static MainEngine game;
	public static final int SCREEN_HEIGHT = 1000;
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCALE = 2;
	public final String title = "main";
	public static boolean success = false;
	private static int mission_cnt =1;
	/*
	 * Below code Author: Michael Fairbank Significant changes applied:
	 */

	// frame dimensions
	public static final Dimension FRAME_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
	public static final float WORLD_WIDTH = 10;// metres
	public static final float WORLD_HEIGHT = SCREEN_HEIGHT * (WORLD_WIDTH / SCREEN_WIDTH);
	public static final float GRAVITY = 9.8f;

	public static int convertWorldXtoScreenX(float worldX) {
		return (int) (worldX / WORLD_WIDTH * SCREEN_WIDTH);
	}

	public static int convertWorldYtoScreenY(float worldY) {
		// minus sign in here is because screen coordinates are upside down.
		return (int) (SCREEN_HEIGHT - (worldY / WORLD_HEIGHT * SCREEN_HEIGHT));
	}

	public static float convertWorldLengthToScreenLength(float worldLength) {
		return (worldLength / WORLD_WIDTH * SCREEN_WIDTH);
	}

	public static float convertScreenXtoWorldX(int screenX) {
		return screenX * WORLD_WIDTH / SCREEN_WIDTH;
	}

	public static float convertScreenYtoWorldY(int screenY) {
		return (SCREEN_HEIGHT - screenY) * WORLD_HEIGHT / SCREEN_HEIGHT;
	}

	// sleep time between two drawn frames in milliseconds
	public static final int DELAY = 20;
	public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH = 100;
	// estimate for time between two frames in seconds
	public static final float DELTA_T = DELAY / 1000.0f;
	// *******end*******//

	// Added code //
	public static boolean pole_fell = false, mousepressed = false;
																	
	public static final boolean ALLOW_MOUSE_POINTER_TO_DRAG_BODIES_ON_SCREEN = true;
	public TargetArrow t_arrow;
	public static RollerBox cart;
	public static BigCart bigCart;
	public static Pole p;
	public static BasicParticle b_wheel, f_wheel, TargetFlag;

	public static int getMission_cnt() {
		return mission_cnt;
	}

	public static void setMission_cnt(int mission_cnt) {
		MainEngine.mission_cnt = mission_cnt;
	}

	public static World world;
	public static BasicParticle whiteParticle;
	public static mouse_listener2 mouse_listener;

	public static List<BasicParticle> particles;
	public static List<BasicPolygon> polygons;
	public static List<RollerBox> rectangles;
	public static List<BigCart> rectangles1;
	public static List<Pole> pole;
	public static List<Ramp> ramps;
	public static List<GroundRect> ground_rect;

	public List<AnchoredBarrier> barriers;
	public List<ElasticConnector> connectors;
	public static boolean isGameOver = false;
	public static MouseJoint mouseJointDef;

	public static enum LayoutMode {
		MENU, MISSION, LEVEL3, LEVEL1,
		LEVEL2, LEVEL4
	};

	public static LayoutMode layout = LayoutMode.MENU;
// managing views and game levels based on layout mode
	public MainEngine() throws IOException {
		isGameOver = false;
		success = false;
		contRegion = false;
		backRegion = false;
		isPaused = true;
		world = new World(new Vec2(0, -GRAVITY));
		world.setContinuousPhysics(true);

		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		rectangles = new ArrayList<RollerBox>();
		rectangles1 = new ArrayList<BigCart>();

		ramps = new ArrayList<Ramp>();
		pole = new ArrayList<Pole>();
		barriers = new ArrayList<AnchoredBarrier>();
		connectors = new ArrayList<ElasticConnector>();

		float linearDragForce = .02f;
		float r = .3f;
		float s = 1.2f;

	}

	public static void main(String[] args) throws Exception {
		switch (layout) {
		case LEVEL4: {
			game = new Level_4();
			final BasicView_L3 view = new BasicView_L4(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		case LEVEL3: {
			game = new Level_3();
			final BasicView_L3 view = new BasicView_L3(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		case LEVEL2: {
			game = new Level_2();
			final BasicView_L3 view = new BasicView_L2(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		case LEVEL1: {
			game = new Level_1();
			final BasicView_L3 view = new BasicView_L1(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		case MENU: {
			game = new Menu();
			final BasicViewMenu view = new BasicViewMenu(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		case MISSION: {
			mission_cnt++;
			game = new Mission();
			final BasicViewMission view = new BasicViewMission(game);
			frame = new JEasyFrame(view, "Help Miki");
			key_listener = new BasicKeyListener();
			view.addKeyListener(key_listener);
			mouse_listener = new mouse_listener2(game, view);
			view.addMouseListener(mouse_listener);
			view.addMouseMotionListener(mouse_listener);
			view.requestFocus();
			game.startThread(game, view);
			break;
		}
		}
	}
	/*
	 * Below code Author: Michael Fairbank Significant changes applied:
	 */
	private static void startThread(final MainEngine game, final BasicView_L3 view) throws InterruptedException {
		music = new AudioPlayer("music.wav");

		Runnable r = new Runnable() {
			public void run() {

				game.setKeyListner(key_listener);
//	    			
				while (theThread == Thread.currentThread()) {
					if (!game.isGameOver) {
						game.update();
					}

					view.repaint();
					Toolkit.getDefaultToolkit().sync();
					try {
						Thread.sleep(MainEngine.DELAY);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		theThread = new Thread(r);
		theThread.start();
	}
	//*****end*****//
	
	// Added code//
	public static BasicKeyListener getKeyListner() {
		return key_listener;
	}

	public static void setKeyListner(BasicKeyListener keyListner) {
		MainEngine.key_listener = keyListner;
	}

	public void update() {

	}

	public static void goToMission() {
		// Function to go to next mission between games
		layout = LayoutMode.MISSION;
		frame.dispose(); 
		try {
			MainEngine.main(null); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//function to go to menu 
	public static void goToMenu() {
		layout = LayoutMode.MENU;
		frame.dispose(); 
		try {
			MainEngine.main(null); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMission_cnt(0);
	}

	public static void togglePause() {
		isPaused = !isPaused;
	}

	public static void resumeGame() {
		isPaused = false;
	}

}
