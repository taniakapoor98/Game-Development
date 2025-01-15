package main_assignment_box2d;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class TargetFlag extends BasicParticle {

	private double angle=0;
	public static final double STEER_RATE = 2 * Math.PI;
	public static final double MAGNITUDE_OF_ENGINE_THRUST_FORCE = 500;

	public TargetFlag(float sx, float sy, float vx, float vy, float radius,Color col, float mass, float linearDragForce) throws IOException {
		super(sx, sy, vx, vy, radius, Color.red, mass, 0);
	}
	
	@Override
	public void draw(Graphics2D g2d) {

		int x = 8800;
		int y = 702;
		g2d.setColor(Color.red);
        g2d.fillRect(x, y, 70,30);
        double stripeHeight = 20;
        g2d.setColor(Color.red);

        g2d.setColor(Color.green);
        g2d.fillRect(x, y , 1, 1);
        int poleWidth =1;
        g2d.setColor(Color.white);
        g2d.fillRect(x , y, poleWidth,100);
	}
	
	
	
}
