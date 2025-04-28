package ai;

import java.awt.Graphics;
import java.util.ArrayList;

import game.Bird;
import game.Pipe;

public class Population {
	public ArrayList<Bird> pop;
	public ArrayList<Bird> dead;
	public int size;
	long start_time;
	public Population(int size) {
		pop = new ArrayList<>();
		dead = new ArrayList<>();
		this.size = size;
		start_time = System.currentTimeMillis();
	}
	
	public void tick(Pipe closest) {
		for(Bird b : pop) {
			b.tick(closest);
			b.fitness = (float) Math.pow(System.currentTimeMillis()-start_time, 2);
		}
	}
	
	public void render(Graphics g) {
		for(Bird b : pop) {
			b.render(g);
		}
	}
	public void nextGen() {
		float best_fitness = Float.MIN_VALUE;
		float fitness_sum = 0;
		Bird bestB = null;
		for(Bird b : dead) {
			fitness_sum += b.fitness;
		}
		
		for(Bird b : dead) {
			b.fitness /= fitness_sum;
			if(b.fitness > best_fitness) {
				best_fitness = b.fitness;
				bestB = b;
			}
			
		}
		pop.add(bestB);
		for(int i = 0; i < size-1; i++) {
			double selector = Math.random();
			double a = 0;
			double b = dead.get(0).fitness;
			int j = 0;
			while(b <= selector) {
				a = b;
				b += dead.get(j+1).fitness;
				j++;
			}
			Bird nextb = new Bird(dead.get(j).brain);
			nextb.mutate();
			pop.add(nextb);
		}
		dead.clear();
		start_time = System.currentTimeMillis();
	}
	
	public boolean isDead() {
		return pop.isEmpty();
	}
	
	public void add(Bird b) {
		pop.add(b);
	}
	
	public void kill(int index) {
		Bird b = pop.remove(index);
		dead.add(b);
	}
	
}
