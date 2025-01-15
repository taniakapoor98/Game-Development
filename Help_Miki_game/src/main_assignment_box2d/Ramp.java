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

public class Ramp {
	
	
	public final float ratioOfScreenScaleToWorldScale ;

	private final float rollingFriction,mass;
	public final Color col;
	protected final Body body;
	private final Path2D.Float polygonPath;
	public static Vec2 v1,v2,v3;
	public int numSides = 3;

	
	public Ramp(float sx, float sy, Color col, float mass ,Vec2 v1,Vec2 v2,Vec2 v3) {
		World w=MainEngine.world; 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KINEMATIC; 
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(0,0);
		bodyDef.angularDamping = 0.1f;
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		this.body = w.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();

		Vec2[] vertices = verticesOfPath2D(mkRegularPolygon(numSides), numSides);
		vertices[0]=v1;
		vertices[1]=v2;
		vertices[2]=v3;

		
		shape.set(vertices, 3);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = .4f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		
		if(this.body != null) {
			body.createFixture(fixtureDef);};

		this.rollingFriction=0;
		this.mass=mass;
		this.ratioOfScreenScaleToWorldScale=MainEngine.convertWorldLengthToScreenLength(1);
		System.out.println("Screenradius="+ratioOfScreenScaleToWorldScale);
		this.col=col;
		this.polygonPath=mkRegularPolygon(numSides);
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

	}




//----------* Author: Michael Fairbank-----------
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
	//--------------------------end --------------------
	public static Path2D.Float mkRegularPolygon(int n) {
		Path2D.Float p = new Path2D.Float();
//		p.moveTo(0, 0);
		p.moveTo(v1.x,v1.y);
		p.lineTo(v2.x,v2.y);
		p.lineTo(v3.x,v3.y);
//		p.lineTo(.8f,-.2f);
//		for (int i = 0; i < n; i++) {
//			float x = (float) (Math.cos((Math.PI * 2 * i) / n) * radius);
//			float y = (float) (Math.sin((Math.PI * 2 * i) / n) * radius);
//			p.lineTo(x, y);
//		}
		
		p.closePath();
		return p;
	}

}
