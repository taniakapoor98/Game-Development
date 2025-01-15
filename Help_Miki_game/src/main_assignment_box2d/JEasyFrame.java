package main_assignment_box2d;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

public class JEasyFrame extends JFrame {
	/* Author: Norbert Voelker
	 */
	public Component comp;

	public JEasyFrame(Component comp, String title) {
		super(title);
		this.comp = comp;
		getContentPane().add(BorderLayout.CENTER, comp);
		pack();
		this.setVisible(true);
        this.setResizable(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		repaint();
	}
}
