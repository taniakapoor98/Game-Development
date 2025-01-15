package main_assignment_box2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import main_assignment_box2d.MainEngine.LayoutMode;


public class BasicParticle  {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	public final int SCREEN_RADIUS;

	private final float linearDragForce,mass;
	public final Color col;
	protected final Body body;
    private final Image image;



	public BasicParticle(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float linearDragForce) throws IOException {
		World w=MainEngine.world; // a Box2D object
		BodyDef bodyDef = new BodyDef();  // a Box2D object
		bodyDef.type = BodyType.DYNAMIC; // this says the physics engine is to move it automatically
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		this.body = w.createBody(bodyDef);
		CircleShape circleShape = new CircleShape();// This class is from Box2D
		circleShape.m_radius = radius;
		FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
		fixtureDef.shape = circleShape;
		fixtureDef.density = (float) (mass/(Math.PI*radius*radius));
		fixtureDef.friction = 100f;// this is surface friction;
		if(MainEngine.layout == LayoutMode.LEVEL1) {
		fixtureDef.restitution = .6f;}
		else {
			
				fixtureDef.restitution = .4f;
		}
		if(this.body != null) {
		body.createFixture(fixtureDef);};
		this.linearDragForce=linearDragForce;
		this.mass=mass;
		this.SCREEN_RADIUS=(int)Math.max(MainEngine.convertWorldLengthToScreenLength(radius),1);
		this.col=col;
		//**************added
		if(MainEngine.layout == LayoutMode.LEVEL1) {
	        image = ImageIO.read(getClass().getResource("basketball.png")); }
		else if(MainEngine.layout == LayoutMode.LEVEL4) {
	        image = ImageIO.read(getClass().getResource("wheel.png")); }
		else if(MainEngine.layout == LayoutMode.LEVEL2) {
	        image = ImageIO.read(getClass().getResource("stone.png")); }
		else {
        image = ImageIO.read(getClass().getResource("wheel2.png")); }

	}

	public void draw(Graphics2D g) {
	    int x = MainEngine.convertWorldXtoScreenX(body.getPosition().x);
	    int y = MainEngine.convertWorldYtoScreenY(body.getPosition().y);
	    float angle = body.getAngle(); 
	    float screenWidth = MainEngine.convertWorldLengthToScreenLength(1);
	    float screenHeight = MainEngine.convertWorldLengthToScreenLength(1);
	    Vec2 position = body.getPosition();

	    float imageWidth = image.getWidth(null);
	    float imageHeight = image.getHeight(null);
	    float scaleX,scaleY;
	    if(MainEngine.layout == LayoutMode.LEVEL1) {
	    	scaleX = (screenWidth * 0.6f) / imageWidth; 
		    scaleY = (screenHeight * 0.6f) / imageHeight; 
	    }
	    else if(MainEngine.layout == LayoutMode.LEVEL3) {
	    	scaleX = (screenWidth * 0.35f) / imageWidth; 
		    scaleY = (screenHeight * 0.35f) / imageHeight; 
	    }
	    else if(MainEngine.layout == LayoutMode.LEVEL4) {
	    	scaleX = (screenWidth * 0.9f) / imageWidth; 
		    scaleY = (screenHeight * 0.9f) / imageHeight; 
	    }
	    else {
	     scaleX = (screenWidth * 0.42f) / imageWidth; 
	     scaleY = (screenHeight * 0.42f) / imageHeight; }

	    AffineTransform af = new AffineTransform();
	    af.translate(x, y);
	    af.rotate(-angle);
	    af.scale(scaleX, scaleY);
	    af.translate(-imageWidth / 2, -imageHeight / 2);

//	    g.setColor(col);
//	    g.fillOval(x - SCREEN_RADIUS, y - SCREEN_RADIUS, 2 * SCREEN_RADIUS, 2 * SCREEN_RADIUS);
	    g.drawImage(image, af, null); 
	}


	public void notificationOfNewTimestep() {
		if (linearDragForce>0) {
			Vec2 dragForce1=new Vec2(body.getLinearVelocity());
			dragForce1=dragForce1.mul(-linearDragForce*mass);
			body.applyForceToCenter(dragForce1);
		}
		
	}
	
}
