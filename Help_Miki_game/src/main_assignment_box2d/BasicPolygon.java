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

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import main_assignment_box2d.MainEngine.LayoutMode;

public class BasicPolygon {
	/*
	 * Author: Michael Fairbank Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	public final float ratioOfScreenScaleToWorldScale;

	private final float rollingFriction, mass;
	public final Color col;

	protected final Body body;
	private final Path2D.Float polygonPath;

	private BufferedImage image;

	private int numSides;

	private int SCREEN_RADIUS;

	public static int bscore = 0;

	public BasicPolygon(float sx, float sy, float vx, float vy, float radius, Color col, float mass,
			float rollingFriction, int numSides) {
		this(sx, sy, vx, vy, radius, col, mass, rollingFriction, mkRegularPolygon(numSides, radius), numSides);
	}

	public BasicPolygon(float sx, float sy, float vx, float vy, float radius, Color col, float mass,
			float rollingFriction, int numSides, boolean isSensor) {
		this(sx, sy, vx, vy, radius, col, mass, rollingFriction, mkRegularPolygon(numSides, radius), numSides,
				isSensor);
	}

	public BasicPolygon(float sx, float sy, float vx, float vy, float radius, Color col, float mass,
			float rollingFriction, Path2D.Float polygonPath, int numSides) {
		World w = MainEngine.world; // a Box2D object
		BodyDef bodyDef = new BodyDef(); // a Box2D object
		if (MainEngine.layout == LayoutMode.LEVEL1) {
			bodyDef.type = BodyType.STATIC;
		} else {
			bodyDef.type = BodyType.DYNAMIC;
		} // this says the physics engine is to move it automatically
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		bodyDef.angularDamping = 0.1f;
		this.body = w.createBody(bodyDef);
		body.setUserData("OBJECT_IDENTIFIER");
		PolygonShape shape = new PolygonShape();
		float angleInRadians = (float) Math.toRadians(45);
		body.setTransform(body.getPosition(), angleInRadians);
		Vec2[] vertices = verticesOfPath2D(polygonPath, numSides);
		shape.set(vertices, numSides);
		FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
		fixtureDef.shape = shape;
		if (MainEngine.layout != LayoutMode.LEVEL3 || radius > .25 / 2f) {
			fixtureDef.density = .9f;
			fixtureDef.friction = 0.3f;
		} // (float) (mass/((float)
			// numSides)/2f*(radius*radius)*Math.sin(2*Math.PI/numSides));

		else {
			fixtureDef.density = 0.0f;
			fixtureDef.friction = 10f;
		}

		// this is surface friction;
		fixtureDef.restitution = 0.4f;

		body.createFixture(fixtureDef);

		this.rollingFriction = rollingFriction;
		this.mass = mass;
		this.ratioOfScreenScaleToWorldScale = MainEngine.convertWorldLengthToScreenLength(1);
		this.col = col;
		this.polygonPath = polygonPath;
		this.SCREEN_RADIUS = (int) Math.max(MainEngine.convertWorldLengthToScreenLength(radius), 1);

		this.numSides = numSides;

	}

	// added*****************
	public BasicPolygon(float sx, float sy, float vx, float vy, float radius, Color col, float mass,
			float rollingFriction, Path2D.Float polygonPath, int numSides, boolean isSensor) {
		World w = MainEngine.world;
		BodyDef bodyDef = new BodyDef();
		if (MainEngine.layout == LayoutMode.LEVEL1 && isSensor == true) {
			bodyDef.type = BodyType.KINEMATIC;
		} else {
			bodyDef.type = BodyType.KINEMATIC;
		}
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		bodyDef.angularDamping = 0.1f;
		this.body = w.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		float angleInRadians = (float) Math.toRadians(45);
		if (MainEngine.layout != LayoutMode.LEVEL3 || radius > .4 / 5f)
			body.setTransform(body.getPosition(), angleInRadians);
		Vec2[] vertices = verticesOfPath2D(polygonPath, numSides);
		shape.set(vertices, numSides);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		if (MainEngine.layout == LayoutMode.LEVEL1 && isSensor == true) {
			fixtureDef.isSensor = true;
		}

		fixtureDef.density = .9f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.4f;

		body.createFixture(fixtureDef);
		body.setUserData("BASKET_SENSOR");

		this.rollingFriction = rollingFriction;
		this.mass = mass;
		this.ratioOfScreenScaleToWorldScale = MainEngine.convertWorldLengthToScreenLength(1);
		this.col = col;
		this.polygonPath = polygonPath;

	}
	//// ***********end///////////

	public void draw(Graphics2D g) {

		g.setColor(col);
		Vec2 position = body.getPosition();
		float angle = body.getAngle();
		AffineTransform af = new AffineTransform();
		af.translate(MainEngine.convertWorldXtoScreenX(position.x), MainEngine.convertWorldYtoScreenY(position.y));
		af.scale(ratioOfScreenScaleToWorldScale, -ratioOfScreenScaleToWorldScale);// there is a minus in here because
																					// screenworld is flipped upsidedown
																					// compared to physics world
		af.rotate(angle);
		Path2D.Float p = new Path2D.Float(polygonPath, af);
		g.fill(p);
		if (MainEngine.layout == LayoutMode.LEVEL2 && numSides == 6) {
			try {
				image = ImageIO.read(getClass().getResource("ring.png"));
				g.drawImage(image, af, null);
				int x = MainEngine.convertWorldXtoScreenX(body.getPosition().x);
				int y = MainEngine.convertWorldYtoScreenY(body.getPosition().y);
				float angle1 = body.getAngle();
				float screenWidth = MainEngine.convertWorldLengthToScreenLength(1);
				float screenHeight = MainEngine.convertWorldLengthToScreenLength(1);
				Vec2 position1 = body.getPosition();

				float imageWidth = image.getWidth(null);
				float imageHeight = image.getHeight(null);
				float scaleX, scaleY;
				{
					scaleX = (screenWidth * 0.42f) / imageWidth;
					scaleY = (screenHeight * 0.42f) / imageHeight;
				}

				AffineTransform af1 = new AffineTransform();
				af1.translate(x, y);
				af1.rotate(-angle1);
				af1.scale(scaleX, scaleY);
				af1.translate(-imageWidth / 2, -imageHeight / 2);

				g.setColor(col);
				g.fillOval(x - SCREEN_RADIUS, y - SCREEN_RADIUS, 2 * SCREEN_RADIUS, 2 * SCREEN_RADIUS);
				g.drawImage(image, af1, null);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void notificationOfNewTimestep() {
		if (rollingFriction > 0) {
			Vec2 rollingFrictionForce = new Vec2(body.getLinearVelocity());
			rollingFrictionForce = rollingFrictionForce.mul(-rollingFriction * mass);
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

	public static Path2D.Float mkRegularPolygon(int n, float radius) {
		Path2D.Float p = new Path2D.Float();
		p.moveTo(radius, 0);
		for (int i = 0; i < n; i++) {
			float x = (float) (Math.cos((Math.PI * 2 * i) / n) * radius);
			float y = (float) (Math.sin((Math.PI * 2 * i) / n) * radius);
			p.lineTo(x, y);
		}
		p.closePath();
		return p;
	}

}
