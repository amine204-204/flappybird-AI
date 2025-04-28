package game;

import javax.swing.Timer;

import ai.Brain;
import ai.Population;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameHandler extends JPanel{
	private static final long serialVersionUID = 1L;
	
	static final float g = 1.5f;
	static final int nbirds = 100;
	static float startPos = 300;
	static float birdStartPos = 100;


	Population pop;
	
	static Color bird_color = new Color(255, 250, 80);
	static Color pipe_color = new Color(150, 250, 100);
	
	ArrayList<Pipe> p;
	int pipeW, pipeG, pipeS, pipeV;
	int npipe;
	
	boolean s0 = false;
	boolean s1 = false;
	
	int generation_num = 0;
	int best = 0;
	Pipe closest;
	Timer timer;
	
	GameHandler(){
		setBackground(new Color(100, 150, 255));
		timer = new Timer(1000/60, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
				repaint();
			}
		});
		
		pipeW = 70; pipeG = 100; pipeV = -5; pipeS = 250;
		p = new ArrayList<>();
		npipe = (int) Math.floor(Main.screenW/(pipeW+pipeS)+1)+1;
		for(int i = 0; i < npipe; i++) {
			p.add(new Pipe(startPos+i*(pipeW+pipeS), pipeV, pipeW, Main.screenW, Main.screenH, (int)(Math.random()*(Main.screenH-pipeG)), pipeG, pipe_color, npipe*(pipeW+pipeS)));
		}
		
		pop = new Population(nbirds);
		for(int i = 0; i < nbirds; i++) {
			pop.add( new Bird(birdStartPos, 100, 0, 0, 10, g, bird_color, new Brain(5, 5)));
		}
		
		timer.start();

	}
	
	public boolean collision(Bird b, Pipe p) {
		Rectangle bird_box = new Rectangle((int)(b.x-b.radius), (int)(b.y-b.radius), 2*b.radius, 2*b.radius);
		Rectangle top_pipe_box = new Rectangle((int)(p.x), 0, p.width, p.gapY);
		Rectangle down_pipe_box = new Rectangle((int)(p.x), p.gapY+p.gapH, p.width, p.screenH-p.gapY-p.gapH);
		return bird_box.intersects(top_pipe_box) || bird_box.intersects(down_pipe_box);
	}
	
	public void reset_pipe() {
		p.clear();
		for(int i = 0; i < npipe; i++) {
			p.add(new Pipe(startPos+i*(pipeW+pipeS), pipeV, pipeW, Main.screenW, Main.screenH, (int)(Math.random()*(Main.screenH-pipeG)), pipeG, pipe_color, npipe*(pipeW+pipeS)));
		}
		Pipe.pipes_passed = 0;
	}
	
	public void tick() {
		best = Math.max(Pipe.pipes_passed, best);

		closest = null;
		float d = Float.POSITIVE_INFINITY;
		for(Pipe pipe : p) {
			if(pipe.x - birdStartPos + pipeW <= d && pipe.x - birdStartPos + pipeW>= 0) {
				d = pipe.x - birdStartPos;
				closest = pipe;
			}
		}
		pop.tick(closest);
		for(Pipe pipe : p) {
			for(int i = 0; i < pop.pop.size(); i++) {
				Bird b = pop.pop.get(i);
				if(collision(b, pipe) || b.y < b.radius || b.y > Main.screenH-b.radius) {
					pop.kill(i);
					break;
				}
			}
		}
		for(Pipe pipe : p) {
			pipe.tick();
		}
		if(pop.isDead()) {
			pop.nextGen();
			generation_num++;
			reset_pipe();

		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//b.render(g);
		for(Pipe pipe : p) {
			pipe.render(g);
		}
		pop.render(g);
		
		g.setColor(Color.white);
		g.setFont(new Font("Calibri", Font.BOLD, 16));
		g.drawString("Generation: " + generation_num, 20, 20);
		g.drawString("Best: " + best, 20, 40);
		
		g.setColor(Color.red);
		g.drawRect((int)closest.x, 0, pipeW, Main.screenH);
	}

}
