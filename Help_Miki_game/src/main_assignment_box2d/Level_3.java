package main_assignment_box2d;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.*;

import main_assignment_box2d.MainEngine.LayoutMode;

public class Level_3 extends MainEngine {
	public static PrismaticJoint joint_p;
	public static RollerBox cart2;
	public static BasicParticle b_wheel2, f_wheel2;
	public static Rectangle contButton, backButton;
	private static int f = 0;

	public Level_3() throws IOException {
		super();
		isPaused = true;
		world = new World(new Vec2(0, -GRAVITY));
		world.setContinuousPhysics(true);
		contButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		backButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		rectangles = new ArrayList<RollerBox>();
		ground_rect = new ArrayList<GroundRect>();

		ramps = new ArrayList<Ramp>();
		pole = new ArrayList<Pole>();
		barriers = new ArrayList<AnchoredBarrier>();
		connectors = new ArrayList<ElasticConnector>();

		float linearDragForce = .02f;
		float r = .4f;

		float s = 1.2f;
		barriers.add(new AnchoredBarrier_StraightLine(-6f, WORLD_HEIGHT / 20 - .02f, WORLD_WIDTH * 5,
				WORLD_HEIGHT / 20 - .02f, new Color(123, 66, 31)));
//		creating cart
		cart = new RollerBox(0 - 3f, 0 + 1, 0.0f, 0.0f, .25f, Color.orange, 10, linearDragForce, 4);

		rectangles.add(cart);

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = cart.body;

		b_wheel = new BasicParticle(-3f - .18f, 1 - r / 2, 0, 0, .1f, new Color(1f, 0f, 0f, 1f), 4f, 0);

		particles.add(b_wheel);

		RevoluteJointDef jointDef_bw = new RevoluteJointDef();
		jointDef_bw.bodyA = cart.body;
		jointDef_bw.bodyB = b_wheel.body;
		jointDef_bw.collideConnected = false;
		jointDef_bw.localAnchorA = new Vec2(-.2f, -.15f);
		jointDef_bw.localAnchorB = new Vec2(0, 0);
		jointDef_bw.maxMotorTorque = 500f;
		world.createJoint(jointDef_bw);

		f_wheel = new BasicParticle(-3f + .18f, 1 - r / 2, 0, 0, .1f, new Color(1f, 0f, 0f, 1f), 4.2f, 0);

		particles.add(f_wheel);

		RevoluteJointDef jointDef_fw = new RevoluteJointDef();
		jointDef_fw.bodyA = cart.body;
		jointDef_fw.bodyB = f_wheel.body;
		jointDef_fw.collideConnected = false;
		jointDef_fw.localAnchorA = new Vec2(.2f, -.15f);
		jointDef_fw.localAnchorB = new Vec2(0, 0);
		world.createJoint(jointDef_fw);

		particles.add(new TargetFlag(WORLD_WIDTH / 2f, 0 - WORLD_HEIGHT / 10f, 0, 0, 0, Color.red, 0, 0));

		ramps.add(new Ramp(WORLD_WIDTH / 2 * 3f, 0, new Color(123, 66, 31), 2, new Vec2(-2.5f, 0), new Vec2(8, 0),
				new Vec2(8, 3)));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH + WORLD_WIDTH / 2 * 3f - 2f, WORLD_HEIGHT / 2 - 2f,
				WORLD_WIDTH / 2 + WORLD_WIDTH + 6f + WORLD_WIDTH / 2 * 3f, WORLD_HEIGHT / 2 - 2f,
				new Color(123, 66, 31)));

		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r,
				WORLD_HEIGHT / 3 + r, 0.0f, 0.0f, r / 2, Color.YELLOW, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - 2 * r,
				WORLD_HEIGHT / 3 + r, 0.0f, 0.0f, r / 2, Color.red, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f, WORLD_HEIGHT / 3 + r,
				0.0f, 0.0f, r / 2, Color.orange, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + r,
				WORLD_HEIGHT / 3 + r, 0.0f, 0.0f, r / 2, Color.green, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + 2 * r,
				WORLD_HEIGHT / 3 + r, 0.0f, 0.0f, r / 2, Color.blue, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + 3 * r,
				WORLD_HEIGHT / 3 + r, 0.0f, 0.0f, r / 2, Color.pink, .5f, linearDragForce, 4));

		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r / 2, Color.red, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r / 2, Color.cyan, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + r,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r / 2, Color.gray, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + 2 * r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r / 2, Color.magenta, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f + 3 * r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r / 2, Color.yellow, .5f, linearDragForce, 4));

		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r,
				WORLD_HEIGHT / 3 + 4 * r, 0.0f, 0.0f, r / 2, Color.YELLOW, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r + r,
				WORLD_HEIGHT / 3 + 4 * r, 0.0f, 0.0f, r / 2, Color.blue, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r + 2 * r,
				WORLD_HEIGHT / 3 + 4 * r, 0.0f, 0.0f, r / 2, Color.pink, .5f, linearDragForce, 4));
		polygons.add(new BasicPolygon(WORLD_WIDTH / 2 + WORLD_WIDTH + 4f + WORLD_WIDTH / 2 * 3f - r + 3 * r,
				WORLD_HEIGHT / 3 + r * 4, 0.0f, 0.0f, r / 2, Color.orange, .5f, linearDragForce, 4));
		float new_x = WORLD_WIDTH / 2 + WORLD_WIDTH + 8f + WORLD_WIDTH / 2 * 3f - r + 3 * r;
		ramps.add(new Ramp(new_x, 0, new Color(123, 66, 31), 2, new Vec2(-3, 0), new Vec2(8, 0), new Vec2(8, 3)));
		barriers.add(new AnchoredBarrier_StraightLine(new_x + 8, WORLD_HEIGHT / 2 - 2f, new_x + 12,
				WORLD_HEIGHT / 2 - 2f, new Color(123, 66, 31)));
		float r_arc = 1f;
		barriers.add(new ArcBarrier(3 * WORLD_WIDTH - 3.2f, WORLD_HEIGHT / 7, 2.7f, -180, -15, 100));
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH * 6 - 2f, WORLD_HEIGHT / 2 - 2f,
				WORLD_WIDTH * 6 + 4.65f, WORLD_HEIGHT / 2 - 2f, new Color(123, 66, 31)));

		// adding prismatic joint to make a moving level
		GroundRect ground1 = new GroundRect(WORLD_WIDTH * 6 + 10.75f, WORLD_HEIGHT / 2 - 2f - .15f, Color.red, 2000,
				new Vec2(1f, .15f), new Vec2(-1f, .15f), new Vec2(-1f, -.15f), new Vec2(1f, -.15f));
		ground_rect.add(ground1);
		BasicPolygon poly = new BasicPolygon(WORLD_WIDTH * 6 + 10.75f - 3f, WORLD_HEIGHT / 2 - 2f - .15f, 0.0f, 0.0f,
				r / 5, Color.red, .3f, linearDragForce, 4, true);
		polygons.add(poly);
		PrismaticJointDef jointDef1 = new PrismaticJointDef();
		jointDef1.bodyA = poly.body;
		jointDef1.bodyB = ground1.body;
		jointDef1.collideConnected = false;
		jointDef1.localAxisA.set(1.0f, 0.0f);
		jointDef1.localAnchorA.set(0.0f, 0.0f);
		jointDef1.localAnchorB.set(0.0f, 0.0f);
		jointDef1.enableLimit = true;
		jointDef1.lowerTranslation = -2.0f;
		jointDef1.upperTranslation = 1.0f;
		jointDef1.enableMotor = true;
		jointDef1.maxMotorForce = 1000.0f;
		jointDef1.motorSpeed = 1.0f;
		joint_p = (PrismaticJoint) world.createJoint(jointDef1);
		barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH * 6 + 9.5f, WORLD_HEIGHT / 2 - 3f,
				WORLD_WIDTH * 6 + 18f, WORLD_HEIGHT / 2 - 3f, new Color(123, 66, 31)));
		r = .2f;

		BasicPolygon prev = new BasicPolygon(WORLD_WIDTH * 6 + 18f + r / 2 - .04f, WORLD_HEIGHT / 2 - 3f - r / 4, 0.0f,
				0.0f, r / 2, Color.red, 10f, linearDragForce, 4, true);
