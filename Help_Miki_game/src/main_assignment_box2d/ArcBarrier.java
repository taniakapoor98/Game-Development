package main_assignment_box2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

//**Author: Tania 
public class ArcBarrier extends AnchoredBarrier {
    private List<Vec2> points; 
    private float radius; 
    private int numSegments; 
    private float centerX; 
    private float centerY; 
	public final Body body;

    public ArcBarrier(float centerX, float centerY, float radius, float startAngle, float endAngle, int numSegments) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.points = calculateArcPoints(centerX, centerY, radius, startAngle, endAngle, numSegments);
        this.radius = radius;
        this.numSegments = numSegments;
        World w=MainEngine.world;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = new Vec2(centerX,centerY);
		Body body = w.createBody(bodyDef);
		this.body=body;
        ChainShape chainShape = createChainShape(); // Creating the chain shape representing the arc
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chainShape;
		body.createFixture(fixtureDef);
		fixtureDef.friction = 20f;
		fixtureDef.restitution = .4f;
		
		
    }

    // Method to calculate the points that define the arc
    private List<Vec2> calculateArcPoints(float centerX, float centerY, float radius, float startAngle, float endAngle, int numSegments) {
        List<Vec2> arcPoints = new ArrayList<>();
        float angleIncrement = (endAngle - startAngle) / (numSegments - 1); 
        for (int i = 0; i < numSegments; i++) {
            float angle = startAngle + angleIncrement * i;
            float x = centerX + (float) (radius * Math.cos(Math.toRadians(angle))); 
            float y = centerY + (float) (radius * Math.sin(Math.toRadians(angle))); 
            arcPoints.add(new Vec2(x, y));
        }
        return arcPoints;
    }

    // drawing the arc barrier
    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(123,66,31));

        Vec2 bodyPosition = body.getPosition();
        float bodyX = bodyPosition.x;
        float bodyY = bodyPosition.y;
        
        Path2D.Float path = new Path2D.Float();
        
        // Iterating through the points defining the arc
        for (int i = 0; i < points.size(); i++) {
            Vec2 worldPoint = points.get(i);
            float xWorld = bodyX + worldPoint.x;
            float yWorld = bodyY + worldPoint.y;
            
            int xScreen = MainEngine.convertWorldXtoScreenX(xWorld);
            int yScreen = MainEngine.convertWorldYtoScreenY(yWorld);
            
            if (i == 0) {
                path.moveTo(xScreen, yScreen);
            } else {
                path.lineTo(xScreen, yScreen);
            }
        }
        
        g.draw(path);
    }



    // funtion to creating a chain shape from the arc points
    public ChainShape createChainShape() {
        ChainShape chainShape = new ChainShape();
        Vec2[] vertices = new Vec2[points.size()];
        for (int i = 0; i < points.size(); i++) {
            vertices[i] = points.get(i);
        }
        chainShape.createChain(vertices, vertices.length);
        return chainShape;
    }
}
