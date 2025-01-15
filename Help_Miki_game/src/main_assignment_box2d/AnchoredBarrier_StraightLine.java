package main_assignment_box2d;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class AnchoredBarrier_StraightLine extends AnchoredBarrier {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied: 
	 */

	private Vec2 startPos,endPos;
	private final Color col;
	public final Body body;


	public AnchoredBarrier_StraightLine(float startx, float starty, float endx, float endy, Color col) {
		
		
		startPos=new Vec2(startx,starty);
		endPos=new Vec2(endx,endy);

		World w=MainEngine.world;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = new Vec2(startx,starty);
		Body body = w.createBody(bodyDef);
		this.body=body;
		Vec2[] vertices = new Vec2[] { new Vec2(), new Vec2(endx-startx, endy-starty) };
		
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(vertices, vertices.length);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chainShape;
		
//		body.createFixture(chainShape, 0);
		body.createFixture(fixtureDef);
		fixtureDef.friction = 20f;// this is surface friction;
		fixtureDef.restitution = .4f;
		
		
		this.col=col;
	}

	@Override
	public void draw(Graphics2D g) {
		int x1 = MainEngine.convertWorldXtoScreenX(startPos.x);
		int y1 = MainEngine.convertWorldYtoScreenY(startPos.y);
		int x2 = MainEngine.convertWorldXtoScreenX(endPos.x);
		int y2 = MainEngine.convertWorldYtoScreenY(endPos.y);
		//*********added to create stroke
		Stroke stroke = new BasicStroke(5.0f); 
        g.setStroke(stroke);
		g.setColor(col);
		g.drawLine(x1, y1, x2, y2);
	}


}
