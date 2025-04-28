package ai;

public class Brain {
	static final float BOUNDS = 10000;
	static final float mutation_rate = 0.02f;
	
	float[] input;
	public float output;
	float[][][] weights;
	float[] weights_out;
	float[][] bias;
	float bias_out;
	float[][] hidden_layers;
	
	public Brain(int hidden_num, int ninputs){
		input = new float[ninputs];
		weights = new float[hidden_num][ninputs][ninputs];
		bias = new float[hidden_num][ninputs];
		weights_out = new float[ninputs];
		hidden_layers = new float[hidden_num][ninputs];
	}
	
	public Brain(Brain b) {
		this(b.weights.length, b.input.length);
	}
	
	public void init() {
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[0].length; j++) {
				for(int k = 0; k < weights[0][0].length; k++) {
					weights[i][j][k] = (float) (Math.random()*2*BOUNDS - BOUNDS);
				}
			}
		}
		
		for(int i = 0; i < weights_out.length; i++) {
			weights_out[i] = (float) (Math.random()*2*BOUNDS - BOUNDS);
		}
		
		for(int i = 0; i < bias.length; i++) {
			for(int j = 0; j < bias[0].length; j++) {
				bias[i][j] = (float) (Math.random()*2*BOUNDS - BOUNDS);
			}
		}
		bias_out = (float) (Math.random()*2*BOUNDS - BOUNDS);
	}
	
	public void mutate() {
		double selector;
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[0].length; j++) {
				for(int k = 0; k < weights[0][0].length; k++) {
					selector = Math.random();
					if(selector <= mutation_rate) {
						weights[i][j][k] = (float) (Math.random()*2*BOUNDS - BOUNDS);
					}
				}
			}
		}
		for(int i = 0; i < weights_out.length; i++) {
			selector = Math.random();
			if(selector <= mutation_rate) {
				weights_out[i] = (float) (Math.random()*2*BOUNDS - BOUNDS);		
			}
		}
		for(int i = 0; i < bias.length; i++) {
			for(int j = 0; j < bias[0].length; j++) {
				selector = Math.random();
				if(selector <= mutation_rate) {
					bias[i][j] = (float) (Math.random()*2*BOUNDS - BOUNDS);		
				}			
			}
		}
		selector = Math.random();
		if(selector <= mutation_rate) {
			bias_out = (float) (Math.random()*2*BOUNDS - BOUNDS);		
		}
	}
	
	public void tick(float[] input) {
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[0].length; j++) {
				for(int k = 0; k < weights[0][0].length; k++) {
					if(i==0){
						hidden_layers[i][j] += weights[i][j][k]*input[k];
					}else {
						hidden_layers[i][j] += weights[i][j][k]*hidden_layers[i-1][k];
					}
				}
				hidden_layers[i][j] += bias[i][j];
				hidden_layers[i][j] = (float) (1/(1+Math.exp(-hidden_layers[i][j])));
			}
		}
		
		for(int i = 0; i < weights_out.length; i++) {
			output += weights_out[i]*hidden_layers[weights.length-1][weights[0].length-1];
		}
		output += bias_out;
		output = (float) (1/(1+Math.exp(-output)));
	}
}
