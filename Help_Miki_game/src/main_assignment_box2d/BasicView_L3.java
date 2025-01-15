package main_assignment_box2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.jbox2d.common.Vec2;

import main_assignment_box2d.MainEngine.LayoutMode;

import javax.imageio.ImageIO;

public class BasicView_L3 extends JComponent {
	private static final int COUNTDOWN_DURATION = 3000;
	private static final int COUNTDOWN_INTERVAL = 1000;
	private static final int COUNTDOWN_FONT_SIZE = 90;
	public static final int TIMER_DURATION = 60;
	public static final int TIMER_DELAY = 1000;
	public int timeLeft = TIMER_DURATION;
	public static Timer timer_left;

	// Countdown variables
	private int countdownValue = 4;
	private Timer countdownTimer;
	public static final Color BG_COLOR = Color.BLACK;
	private Image backgroundImage, trees, clouds;

	private boolean showYouWonMessage = true;
	public int cameraX = 5, cameraY = 5;
	public static Vec2 cameraPosition;
	public static Timer timer;
	private int i = 0;

	private MainEngine game;
	private Clip beepClip, cheer;

	public BasicView_L3(MainEngine game) throws IOException {
		int delay = 1000;

		this.game = game;
		backgroundImage = ImageIO.read(getClass().getResource("bgsky.png"));
		trees = ImageIO.read(getClass().getResource("trees.png"));
		clouds = ImageIO.read(getClass().getResource("clouds.png"));
		if (game.layout == LayoutMode.LEVEL3) {
			countdownTimer = new Timer(COUNTDOWN_INTERVAL, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					countdownValue--;
					if (countdownValue == 0) {
						countdownTimer.stop();
					}
					repaint();
					if (countdownValue > 0 && i == 0) {
						playBeepSound();
						i++;
					}
				}
			});
			countdownTimer.setInitialDelay(0);
			countdownTimer.start();
			// Loading beep sound
			
		}

		setFocusable(true);
// winning sound
		InputStream audioSrc = getClass().getResourceAsStream("cheer.wav");
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

		cameraPosition = game.cart.body.getPosition();

// moving camera
		cameraX = (MainEngine.convertWorldXtoScreenX(game.cart.body.getPosition().x)) - 200;
		cameraY = (MainEngine.convertWorldXtoScreenX(game.cart.body.getPosition().y));

		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		BufferedImage buffetrees = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < 6; i++) {
			g.drawImage(trees, -cameraX / 3 * 2 + getWidth() * i - 300, 550, getWidth(), getHeight() - 500, this);
			g.drawImage(clouds, -cameraX / 4 + getWidth() * i - 300, -0, getWidth(), getHeight() - 500, this);

		}

		g.translate(-cameraX, -0);

		for (BasicPolygon p : game.polygons)
			p.draw(g);
		for (Ramp p : game.ramps)
			p.draw(g);
		for (Pole p : game.pole) {
			p.draw(g);
		}
		for (RollerBox p : game.rectangles) {
			p.draw(g);
		}

		for (ElasticConnector c : game.connectors)
			c.draw(g);

		for (BasicParticle p : game.particles)
			p.draw(g);
		for (AnchoredBarrier b : game.barriers)
			b.draw(g);
		for (GroundRect b : game.ground_rect)
			b.draw(g);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		int x = 0, y = -5;

		g.setFont(new Font("Arial", Font.BOLD, 15));
		x = 350;
		y = 300;

		g.setColor(Color.WHITE);
		FontMetrics fontMetrics1 = g.getFontMetrics();
		int textWidth = fontMetrics1
				.stringWidth("Keep me happy for 15 seconds to win by keeping the pole steady and staying on track");

		int x1 = 0, y1 = 0;

		if (countdownValue > 0) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, COUNTDOWN_FONT_SIZE));
			FontMetrics fontMetrics = g.getFontMetrics();
			String countdownText = Integer.toString(countdownValue);
			int textWidth1 = fontMetrics.stringWidth(countdownText);
			int textHeight = fontMetrics.getHeight();
			x1 = (cameraX) / 2;
			y1 = (getHeight() - textHeight) / 2;
			g.drawString(countdownText, x1 + 200, y1);
		} else if (countdownValue == 0) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, COUNTDOWN_FONT_SIZE));
			FontMetrics fontMetrics = g.getFontMetrics();
			String goText = "GO!";
			int textWidth1 = fontMetrics.stringWidth(goText);
			int textHeight = fontMetrics.getHeight();
			x1 = (cameraX) / 2;
			y1 = (getHeight() - textHeight) / 2;
			g.drawString(goText, x1 + 200, y1);

			Timer timer = new Timer(600, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					countdownValue = -1;
				}
			});
			timer.setRepeats(false);
			timer.start();
			Level_3.resumeGame();
		}
		if (Level_3.success) {
			// Flickering the message and adding continue button
			long currentTime = System.currentTimeMillis();
			if (currentTime % 1000 < 500) {
				showYouWonMessage = true;
			} else {
				showYouWonMessage = false;
			}
		}
		if (Level_3.success) {
			Level_3.contButton = new Rectangle(cameraX + 400, 500, 160, 60);
			try {

				cheer.start();

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (showYouWonMessage) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				x1 = cameraX + 180;
				y1 = game.SCREEN_WIDTH / 2 - 100;
				;

			} else {
				g.setFont(new Font("Arial", Font.BOLD, 34));
				x1 = cameraX + 160;
				y1 = game.SCREEN_WIDTH / 2 - 100;
			}
			g.setColor(Color.black);
//				        g.setFont(new Font("Arial", Font.BOLD, 24));
			FontMetrics fontMetrics = g.getFontMetrics();
			int textWidth1 = fontMetrics.stringWidth("That was one blast of a date! Thanks to you!!");
//				        int x = (int) ((game.WORLD_WIDTH / 2 - 20 - textWidth) / 2);
//				        int y = (int) (game.WORLD_HEIGHT / 2);
			g.drawString("That was one blast of a date! Thanks to you!!", x1, y1);

			if (Level_3.contRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_3.contButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_3.contButton);
				g2.setColor(Color.black);

			}
//				        g2.fill(Level_3.contButton);
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_3.contButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 24));
			g2.drawString("CONTINUE", Level_3.contButton.x + 18, Level_3.contButton.y + 40);

		}
		if (Level_3.isGameOver) {
			Level_3.backButton = new Rectangle(cameraX + 400, 500, 160, 60);
			g.setFont(new Font("Arial", Font.BOLD, 28));
			x1 = cameraX + 200;
			y1 = game.SCREEN_WIDTH / 2;

			g.setColor(Color.black);
			FontMetrics fontMetrics = g.getFontMetrics();
			textWidth = fontMetrics.stringWidth("Well, Thanks to you the date was a flop :(");

			g.drawString("Well, Thanks to you the date was a flop :(", x1, y1 - 100);
			if (Level_3.backRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_3.backButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_3.backButton);
				g2.setColor(Color.black);

			}
//			        g2.fill(Level_3.contButton);
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_3.backButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 20));
			g2.drawString("BACK TO MENU", Level_3.backButton.x + 4, Level_3.backButton.y + 38);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return MainEngine.FRAME_SIZE;
	}

	private void playBeepSound() {
		try {
			InputStream audioSrc = getClass().getResourceAsStream("beep.wav");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(audioSrc);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			Clip beepClip = AudioSystem.getClip();
			beepClip.open(audioInputStream);
			beepClip.setFramePosition(0);
			beepClip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public synchronized void updateGame(MainEngine game) {
		this.game = game;
	}
}