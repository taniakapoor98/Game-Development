package main_assignment_box2d;
import java.io.BufferedInputStream;

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
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;

public class BasicView_L1 extends BasicView_L3 {

	private static final int COUNTDOWN_DURATION = 3000;
	private static final int COUNTDOWN_INTERVAL = 1000;
	private static final int COUNTDOWN_FONT_SIZE = 90;
	public static final int TIMER_DURATION = 60;
	public static final int TIMER_DELAY = 1000;
	public int timeLeft = TIMER_DURATION;

	// Countdown variables
	private int countdownValue = 4;
	private Timer countdownTimer;

	// Beep sound clip
	private Clip beepClip;
	public static final Color BG_COLOR = Color.BLACK;
	private Image backgroundImage;
	private int i = 0;
	private boolean showYouWonMessage = true;

	private MainEngine game;
	public static Timer timer_left;
	

	public BasicView_L1(MainEngine game) throws IOException {
		super(game);
		this.game = game;
		backgroundImage = ImageIO.read(getClass().getResource("court_2.jpg"));

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
		

		timer_left = new Timer(TIMER_DELAY, e -> {
			if (timeLeft > 0) {
				timeLeft--;
			} else {

				if (Level_1.bscore >= 5) {
					System.out.println("You win!");
					Level_1.success = true;
				} else {
					System.out.println("You Lose..");
					game.isGameOver = true;
					AudioPlayer cheer = new AudioPlayer();
					cheer.playSound("gameOver.wav");
				}
				((Timer) e.getSource()).stop();

			}
			if (game.isGameOver || Level_1.success)
				((Timer) e.getSource()).stop();

		});

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

		for (BasicParticle p : game.particles)
			p.draw(g);
		for (BasicPolygon p : game.polygons)
			p.draw(g);

		for (ElasticConnector c : game.connectors)
			c.draw(g);
		for (AnchoredBarrier b : game.barriers)
			b.draw(g);
		try {
			Image basketImage = ImageIO.read(getClass().getResource("basket.png"));
			// draying the basket
			g.drawImage(basketImage, WIDTH / 2 + 630, HEIGHT / 2 + 450, 94, 100, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		int minutes = timeLeft / 60;
		int seconds = timeLeft % 60;
		g.drawString("TIME LEFT: " + String.format("%02d:%02d", minutes, seconds), 40, 90);

		int x = 0, y = 0;

		g.setFont(new Font("Arial", Font.BOLD, 15));
		x = 350;
		y = 300;
		// level description
		g.setColor(Color.WHITE);
		FontMetrics fontMetrics1 = g.getFontMetrics();
		int textWidth = fontMetrics1.stringWidth("Basket 5 balls within a minute to impress her");

		g.drawString("Basket 5 balls within a minute to impress her", 330, 20);
		g.setFont(new Font("Arial", Font.BOLD, 18));

		g.drawString("SCORE: " + Level_1.bscore, 40, 60);

		if (MainEngine.mousepressed == true) {
			g2.setColor(Color.white);
			float[] dashPattern = { 5, 5 }; // 5 pixels on, 5 pixels off
			BasicStroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, dashPattern,
					0);
			g2.setStroke(dashedStroke);
			game.t_arrow.draw(g2);
		}
		int x1 = 0, y1 = 0;

		// printing final success message
		if (Level_1.success) {
			if (showYouWonMessage) {
				g.setFont(new Font("Arial", Font.BOLD, 28));
				x1 = 340;
				y1 = 300;

			} else {
				g.setFont(new Font("Arial", Font.BOLD, 34));
				x1 = 320;
				y1 = 300;
			}
			g.setColor(Color.black);
			FontMetrics fontMetrics = g.getFontMetrics();
			int textWidth1 = fontMetrics.stringWidth("YOU IMPRESSED HER!!");

			g.drawString("YOU IMPRESSED HER!!", x1, y1);
			// adding continue button
			if (Level_1.contRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_1.contButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_1.contButton);
				g2.setColor(Color.black);

			}
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_1.contButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 24));
			g2.drawString("CONTINUE", Level_1.contButton.x + 18, Level_1.contButton.y + 40);

		}

		if (Level_1.success) {
			// Flickering the message every 500 ms
			long currentTime = System.currentTimeMillis();
			if (currentTime % 1000 < 500) {
				showYouWonMessage = true;
			} else {
				showYouWonMessage = false;
			}
		}
		// initial countdown
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

			Timer timer = new Timer(600, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					countdownValue = -1;
				}
			});
			timer.setRepeats(false);
			timer.start();
			Level_1.resumeGame();
			timer_left.start();
		}
		if (Level_1.isGameOver) {
			g.setFont(new Font("Arial", Font.BOLD, 28));
			x1 = 310;
			y1 = 300;

			g.setColor(Color.black);
			FontMetrics fontMetrics = g.getFontMetrics();
			textWidth = fontMetrics.stringWidth("BETTER LUCK NEXT TIME :(");

			g.drawString("BETTER LUCK NEXT TIME :(", x1, y1);
			if (Level_1.backRegion) {
				g2.setColor(new Color(255, 255, 255, 128));
				g2.fill(Level_1.backButton);
				g2.setColor(Color.black);

			}

			else {
				g2.setColor(new Color(128, 0, 25, 70));
				g2.fill(Level_1.backButton);
				g2.setColor(Color.black);

			}
			g2.setStroke(new BasicStroke(2));
			g2.draw(Level_1.backButton);
			g2.setColor(Color.black);

			g2.setFont(new Font("MANGAL", Font.PLAIN, 20));
			g2.drawString("BACK TO MENU", Level_1.backButton.x + 4, Level_1.backButton.y + 38);
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