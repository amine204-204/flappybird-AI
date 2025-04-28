package game;

import javax.swing.JFrame;

public class Main {
	
	static int screenW = 640, screenH = 480;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		GameHandler gh = new GameHandler();

		f.setSize(screenW, screenH);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.add(gh);
	}

}
