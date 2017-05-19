package it.unical.igpe.desktop;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InitialPanel extends JPanel implements Runnable {
	public InitialPanel() {
		setPreferredSize(new Dimension(800,600));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
