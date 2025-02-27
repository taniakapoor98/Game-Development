package main_assignment_box2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import java.awt.Image;

public class BasicRect {

	private final Image image;

	public final float ratioOfScreenScaleToWorldScale;

	private final float rollingFriction, mass;
	public final Color col;
	protected final Body body;
	private final Path2D.Float polygonPath;

	public BasicRect(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction,
			int numSides) throws IOException {
		this(sx, sy, vx, vy, radius, col, 0, rollingFriction, mkRegularPolygon(numSides, radius), numSides);
	}

	public BasicRect(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction,
			Path2D.Float polygonPath, int numSides) throws IOException {
		World w = MainEngine.world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(sx, sy);
		bodyDef.active = false;
		bodyDef.linearVelocity.set(vx, vy);
		bodyDef.angularDamping = 0.1f;
		this.body = w.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();

		Vec2[] vertices = verticesOfPath2D(polygonPath, numSides);
		shape.set(vertices, numSides);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = .9f;// (float) (mass/((float)
									// numSides)/2f*(radius*radius)*Math.sin(2*Math.PI/numSides));
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.4f;
		image = ImageIO.read(getClass().getResource("tiles.png"));

		body.createFixture(fixtureDef);

		this.rollingFriction = rollingFriction;
		this.mass = mass;
		this.ratioOfScreenScaleToWorldScale = MainEngine.convertWorldLengthToScreenLength(1);
		System.out.println("Screenradius=" + ratioOfScreenScaleToWorldScale);
		this.col = col;
		this.polygonPath = polygonPath;
	}

	public void draw(Graphics2D g) {
		Vec2 position = body.getPosition();
		float angle = body.getAngle();
		float screenWidth = MainEngine.convertWorldLengthToScreenLength(1);
		float screenHeight = MainEngine.convertWorldLengthToScreenLength(1);
		float imageWidth = image.getWidth(null);
		float imageHeight = image.getHeight(null);

		float scaleX = 0.3f * screenWidth / imageWidth;
		float scaleY = .3f * screenHeight / imageHeight;

		AffineTransform af = new AffineTransform();
		af.translate(MainEngine.convertWorldXtoScreenX(position.x), MainEngine.convertWorldYtoScreenY(position.y));
		af.rotate(angle);

		af.scale(scaleX, scaleY);

		g.drawImage(image, af, null);

	}

	public void notificationOfNewTimestep() {
		if (rollingFriction > 0) {
			Vec2 rollingFrictionForce = new Vec2(body.getLinearVelocity());
			rollingFrictionForce = rollingFrictionForce.mul(-rollingFriction * mass);
			body.applyForceToCenter(rollingFrictionForce);
		}
	}

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
