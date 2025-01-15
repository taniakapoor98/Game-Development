package main_assignment_box2d;

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

public class BasicView_L4 extends BasicView_L3 {

	private Image sad, happy, neutral;

	private static final int COUNTDOWN_DURATION = 3000;
	private static final int COUNTDOWN_INTERVAL = 1000;
	private static final int COUNTDOWN_FONT_SIZE = 90;
	public static final int TIMER_DURATION = 15;
	public static final int TIMER_DELAY = 1000;
	public static int timeLeft = TIMER_DURATION;

	// Countdown variables
	private int countdownValue = 4;
	private Timer countdownTimer;

	// Beeping sound clip
	private Clip beepClip;
	public static final Color BG_COLOR = Color.BLACK;
	private Image backgroundImage;
	private int i = 0;
	private boolean showYouWonMessage = true;

	private MainEngine game;
	private Clip cheer;
	public static Timer timer_left;

	public BasicView_L4(MainEngine game) throws IOException {
		super(game);
		this.game = game;
		timer_left = new Timer(TIMER_DELAY, e -> {
			if (timeLeft > 0) {
				timeLeft--;
			} else {

				((Timer) e.getSource()).stop();

			}
			if (game.isGameOver || Level_1.success)
				((Timer) e.getSource()).stop();

		});
		this.game = game;
		backgroundImage = ImageIO.read(getClass().getResource("background2.png"));

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
		sad = ImageIO.read(getClass().getResource("sad.png"));
		happy = ImageIO.read(getClass().getResource("happy.png"));
		neutral = ImageIO.read(getClass().getResource("neutral.png"));
		BufferedImage buffer = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

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

		for (BasicPolygon p : game.polygons)
			p.draw(g);
		for (Pole p : Level_4.pole) {
			p.draw(g);
		}
		for (BigCart p : Level_4.rectangles1) {
			p.draw(g);
		}

		for (ElasticConnector c : game.connectors)
			c.draw(g);
		for (AnchoredBarrier b : game.barriers)
			b.draw(g);
		for (BasicParticle p : Level_4.particles)
			p.draw(g);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Time left: 00:" + String.format("%02d", timeLeft), 410, 80);
		int x = 0, y = 0;

		g.setFont(new Font("Arial", Font.BOLD, 15));
		x = 350;
		y = 300;

		g.setColor(Color.WHITE);
		FontMetrics fontMetrics1 = g.getFontMetrics();
		int textWidth = fontMetrics1.stringWidth(
				"Keep her mother happy for 15 seconds to win by keeping the pole steady and staying on track");

		g.drawString("Keep her mother happy for 15 seconds to win by keeping the pole steady and staying on track", 180,
				40);

		int xm1 = MainEngine.convertWorldXtoScreenX(Level_4.bigCart.body.getPosition().x) - 40;
		int ym1 = MainEngine.convertWorldYtoScreenY(Level_4.bigCart.body.getPosition().y) - 40;

		if ((!Level_4.isGameOver && Level_4.p.body.getPosition().y >= 2.5f) || (Level_4.success)) {
			g.drawImage(happy, xm1, ym1, getWidth() / 12, getHeight() / 12, this);

		} else if (Level_4.p.body.getPosition().y >= 2f && Level_4.p.body.getPosition().y < 2.5f
				&& !Level_4.isGameOver) {
			g.drawImage(neutral, xm1 - 10, ym1 - 23, getWidth() / 9, getHeight() / 7 + 2, this);
		} else if (Level_4.isGameOver) {
			g.drawImage(sad, xm1 - 12, ym1 - 27, getWidth() / 9, getHeight() / 7, this);
		}
// final message 
		if (!game.isGameOver && !game.pole_fell && timeLeft == 0) {
			// Flicker the message every 500 milliseconds (adjust the interval as needed)
			long currentTime = System.currentTimeMillis();
			if (currentTime % 1000 < 500) {
				showYouWonMessage = true;
			} else {
				showYouWonMessage = false;
			}
		}

		int x1 = 0, y1 = 0;

// adding continue button and playing sound
		if (Level_4.success) {
			try {

				cheer.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (showYouWonMessage) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				x1 = 350;
				y1 = 300;

			} else {
				g.setFont(new Font("Arial", Font.BOLD, 34));
				x1 = 330;
				y1 = 300;
			}
			g.setColor(Color.white);
//			        g.setFont(new Font("Arial", Font.BOLD, 24));
			FontMetrics fontMetrics = g.getFontMetrics();
			int textWidth1 = fontMetrics.stringWidth("MIKI IS APPROVED!!");
//			        int x = (int) ((game.WORLD_WIDTH / 2 - 20 - textWidth) / 2);
//			        int y = (int) (game.WORLD_HEIGHT / 2);
			g.drawString("MIKI IS APPROVED!!", x1, y1);

			if (Level_4.contRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_4.contButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_4.contButton);
				g2.setColor(Color.black);

			}
//			        g2.fill(Level_4.contButton);
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_4.contButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 24));
			g2.drawString("CONTINUE", Level_4.contButton.x + 18, Level_4.contButton.y + 40);

		}

		if (Level_4.success) {
			long currentTime = System.currentTimeMillis();
			if (currentTime % 1000 < 500) {
				showYouWonMessage = true;
			} else {
				showYouWonMessage = false;
			}
		}
		if (countdownValue > 0) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, COUNTDOWN_FONT_SIZE));
			FontMetrics fontMetrics = g.getFontMetrics();
			String countdownText = Integer.toString(countdownValue);
			int textWidth1 = fontMetrics.stringWidth(countdownText);
			int textHeight = fontMetrics.getHeight();
			x1 = (getWidth() - textWidth1) / 2;
			y1 = (getHeight() - textHeight) / 2;
			g.drawString(countdownText, x1, y1);
		} else if (countdownValue == 0) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, COUNTDOWN_FONT_SIZE));
			FontMetrics fontMetrics = g.getFontMetrics();
			String goText = "GO!";
			int textWidth1 = fontMetrics.stringWidth(goText);
			int textHeight = fontMetrics.getHeight();
			x1 = (getWidth() - textWidth1) / 2;
			y1 = (getHeight() - textHeight) / 2;
			g.drawString(goText, x1, y1);
// starting animation
			Timer timer = new Timer(600, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					countdownValue = -1;
				}
			});
			timer.setRepeats(false);
			timer.start();

			timer_left.start();
			Level_4.resumeGame();
		}
		if (Level_4.isGameOver) {
			g.setFont(new Font("Arial", Font.BOLD, 28));
			x1 = 140;
			y1 = 300;

			g.setColor(Color.white);
//			        g.setFont(new Font("Arial", Font.BOLD, 24));
			FontMetrics fontMetrics = g.getFontMetrics();
			textWidth = fontMetrics.stringWidth("Huhh..YOU MADE HER MOTHER DISSAPOINTED :(");
//			        int x = (int) ((game.WORLD_WIDTH / 2 - 20 - textWidth) / 2);
//			        int y = (int) (game.WORLD_HEIGHT / 2);
			g.drawString("Huhh..YOU MADE HER MOTHER DISSAPOINTED :(", x1, y1);
			if (Level_4.backRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_4.backButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_4.backButton);
				g2.setColor(Color.black);

			}
//			        g2.fill(Level_4.contButton);
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_4.backButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 20));
			g2.drawString("BACK TO MENU", Level_4.backButton.x + 4, Level_4.backButton.y + 38);
		}

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

	@Override
	public Dimension getPreferredSize() {
		return MainEngine.FRAME_SIZE;
	}

	public synchronized void updateGame(MainEngine game) {
		this.game = game;
	}
}