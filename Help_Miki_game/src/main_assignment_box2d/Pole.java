package main_assignment_box2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Pole  {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	public final float ratioOfScreenScaleToWorldScale;

	private final float rollingFriction,mass;
	public final Color col;
	protected final Body body;
	private final Path2D.Float polygonPath;

	public Pole(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, int numSides) {
		this(sx, sy, vx, vy, radius, col, mass, rollingFriction,mkRegularPolygon(numSides, radius),numSides);
	}
	public Pole(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, Path2D.Float polygonPath, int numSides) {
		World w=MainEngine.world; 
		BodyDef bodyDef = new BodyDef(); 
		bodyDef.type = BodyType.DYNAMIC; 
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		bodyDef.angularDamping = 0.1f;
		this.body = w.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
// added-----------
		Vec2[] vertices = verticesOfPath2D(polygonPath, numSides);
		vertices[0]=new Vec2(.1f,1.f);
		vertices[1]=new Vec2(-.1f,1f);
		vertices[2]=new Vec2(-.1f,-1.f);
		vertices[3]=new Vec2(.1f,-1f);

	//-------------------	
		shape.set(vertices, numSides);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = .9f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.4f;
		
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
		af.scale(ratioOfScreenScaleToWorldScale, -ratioOfScreenScaleToWorldScale);// there is a minus in here because screenworld is flipped upsidedown compared to physics world
		af.rotate(angle); 
		Path2D.Float p = new Path2D.Float (polygonPath,af);
		g.fill(p);
	}



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
	// added--------------------
	public static Path2D.Float mkRegularPolygon(int n, float radius) {
		Path2D.Float p = new Path2D.Float();
//		p.moveTo(0, 0);
		p.moveTo(.1f,1f);
		p.lineTo(-.1f,1f);
		p.lineTo(-.1f,-1f);
		p.lineTo(.1f,-1f);

		
		p.closePath();
		return p;
	}
	
}