// creating bridge
		for (int i = 1; i < 23; i++) {
			float nextX = prev.body.getPosition().x + r;
			float nextY = WORLD_HEIGHT / 2 - 3f - r / 4;
			BasicPolygon next;
			if (i == 22) {
				next = new BasicPolygon(nextX, nextY, 0.0f, 0.0f, r / 2, Color.red, 10f, linearDragForce, 4, true);

			} else {
				next = new BasicPolygon(nextX, nextY, 0.0f, 0.0f, r / 2, new Color(123, 66, 31), 10f, linearDragForce,
						4);
			}

			DistanceJointDef jointDef11 = new DistanceJointDef();
			jointDef11.bodyA = prev.body;
			jointDef11.bodyB = next.body;

			jointDef11.length = r / 2;
			jointDef11.dampingRatio = .4f;
			jointDef11.localAnchorA.set(r / 8, 0);
			jointDef11.localAnchorB.set(-r / 8, 0);
			jointDef11.collideConnected = true;
			world.createJoint(jointDef11);

			polygons.add(prev);
			polygons.add(next);

			prev = next;

		}
		barriers.add(new AnchoredBarrier_StraightLine(prev.body.getPosition().x + r / 2, WORLD_HEIGHT / 2 - 3f,
				prev.body.getPosition().x + 2f * r + 25f, WORLD_HEIGHT / 2 - 3f, new Color(123, 66, 31)));

		// ***********creating pulley
		float x, y;
		x = 8f;
		y = WORLD_WIDTH / 4;
		GroundRect ground2 = new GroundRect(x, y, Color.cyan, 200, new Vec2(.8f, .1f), new Vec2(-.8f, .1f),
				new Vec2(-.8f, -.1f), new Vec2(.8f, -.1f));
		ground_rect.add(ground2);
		GroundRect ground3 = new GroundRect(x + 3f, y, Color.cyan, 200, new Vec2(.8f, .1f), new Vec2(-.8f, .1f),
				new Vec2(-.8f, -.1f), new Vec2(.8f, -.1f));
		ground_rect.add(ground3);
		Body bodyA = ground2.body, bodyB = ground3.body;
		Vec2 groundAnchorA = new Vec2(x, y);
		Vec2 groundAnchorB = new Vec2(x + 3f, y);
		Vec2 anchorA = bodyA.getWorldCenter();
		Vec2 anchorB = bodyB.getWorldCenter();

		PulleyJointDef pulleyJointDef = new PulleyJointDef();
		pulleyJointDef.initialize(bodyA, bodyB, groundAnchorA, groundAnchorB, anchorA, anchorB, 1f);
		pulleyJointDef.lengthA = .8f;
		pulleyJointDef.lengthB = .8f;

		PulleyJoint pulleyJoint = (PulleyJoint) world.createJoint(pulleyJointDef);

		ramps.add(new Ramp(3.5f, 0 + 4 * r, new Color(123, 66, 31), 2, new Vec2(3 / 2f, .5f), new Vec2(-3 / 2f, -.5f),
				new Vec2(3 / 2f, -.5f)));
	}

	public void update() {
		if (!isPaused) {
			int k = 0;
			float r = .3f;
			float linearDragForce = .02f;
			int VELOCITY_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
			int POSITION_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
			if (layout == LayoutMode.LEVEL3 && cart.body.getPosition().y < 0) {
				isGameOver = true;
				b_wheel.body.setLinearVelocity(new Vec2(0.1f, 0));
				;
			}
			if (key_listener.isRotateLeftKeyPressed()) {
				b_wheel.body.applyTorque(20);
				cart.body.applyTorque(-20);

				cart.notificationOfNewTimestep();
				b_wheel.notificationOfNewTimestep();

				key_listener.setRotateLeftKeyPressed(false);
			}
			if (key_listener.isRotateRightKeyPressed()) {

				b_wheel.body.applyTorque(-20);
				cart.body.applyTorque(20);

				cart.notificationOfNewTimestep();
				b_wheel.notificationOfNewTimestep();

				key_listener.setRotateRightKeyPressed(false);

			}
			if (key_listener.isBreakPressed()) {
				;
				b_wheel.body.setLinearVelocity(new Vec2(0, 0));
				key_listener.breakPressed = false;

			}
			if (key_listener.isSpacePressed()) {
				cart.body.applyTorque(-150f);

				key_listener.spacePressed = false;

			}

			for (BasicPolygon p : polygons) {
				p.notificationOfNewTimestep();

			}
			for (GroundRect p : ground_rect) {
				p.notificationOfNewTimestep();

			}

			for (RollerBox p : rectangles) {
				p.notificationOfNewTimestep();
			}

			world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

			float currentSpeed = joint_p.getMotorSpeed();
			float upperLimit = joint_p.getUpperLimit();
			float lowerLimit = joint_p.getLowerLimit();
			float translation = joint_p.getJointTranslation();
// making the prismatic joint level move automatically
			if (translation >= upperLimit || translation >= upperLimit) {
				joint_p.setMotorSpeed(-Math.abs(currentSpeed));

			} else if (translation <= lowerLimit) {
				joint_p.setMotorSpeed(Math.abs(currentSpeed));
			}

			if (cart.body.getPosition().x >= WORLD_HEIGHT * 9 - 2f && f == 0) {
				success = true;
				f++;
			}
			gameStatus();
		}

	}

	private void gameStatus() {

		if (isGameOver && !success) {
			AudioPlayer cheer = new AudioPlayer();
			cheer.playSound("gameOver.wav");
		}
	}

}
