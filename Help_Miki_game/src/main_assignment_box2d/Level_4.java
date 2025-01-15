package main_assignment_box2d;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Level_4 extends MainEngine {
	public static Rectangle contButton, backButton;

	public static int bscore = 0;
	public static BasicPolygon sensorParticle;
//	public static boolean success = false;
	public static List<BasicParticle> particles;
	public static List<BasicPolygon> polygons;
//	public List<BigCart> rectangles1;
	public static List<Pole> pole;

	public List<AnchoredBarrier> barriers;

//	private BigCart bigCart;
	public Level_4() throws IOException {

		contButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		backButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		rectangles1 = new ArrayList<BigCart>();
		pole = new ArrayList<Pole>();
		float linearDragForce = .02f;

		float r = .3f;
		barriers = new ArrayList<AnchoredBarrier>();

		barriers.add(new AnchoredBarrier_StraightLine(0, WORLD_HEIGHT / 20, WORLD_WIDTH, WORLD_HEIGHT / 20,
				new Color(1f, 0f, 0f, 0f)));

		bigCart = new BigCart(0 + WORLD_HEIGHT / 2, WORLD_HEIGHT / 20 + 1f, 0.0f, 0.0f, .25f, Color.orange, 4,
				linearDragForce, 4);
		rectangles1.add(bigCart);
		p = new Pole(0 + WORLD_HEIGHT / 2, 0 + 3f, 0.0f, 0.0f, .25f, Color.red, 2, 4, 4);
		pole.add(p);
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = bigCart.body;
		jointDef.bodyB = p.body;
		jointDef.collideConnected = false;
		jointDef.localAnchorA = new Vec2(0f, 0.5f);
		jointDef.localAnchorB = new Vec2(.0f, -1f);
		world.createJoint(jointDef);
		b_wheel = new BasicParticle(WORLD_HEIGHT / 2 - 1.5f / 2, WORLD_HEIGHT / 20 + r * 1.5f, 0, 0, .4f,
				new Color(1f, 0f, 0f, 0), 2, 0);

		particles.add(b_wheel);

		RevoluteJointDef jointDef_bw = new RevoluteJointDef();
		jointDef_bw.bodyA = bigCart.body;
		jointDef_bw.bodyB = b_wheel.body;
		jointDef_bw.collideConnected = false;
		jointDef_bw.localAnchorA = new Vec2(-1.5f / 2, -.5f);
		jointDef_bw.localAnchorB = new Vec2(0, 0);
		world.createJoint(jointDef_bw);

		f_wheel = new BasicParticle(WORLD_HEIGHT / 2 + 1.5f / 2, WORLD_HEIGHT / 20 + r * 1.5f, 0, 0, .4f,
				new Color(1f, 0f, 0f, 0), 2, 0);

		particles.add(f_wheel);

		RevoluteJointDef jointDef_fw = new RevoluteJointDef();
		jointDef_fw.bodyA = bigCart.body;
		jointDef_fw.bodyB = f_wheel.body;
		jointDef_fw.collideConnected = false;
		jointDef_fw.localAnchorA = new Vec2(1.5f / 2, -.5f);
		jointDef_fw.localAnchorB = new Vec2(0, 0);
		world.createJoint(jointDef_fw);

	}

	public void update() {
		if (!isPaused) {
			float r = .3f;
			float linearDragForce = .02f;
			int VELOCITY_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
			int POSITION_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;

			if (key_listener.isRotateLeftKeyPressed()) {
				b_wheel.body.applyTorque(10);

				bigCart.notificationOfNewTimestep();
				key_listener.setRotateLeftKeyPressed(false);
			}
			if (key_listener.isRotateRightKeyPressed()) {

				b_wheel.body.applyTorque(-10);
				bigCart.notificationOfNewTimestep();
				key_listener.setRotateRightKeyPressed(false);

			}

			for (BasicParticle p : particles) {
				p.notificationOfNewTimestep();
			}

			for (BasicPolygon p : polygons) {
				p.notificationOfNewTimestep();

			}

			for (Pole p : pole) {
				p.notificationOfNewTimestep();
			}
			for (BigCart p : rectangles1) {
				p.notificationOfNewTimestep();
			}

			world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			if (BasicView_L4.timeLeft > 0 && !success) {

				if (pole_status() || bigCart.body.getPosition().y < WORLD_HEIGHT / 20) {
					isGameOver = true;

					AudioPlayer cheer = new AudioPlayer();
					cheer.playSound("gameOver.wav");

				}
			} else if (!pole_status() && BasicView_L4.timeLeft <= 0)
				success = true;
		}

	}

	public static boolean pole_status() {
		if (p.body.getPosition().y < 2f) {
			pole_fell = true;
		}

		return pole_fell;
	}

}
