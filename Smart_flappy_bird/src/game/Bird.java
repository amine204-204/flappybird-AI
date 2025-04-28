package game;

import java.awt.Color;
import java.awt.Graphics;

import ai.Brain;

public class Bird {
	float x, y, v, a;
	int radius;
	float g;
	Color color;
	
	//Genetic Algo
	public Brain brain;
	public float fitness;
	
	Bird(float x, float y, float v, float a, int r, float g, Color c, Brain b){
		this.x = x;
		this.y = y;
		this.v = v;
		this.a = a;
		radius = r;
		this.g = g;
		color = c;
		
		//Genetic Algo
		brain = new Brain(b);
		brain.init();
	}
	
	public Bird(Brain brain){
		this(GameHandler.birdStartPos, Main.screenH/2, 0, 0, 10, GameHandler.g, GameHandler.bird_color, new Brain(brain));
	}
	
	public Bird(Bird b) {
		this(b.x, b.y, b.v, b.a, b.radius, b.g, b.color, b.brain);
	}
	
	public void jump() {
		v = -10;
	}
	
	public void mutate() {
		brain.mutate();
	}
	
	public void tick(Pipe closest) {
		a = g;
		v += a;
		y += v;
		
		//Genetic Algo
		brain.tick(new float[] {y, v, closest.x, closest.gapY, closest.gapY+closest.gapH});
		if(brain.output >= 0.5) jump();
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(x-radius), (int)(y-radius), 2*radius, 2*radius);
	}
}
