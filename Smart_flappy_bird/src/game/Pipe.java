package game;

import java.awt.Color;
import java.awt.Graphics;

public class Pipe {
	public static int pipes_passed = 0;
	float x, v;
	int width, screenW, screenH;
	int gapY, gapH;
	int pipeTotalWidth;
	Color color;
	
	Pipe(float x, float v, int w, int sw, int sh, int gy, int gh, Color c, int ptw){
		this.x = x;
		this.v = v;
		width = w;
		screenW = sw;
		screenH = sh;
		gapY = gy;
		gapH = gh;
		color = c;
		pipeTotalWidth = ptw;
	}
	
	public void tick() {
		x += v;
		if(x+width < 0) {
			pipes_passed++;
			x = pipeTotalWidth-width;
			gapY = (int)(Math.random()*(screenH-gapH));
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, 0, width, gapY);
		g.fillRect((int)x, gapY+gapH, width, screenH-(gapY+gapH));
	}
	
}
