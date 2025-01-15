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
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;

public class BasicViewMission extends BasicView_L3 {

	public static final Color BG_COLOR = Color.BLACK;
	private Image backgroundImage;

	private boolean showYouWonMessage = true;

	private MainEngine game;
	private Clip cheer;

	public BasicViewMission(MainEngine game) throws IOException {
		super(game);
		this.game = game;
		if (MainEngine.getMission_cnt() <= 4) {
			backgroundImage = ImageIO.read(getClass().getResource("mission.png"));
		} else {

			backgroundImage = ImageIO.read(getClass().getResource("bgf.png"));
			InputStream audioSrc = getClass().getResourceAsStream("winning.wav");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(audioSrc);
			AudioInputStream audioInputStream;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
				cheer = AudioSystem.getClip();
				cheer.open(audioInputStream);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cheer.start();
		}
	}

	@Override
	public void paintComponent(Graphics g0) {
		MainEngine game;
		synchronized (this) {
			game = this.game;
		}
		Graphics2D g = (Graphics2D) g0;
		Graphics2D g2 = (Graphics2D) g0;
		BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		g2.setComposite(AlphaComposite.SrcOver);

		if (MainEngine.getMission_cnt() <= 4) {
			if (Mission.playRegion) {
				g2.fill(Mission.playButton);
				g2.setColor(Color.red);

			}

			else {
				g2.setColor(new Color(128, 198, 255, 80));
				g2.fill(Mission.playButton);
				g2.setColor(Color.white);

			}
			g2.setStroke(new BasicStroke(2));
			g2.draw(Mission.playButton);
			g2.setColor(Color.white);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 30));
			g2.drawString("START", Mission.playButton.x + 18, Mission.playButton.y + 42);
		}

		// Drawing card-style rectangle
		int rectWidth = 450;
		int rectHeight = 600;
		int rectX = (MainEngine.SCREEN_WIDTH - rectWidth) / 2 + 300;
		int rectY = (MainEngine.SCREEN_HEIGHT - rectHeight) / 2 + 100;

		String paragraph = Mission.getMissionText(MainEngine.getMission_cnt());
		// Breaking the paragraph into lines to fit within the rectangle
		List<String> lines = new ArrayList<>();
		FontMetrics fontMetrics = g2.getFontMetrics();
		int lineHeight = fontMetrics.getHeight();

		String[] words = paragraph.split("\\s+");
		StringBuilder line = new StringBuilder();
		for (String word : words) {
			if (fontMetrics.stringWidth(line.toString() + word) < rectWidth) {
				line.append(word).append(" ");
			} else {
				lines.add(line.toString());
				line = new StringBuilder(word + " ");
			}
		}
		lines.add(line.toString());

		// Drawing text inside the rectangle
		g2.setColor(Color.white);
		g2.setFont(new Font("MANGAL", Font.PLAIN, 18));
		int textY = rectY + lineHeight;
		for (String lineText : lines) {
			int textX = rectX + (rectWidth - fontMetrics.stringWidth(lineText)) / 4 + 40;
			g2.drawString(lineText, textX, textY);
			textY += lineHeight;
		}

		if (MainEngine.getMission_cnt() == 2) {
			String[] lines1 = { "                          Controls:", "→ = Accelerate, ← = Reverse, ↓ = Brake",
					"                     Spacebar = Flip" };
			g2.setColor(Color.cyan);
			g2.setFont(new Font("Arial", Font.BOLD, 15));

			int lineHeight1 = fontMetrics.getHeight();
			int textX = rectX + (rectWidth - fontMetrics.stringWidth(lines1[0])) / 4;

			for (String line1 : lines1) {
				g2.drawString(line1, textX, textY);
				textY += lineHeight1;
			}
		} else if (MainEngine.getMission_cnt() == 3) {
			String[] lines1 = { "                          Controls:", "        ← = move left, → = move right" };
			g2.setColor(Color.cyan);
			g2.setFont(new Font("Arial", Font.BOLD, 15));

			int lineHeight1 = fontMetrics.getHeight();
			int textX = rectX + (rectWidth - fontMetrics.stringWidth(lines1[0])) / 4;

			for (String line1 : lines1) {
				g2.drawString(line1, textX, textY);
				textY += lineHeight1;
			}
		}

		int x = 0, y = 0;

		if (true && MainEngine.getMission_cnt() <= 4) {

			if (showYouWonMessage) {
				g.setFont(new Font("Segoe UI Black", Font.BOLD, 56));
				x = 340;
				y = 100;

			} else {
				g.setFont(new Font("Segoe UI Black", Font.BOLD, 58));
				x = 340 - 5;
				y = 100;
			}
			g.setColor(Color.WHITE);
			FontMetrics fontMetrics1 = g.getFontMetrics();
			int textWidth = fontMetrics1.stringWidth("MISSION " + MainEngine.getMission_cnt());

			g.drawString("MISSION " + MainEngine.getMission_cnt(), x, y);

		} else {
			for (BasicPolygon p : game.polygons)
				p.draw(g);
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI Black", Font.BOLD, 50));

			g.drawString("Thank you so much for helping Miki!", 40, MainEngine.SCREEN_WIDTH / 2 + 100);
			g.drawString("You have been Awesome!!!", 150, MainEngine.SCREEN_WIDTH / 2 + 200);
			if (showYouWonMessage) {
				g.setFont(new Font("Segoe UI Black", Font.BOLD, 80));
				x = 200;
				y = MainEngine.SCREEN_WIDTH / 2 - 100;

			} else {
				g.setFont(new Font("Segoe UI Black", Font.BOLD, 88));
				x = 180;
				y = MainEngine.SCREEN_WIDTH / 2 - 100;
			}
			g.setColor(Color.BLACK);
			FontMetrics fontMetrics1 = g.getFontMetrics();
			int textWidth = fontMetrics1.stringWidth("SHE SAID YES!!");

			g.drawString("SHE SAID YES!!", x, y);

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
		this.game = game;
	}
}