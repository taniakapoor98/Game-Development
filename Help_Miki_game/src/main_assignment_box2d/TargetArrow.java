package main_assignment_box2d;

import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
//-Author Tania

public class TargetArrow {
	private static Vec2 startPos,endPos,unitNormal,unitTangent;
	private static double barrierLength = 0;
	private static Double barrierDepth ;
	
	

	public static Vec2 getStartPos() {
		return startPos;
	}




	public static void setStartPos(Vec2 startPos) {
		TargetArrow.startPos = startPos;
	}

	

	public static Vec2 getEndPos() {
		return endPos;
	}




	public static void setEndPos(Vec2 endPos) {
		TargetArrow.endPos = endPos;
	}




	public TargetArrow(Vec2 startPos, Vec2 endPos) {
		this.startPos = startPos;
		this.endPos = endPos;
	}



	
	public void draw(Graphics2D g) {
		int x1 = MainEngine.convertWorldXtoScreenX(startPos.x);
		int y1 = MainEngine.convertWorldYtoScreenY(startPos.y);
		int x2 = MainEngine.convertWorldXtoScreenX(endPos.x);
		int y2 = MainEngine.convertWorldYtoScreenY(endPos.y);

        Body whiteBody = MainEngine.whiteParticle.body;

        if( whiteBody.getPosition().x!=0+2*.3f)
    		g.setColor(new Color(1f,0f,0f,0 ));
        

        drawInvertedParabola(g, startPos, endPos);

	}
	
	private void drawInvertedParabola(Graphics2D g2d, Vec2 startPos, Vec2 endPos) {
        int startX = MainEngine.convertWorldXtoScreenX(startPos.x);
        int startY = MainEngine.convertWorldYtoScreenY(startPos.y);
        int endX = MainEngine.convertWorldXtoScreenX(endPos.x);
        int endY = MainEngine.convertWorldYtoScreenY(endPos.y);

        int controlX = (startX + endX) / 2;
        int controlY = Math.min(startY, endY) - 100;
        Body whiteBody = MainEngine.whiteParticle.body;

        	g2d.draw(new QuadCurve2D.Float(startX, startY, controlX, controlY, endX, endY));
    }
	


}