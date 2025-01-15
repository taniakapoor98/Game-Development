package main_assignment_box2d;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;




public class Level_1 extends MainEngine{
	public static Rectangle contButton,backButton ;
	
	public static int bscore = 0;
	public static BasicPolygon sensorParticle;
	
	public Level_1() throws IOException {
		super();
		bscore = 0;
		world = new World(new Vec2(0, -GRAVITY));
		world.setContinuousPhysics(true);

		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		rectangles = new ArrayList<RollerBox>();
		ramps = new ArrayList<Ramp>();
		pole = new ArrayList<Pole>();
		barriers = new ArrayList<AnchoredBarrier>();
		connectors=new ArrayList<ElasticConnector>();
		float linearDragForce=.02f;
		float r=.3f;
	float s=1.2f;
		
		contButton = new Rectangle(WIDTH/2+420,500,160,60);
		backButton = new Rectangle(WIDTH/2+420,500,160,60);

		barriers.add(new AnchoredBarrier_StraightLine(0,WORLD_HEIGHT/15 , 2*WORLD_WIDTH, WORLD_HEIGHT/15, new Color(1f,0f,0f,0f )));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/7*5,WORLD_HEIGHT/10*7+.3f , WORLD_WIDTH/7*5, WORLD_HEIGHT/2, new Color(0f,1f,0f,0f )));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/7*5,WORLD_HEIGHT/2+.1f ,WORLD_WIDTH/7*5+1.2f, WORLD_HEIGHT/2+.1f, new Color(0f,1f,0f,0f )));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/7*5+1.2f,WORLD_HEIGHT/2+.1f ,WORLD_WIDTH/7*5+1.9f, WORLD_HEIGHT/2f-.4f, new Color(0f,1f,0f,0f )));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/9*8+.1f,WORLD_HEIGHT/2-.2f , WORLD_WIDTH/9*8+.1f, WORLD_HEIGHT/2-.8f, new Color(0f,0f,0f,0f )));

		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/9*8,WORLD_HEIGHT/2-.8f , WORLD_WIDTH/9*8, WORLD_HEIGHT/15, new Color(0f,0f,0f,0f )));

		barriers.add(new AnchoredBarrier_StraightLine(0+.4f,WORLD_HEIGHT/4 , WORLD_WIDTH/6-.5f, WORLD_HEIGHT/4, new Color(1f,0f,0f,0f )));
		
		polygons.add(new BasicPolygon(WORLD_WIDTH-WORLD_WIDTH/3-.28f,WORLD_HEIGHT/2+.44f,0.0f,0.0f,r/4,Color.YELLOW, 1, linearDragForce,4));
		sensorParticle = new BasicPolygon(WORLD_WIDTH-WORLD_WIDTH/3+.1f,WORLD_HEIGHT/2+.2f,0.0f,0.0f,r/2, new Color(1f,0f,0f,0f ), 1, linearDragForce,4,true);
		sensorParticle.body.setUserData("LEVITATING_SENSOR"); // adding sensor to detect when basket is scored

		polygons.add(sensorParticle);
		
		
		
		
		whiteParticle = new BasicParticle(0+2*r,(WORLD_HEIGHT/4)+r/2,0,0, r,Color.white, 12, linearDragForce);
		whiteParticle.body.setUserData("BALL");
		particles.add(whiteParticle);

		
		t_arrow = new TargetArrow(getWhiteParticlePos(),getWhiteParticlePos()); // dashed raw
		  world.setContactListener(new ContactListener() {
	            @Override
	            public void beginContact(Contact contact) {
	                Object userDataA = contact.getFixtureA().getBody().getUserData();
	                Object userDataB = contact.getFixtureB().getBody().getUserData();
	                if (("LEVITATING_SENSOR".equals(userDataA) && "BALL".equals(userDataB))|| 
	                		("LEVITATING_SENSOR".equals(userDataB) && "BALL".equals(userDataA))) {
	                    incrementScore();
	                }
	            }

	            @Override
	            public void endContact(Contact contact) {
	            }

	            @Override
	            public void preSolve(Contact contact, Manifold oldManifold) {
	            }

	            @Override
	            public void postSolve(Contact contact, ContactImpulse impulse) {
	            }
	        });
	    
		
	}
	private void incrementScore() {
        bscore++;
    	AudioPlayer cheer = new AudioPlayer();

        if (bscore>=5) {
        	success = true;
        	cheer.playSound("cheer.wav");}
        else {
        	cheer.playSound("score.wav");
        }
    }
	public void update() {
		if (!isPaused) {
		float r=.3f;
		float linearDragForce=.02f;
		int VELOCITY_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
		int POSITION_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
		mousepressed = false;
		if(whiteParticle.body.getPosition().y>0+WORLD_HEIGHT/3.5 || whiteParticle.body.getPosition().x <0 || whiteParticle.body.getPosition().x > WORLD_WIDTH ) {
			try {
				whiteParticle= new BasicParticle(0+2*r,(WORLD_HEIGHT/4)+r/2,0,0, r,Color.white, 12, linearDragForce);
			} catch (IOException e) {
				e.printStackTrace();
			}
			whiteParticle.body.setUserData("BALL");

			particles.add(whiteParticle);


		}
		for (BasicParticle p:particles) {
			p.notificationOfNewTimestep();
		}
		
		for (BasicPolygon p:polygons) {
			p.notificationOfNewTimestep();
			
		}
		
		
		world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		
		if(mouse_listener.isMouseButtonPressed() == true && !isPaused) {

			mousepressed = true;
			TargetArrow.setStartPos(getWhiteParticlePos());
			TargetArrow.setEndPos(mouse_listener.getWorldCoordinatesOfMousePointer());}
		
	else {
		TargetArrow.setStartPos(getWhiteParticlePos());
		TargetArrow.setEndPos(getWhiteParticlePos());}
		if (!isPaused) {
		if ( mouse_listener.isMouseButtonReleased() == true) {

			Vec2 endpos = new Vec2(convertScreenXtoWorldX(mouse_listener.getStartx()+mouse_listener.getEndx()/2),convertScreenYtoWorldY( Math.min(mouse_listener.getStarty(), mouse_listener.getEndy()) - 120));
			Vec2 vel_new = endpos.sub(getWhiteParticlePos());
			whiteParticle.body.setLinearVelocity(vel_new.mul(1.2f));
			whiteParticle.body.applyForceToCenter(new Vec2(0,1));
			whiteParticle.notificationOfNewTimestep();
			TargetArrow.setStartPos(getWhiteParticlePos());
			TargetArrow.setEndPos(getWhiteParticlePos());
			
			
			
		mouse_listener.setMouseButtonReleased(false); 
		}}
		else {whiteParticle.body.setLinearVelocity(new Vec2(0,0));}
		
		}
		
		
	}
	
	
	public void removeBody(Body body) {
        if (body != null && body.getWorld() == world) {
            world.destroyBody(body);
        }
    }
	
	
	private Vec2 getWhiteParticlePos() {
		// TODO Auto-generated method stub
		return whiteParticle.body.getPosition();
	}
	


}
