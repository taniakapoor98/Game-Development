package main_assignment_box2d;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.jbox2d.common.Vec2;


import javax.imageio.ImageIO;

public class BasicViewMenu extends BasicView_L3 {
	
	public static final Color BG_COLOR = Color.BLACK;
    private Image backgroundImage;

	private boolean showYouWonMessage = true;

	private MainEngine game;

	public BasicViewMenu(MainEngine game) throws IOException {
		super(game);
		this.game = game;
		backgroundImage = ImageIO.read(getClass().getResource("menu.png"));
	}
	
	@Override
	public void paintComponent(Graphics g0) {
		MainEngine game;
		synchronized(this) {
			game=this.game;
		}
		Graphics2D g = (Graphics2D) g0;
		Graphics2D g2 = (Graphics2D) g0;
        BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2.setColor(new Color(128, 198, 255, 80)); // 128 = 50% opacity
        g2.fill(Menu.playButton);
        g2.fill(Menu.quitButton);
        g2.setComposite(AlphaComposite.SrcOver); 
        // adding hovering effect
        if(Menu.playRegion) {
        	g2.setColor(new Color(0, 198, 255, 255));
        	g2.fill(Menu.playButton);
        }
        else if(Menu.quitRegion) {
        	g2.setColor(new Color(0, 198, 255, 255));
        	g2.fill(Menu.quitButton);
        }
        else {
        	g2.setColor(new Color(128, 198, 255, 80)); 
            g2.fill(Menu.playButton);
            g2.fill(Menu.quitButton);
        }
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4));       
        g2.draw(Menu.playButton);
        g2.draw(Menu.quitButton);
        g2.setFont(new Font("Segoe UI Black", Font.PLAIN, 38));
        g2.drawString("PLAY",Menu.playButton.x+15 , Menu.playButton.y+42); // drawing buttons
        g2.drawString("QUIT",Menu.quitButton.x+15 , Menu.quitButton.y+42);

        

		int x = 0,y=0;

        if (true) {
		    if (showYouWonMessage) {
		    	g.setFont(new Font("Segoe UI Black", Font.BOLD, 56));
		    	x = 340;
		    	y=100;
		    			
		    }
		    else
		    	{g.setFont(new Font("Segoe UI Black", Font.BOLD, 58));
		    	x = 340-5;
		    	y=100;
		    	}
		        g.setColor(Color.WHITE);
//		        g.setFont(new Font("Arial", Font.BOLD, 24));
		        FontMetrics fontMetrics = g.getFontMetrics();
		        int textWidth = fontMetrics.stringWidth("HELP MIKI!");
//		        int x = (int) ((game.WORLD_WIDTH / 2 - 20 - textWidth) / 2);
//		        int y = (int) (game.WORLD_HEIGHT / 2);
		        g.drawString("HELP MIKI!",x,y);
		    
		}

		if (true) {
		    long currentTime = System.currentTimeMillis();
		    if (currentTime % 1000 < 500) {
		        showYouWonMessage = true;
		    } else {
		        showYouWonMessage = false;
		    }
		}
			
			
	}

	@Override
	public Dimension getPreferredSize() {
		return MainEngine.FRAME_SIZE;
	}
	
	public synchronized void updateGame(MainEngine game) {
		this.game=game;
	}
}