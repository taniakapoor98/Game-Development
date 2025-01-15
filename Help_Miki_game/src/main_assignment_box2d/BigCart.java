package main_assignment_box2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class BigCart  {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	public final float ratioOfScreenScaleToWorldScale;

	private final float rollingFriction,mass;
	public final Color col;
	protected final Body body;
	private final Path2D.Float polygonPath;

	public BigCart(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, int numSides) throws IOException {
		this(sx, sy, vx, vy, radius, col, mass, rollingFriction,mkRegularPolygon(numSides, radius),numSides);
	}
	public BigCart(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, Path2D.Float polygonPath, int numSides) throws IOException {
		World w=MainEngine.world; // a Box2D object
		BodyDef bodyDef = new BodyDef();  // a Box2D object
		bodyDef.type = BodyType.DYNAMIC; // this says the physics engine is to move it automatically
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		bodyDef.angularDamping = 0.1f;
		this.body = w.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
//		float angleInRadians = (float) Math.toRadians(45); 
//		body.setTransform(body.getPosition(), angleInRadians);
		Vec2[] vertices = verticesOfPath2D(polygonPath, numSides);
		vertices[0]=new Vec2(1.5f,.5f);
		vertices[1]=new Vec2(-1.5f,.5f);
		vertices[2]=new Vec2(-1.5f,-.5f);
		vertices[3]=new Vec2(1.5f,-.5f);

		
		shape.set(vertices, numSides);
		FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
		fixtureDef.shape = shape;
		fixtureDef.density = .4f;//(float) (mass/((float) numSides)/2f*(radius*radius)*Math.sin(2*Math.PI/numSides));
		fixtureDef.friction = 0f;// this is surface friction;
		fixtureDef.restitution = 0f;
		
		
		body.createFixture(fixtureDef);

		this.rollingFriction=rollingFriction;
		this.mass=mass;
		this.ratioOfScreenScaleToWorldScale=MainEngine.convertWorldLengthToScreenLength(1);
		System.out.println("Screenradius="+ratioOfScreenScaleToWorldScale);
		this.col=col;
		this.polygonPath=polygonPath;
		

	}
	

	
	public void draw(Graphics2D g) {
	    g.setColor(col);
	    Vec2 position = body.getPosition();
	    float angle = body.getAngle(); 
	    AffineTransform af = new AffineTransform();
	    af.translate(MainEngine.convertWorldXtoScreenX(position.x), MainEngine.convertWorldYtoScreenY(position.y));
	    af.scale(ratioOfScreenScaleToWorldScale, -ratioOfScreenScaleToWorldScale); // there is a minus in here because screenworld is flipped upsidedown compared to physics world
	    af.rotate(angle); 
	    Path2D.Float p = new Path2D.Float(polygonPath, af);
	    g.fill(p);

//	    if (!MainEngine.isGameOver) {
//	        
//	        drawSmiley(g, position);
//	    } else {
//	        drawSadFace(g, position);
//	    }
	}
	// added----------------------------------------------
	private void drawSmiley(Graphics2D g, Vec2 position) {
	    int x = MainEngine.convertWorldXtoScreenX(position.x);
	    int y = MainEngine.convertWorldYtoScreenY(position.y);

	    g.setColor(Color.red);
	    g.drawOval(x - 25, y - 25, 50, 50);
	    g.setColor(Color.YELLOW);
	    g.fillOval(x - 25, y - 25, 50, 50);
	    g.setColor(Color.BLACK);
	    g.fillOval(x - 10, y - 10, 5, 5);
	    g.fillOval(x + 5, y - 10, 5, 5);
	    g.drawArc(x - 10, y-2, 20, 12, 180, 180);
	}

	private void drawSadFace(Graphics2D g, Vec2 position) {
	    int x = MainEngine.convertWorldXtoScreenX(position.x);
	    int y = MainEngine.convertWorldYtoScreenY(position.y);

	    g.setColor(Color.red);
	    g.drawOval(x - 25, y - 25, 50, 50);
	    g.setColor(new Color(231,250,231));
	    g.fillOval(x - 25, y - 25, 50, 50);
	    g.setColor(Color.BLACK);
	    g.fillOval(x - 10, y - 10, 5, 5);
	    g.fillOval(x + 5, y - 10, 5, 5);
//	    g.drawArc(x - 10, y-2, 20, 12, 0, 180);
	    g.drawArc(x - 10, y + 5+2, 20, 10, 0, 180);
	}


//----------------end-----------------------------------

	public void notificationOfNewTimestep() {
		if (rollingFriction>0) {
			Vec2 rollingFrictionForce=new Vec2(body.getLinearVelocity());
			rollingFrictionForce=rollingFrictionForce.mul(-rollingFriction*mass);
			body.applyForceToCenter(rollingFrictionForce);
		}
	}
	
	// Vec2 vertices of Path2D
	public static Vec2[] verticesOfPath2D(Path2D.Float p, int n) {
		Vec2[] result = new Vec2[n];
		float[] values = new float[6];
		PathIterator pi = p.getPathIterator(null);
		int i = 0;
		while (!pi.isDone() && i < n) {
			int type = pi.currentSegment(values);
			if (type == PathIterator.SEG_LINETO) {
				result[i++] = new Vec2(values[0], values[1]);
			}
			pi.next();
		}
		return result;
	}
	// added*******************///////////
	public static Path2D.Float mkRegularPolygon(int n, float radius) {
		Path2D.Float p = new Path2D.Float();
//		p.moveTo(0, 0);
		p.moveTo(1.5f,.5f);
		p.lineTo(-1.5f,.5f);
		p.lineTo(-1.5f,-.5f);
		p.lineTo(1.5f,-.5f);

		
		p.closePath();
		return p;
	}
	
}

