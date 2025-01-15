package main_assignment_box2d;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.*;

public class AudioPlayer {
	private static boolean musicPlayed = false;
	private static FloatControl musicVolumeControl;

	public AudioPlayer() {
	}

	public AudioPlayer(String file) {
		if (!musicPlayed) {
			playMusic(file);
			musicPlayed = true;
		}
	}

	public void playMusic(String file) {
		try {
			// Accessing the audio file
			InputStream inputStream = AudioPlayer.class.getResourceAsStream(file);
			if (inputStream != null) {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(new BufferedInputStream(inputStream));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				// volume control
				FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volumeControl.setValue(-8.0f);

				clip.addLineListener(new LineListener() {
					@Override
					public void update(LineEvent event) {
						if (event.getType() == LineEvent.Type.STOP) {
							// Restarting the music when it finishes
							clip.setFramePosition(0);
							clip.start();
						}
					}
				});

				clip.start();

				musicVolumeControl = volumeControl;
			} else {
				System.err.println("File not found!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playSound(String file) {
		try {
			InputStream inputStream = AudioPlayer.class.getResourceAsStream(file);
			if (inputStream != null) {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(new BufferedInputStream(inputStream));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);

				clip.loop(0);
			} else {
				System.err.println("File not found!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
