package main_assignment_box2d;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Level_2 extends MainEngine {
	public static Rectangle contButton, backButton;

	public Level_2() throws IOException {
		super();
		world = new World(new Vec2(0, -GRAVITY));
		world.setContinuousPhysics(true);
		contButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		backButton = new Rectangle(WIDTH / 2 + 420, 500, 160, 60);
		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		rectangles = new ArrayList<RollerBox>();
		ramps = new ArrayList<Ramp>();
		pole = new ArrayList<Pole>();
		barriers = new ArrayList<AnchoredBarrier>();
		connectors = new ArrayList<ElasticConnector>();
		float linearDragForce = .02f;
		float r = .3f;

		float s = 1.2f;
		barriers.add(new AnchoredBarrier_StraightLine(0, WORLD_HEIGHT / 20, 2 * WORLD_WIDTH, WORLD_HEIGHT / 20,
				new Color(1f, 0f, 0f, 0f)));
		barriers.add(new AnchoredBarrier_StraightLine(0, WORLD_HEIGHT / 4, WORLD_WIDTH / 6 - r / 2, WORLD_HEIGHT / 4,
				new Color(255, 255, 255)));

		barriers.add(new AnchoredBarrier_StraightLine(0 + WORLD_WIDTH / 2 - r, WORLD_HEIGHT / 3,
				WORLD_WIDTH - WORLD_WIDTH / 5, WORLD_HEIGHT / 3, new Color(255, 255, 255)));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r, WORLD_HEIGHT / 3 + r, 0.0f,
				0.0f, r, Color.YELLOW, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - 2 * r, WORLD_HEIGHT / 3 + r,
				0.0f, 0.0f, r, Color.red, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4, WORLD_HEIGHT / 3 + r, 0.0f,
				0.0f, r, Color.orange, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + r, WORLD_HEIGHT / 3 + r, 0.0f,
				0.0f, r, Color.green, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + 2 * r, WORLD_HEIGHT / 3 + r,
				0.0f, 0.0f, r, Color.blue, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + 3 * r, WORLD_HEIGHT / 3 + r,
				0.0f, 0.0f, r, Color.pink, 1, linearDragForce, 4));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - .2f, WORLD_HEIGHT / 3 + 2 * r,
				0.0f, 0.0f, r, Color.red, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r, Color.cyan, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + r, WORLD_HEIGHT / 3 + 2 * r,
				0.0f, 0.0f, r, Color.gray, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + 2 * r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r, Color.magenta, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 + 3 * r - .2f,
				WORLD_HEIGHT / 3 + 2 * r, 0.0f, 0.0f, r, Color.yellow, 1, linearDragForce, 4));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r, WORLD_HEIGHT / 3 + 4 * r,
				0.0f, 0.0f, r, Color.YELLOW, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r + r,
				WORLD_HEIGHT / 3 + 4 * r, 0.0f, 0.0f, r, Color.blue, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r + 2 * r,
				WORLD_HEIGHT / 3 + 4 * r, 0.0f, 0.0f, r, Color.pink, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r + 3 * r,
				WORLD_HEIGHT / 3 + r * 4, 0.0f, 0.0f, r, Color.orange, 1, linearDragForce, 4));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - .1f, WORLD_HEIGHT / 3 + 5 * r,
				0.0f, 0.0f, r, Color.green, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - .1f + r,
				WORLD_HEIGHT / 3 + 5 * r, 0.0f, 0.0f, r, Color.red, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - .1f + 2 * r,
				WORLD_HEIGHT / 3 + 5 * r, 0.0f, 0.0f, r, Color.cyan, 1, linearDragForce, 4));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r + r,
				WORLD_HEIGHT / 3 + 6 * r, 0.0f, 0.0f, r, Color.blue, 1, linearDragForce, 4));
		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - r + 2 * r,
				WORLD_HEIGHT / 3 + 6 * r, 0.0f, 0.0f, r, Color.magenta, 1, linearDragForce, 4));

		polygons.add(new BasicPolygon((WORLD_WIDTH - WORLD_WIDTH / 8) - WORLD_WIDTH / 4 - .1f + r,
				WORLD_HEIGHT / 3 + 7 * r, 0.0f, 0.0f, r, Color.orange, 1, linearDragForce, 6));
		whiteParticle = new BasicParticle(0 + 2 * r, (WORLD_HEIGHT / 4) + r / 2, 0, 0, r / 2, Color.white, 2 * 4,
				linearDragForce);
		particles.add(whiteParticle);
		t_arrow = new TargetArrow(getWhiteParticlePos(), getWhiteParticlePos()); // dashed ray
	}

	public void update() {
		float r = .3f;
		float linearDragForce = .02f;
		int VELOCITY_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
		int POSITION_ITERATIONS = NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
		mousepressed = false;
		if (whiteParticle.body.getPosition().y > 0 + WORLD_HEIGHT / 3.5) {
			try {
				whiteParticle = new BasicParticle(0 + 2 * r, (WORLD_HEIGHT / 4) + r / 2, 0, 0, r / 2, Color.white,
						2 * 4, linearDragForce);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			particles.add(whiteParticle);

		}
		for (BasicParticle p : particles) {
			p.notificationOfNewTimestep();
		}

		for (BasicPolygon p : polygons) {
			p.notificationOfNewTimestep();

		}

		world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

		if (mouse_listener.isMouseButtonPressed() == true) {

			mousepressed = true;
			TargetArrow.setStartPos(getWhiteParticlePos());
			TargetArrow.setEndPos(mouse_listener.getWorldCoordinatesOfMousePointer());
		}

		else {
			TargetArrow.setStartPos(getWhiteParticlePos());
			TargetArrow.setEndPos(getWhiteParticlePos());
		}
		if (mouse_listener.isMouseButtonReleased() == true) {
// setting the position for dashed ray
			Vec2 endpos = new Vec2(convertScreenXtoWorldX(mouse_listener.getStartx() + mouse_listener.getEndx() / 2),
					convertScreenYtoWorldY(Math.min(mouse_listener.getStarty(), mouse_listener.getEndy()) - 120));
			Vec2 vel_new = endpos.sub(getWhiteParticlePos());
			whiteParticle.body.setLinearVelocity(vel_new.mul(2));
			whiteParticle.body.applyForceToCenter(new Vec2(0, 1));// adding forces to make it follow a curved path
			whiteParticle.notificationOfNewTimestep();
			TargetArrow.setStartPos(getWhiteParticlePos());
			TargetArrow.setEndPos(getWhiteParticlePos());

			mouse_listener.setMouseButtonReleased(false);
		}
		score = 0;
		for (int i = 0; i < polygons.size(); i++) {
			float posY = polygons.get(i).body.getPosition().y;
			if (posY < WORLD_HEIGHT / 3) {
				score = score + 1;
			}
		}
		if (score == polygons.size()) {
			success = true;
		}

	}

	public void startThread(final MainEngine game1, final BasicView_L3 view) throws InterruptedException {
		int cnt = 0;
		final MainEngine game = game1;
		while (true) {
			game.update();
			score = 0;
			for (int i = 0; i < polygons.size(); i++) {
				float posY = polygons.get(i).body.getPosition().y;
				if (posY < WORLD_HEIGHT / 3) {
					score = score + 1;
				}
			}
			if (score == polygons.size()) {
				isGameOver = true;
			}
			view.repaint();
			view.requestFocus();
			Toolkit.getDefaultToolkit().sync();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
			}

		}
	}

	private Vec2 getWhiteParticlePos() {
		// TODO Auto-generated method stub
		return whiteParticle.body.getPosition();
	}

}
