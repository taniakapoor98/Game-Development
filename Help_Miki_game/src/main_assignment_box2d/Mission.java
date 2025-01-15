package main_assignment_box2d;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.util.Random;
public class Mission extends MainEngine{
	public static Rectangle playButton ;
	public static Container con;
	public static JPanel missionPanel;
	public static boolean playRegion = false;
	public Mission() throws IOException {
		super();
		float linearDragForce=.02f;
		float r=.3f;
		playButton = new Rectangle(WIDTH/4+680,800,130,60);
		if(MainEngine.getMission_cnt()>4) {
			polygons = new ArrayList<BasicPolygon>();
			Random random = new Random();
	        for (int i = 0; i < 200; i++) {
	            float x = random.nextFloat() * WORLD_WIDTH;
	            float y = random.nextFloat() * WORLD_HEIGHT;
	            float vx = random.nextFloat() * 10 - 5;
	            float vy = random.nextFloat() * 10 - 5;
	            Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	            polygons.add(new BasicPolygon(x, y, vx, vy, 0.1f, randomColor, 1, linearDragForce, 4));
	        }
		}

	}
	
	// function to manage levels
	public void goToLevel() {
		switch(MainEngine.getMission_cnt()){
		case 1:{        
			layout = LayoutMode.LEVEL1;
			break;
				} 
		case 2:{        
			layout = LayoutMode.LEVEL3;
			break;
				} 
		case 3:{        
			layout = LayoutMode.LEVEL4;
			break;
				} 
		case 4:{        
			layout = LayoutMode.LEVEL2;
			break;
				} 
		}
        frame.dispose(); 
        try {
            MainEngine.main(null); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void update() {
		if(MainEngine.getMission_cnt()>4) {
			float r=.3f;
			float linearDragForce=.02f;
			int VELOCITY_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
			int POSITION_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
			mousepressed = false;

			for (BasicPolygon p:polygons) {
				p.notificationOfNewTimestep();
				
			}
			
			
			world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}}
	// mission texts
	public static String getMissionText(int i) {
		final String[] missions = {
			    "Miki really likes a girl named Anne but she is intereseted in a top basketball player who can score 4 baskets in a minute. Help Miki impress Anne by scoring 5 baskets within a minute.",
			    "Miki asked her on a date and she is down! Well there is one problem, she asked to do something really cool and he suggested stunt driving!! He has never done it before so it’s all on you now. Help Miki and Anne reach the finish line safe and sound.",
			    "After 6 months, it is the first Miki is going to meet Anne’s parents and he is too nervous. Help Miki leave a good impression on Anne’s mother who wants him to do their family ritual, a responsibility test you see, in which he is asked to keep a pole from falling while driving for 15 seconds."
			    ,"Miki has finally gotten a chance to propose and he doesn’t have a ring. But her sister does which she recently bought for herself. Help Miki steal the ring by breaking the boxes."

			    ,""	,""
		};

		return missions[i-1];
	}

}
