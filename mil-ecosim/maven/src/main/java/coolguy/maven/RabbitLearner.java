package coolguy.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.github.chen0040.rl.learning.qlearn.QLearner;
import com.github.chen0040.rl.utils.IndexValue;
public class RabbitLearner {
	//Article on what I thought I would be doing
	//https://towardsdatascience.com/simple-reinforcement-learning-q-learning-fcddc4b6fe56
	//reference to the class that package that someone built for Qlearning:
	//https://github.com/chen0040/java-reinforcement-learning
	// if the there is no box then that box is -1
	final public int STATE_COUNT =16;
	int action_count=4;
	private final QLearner agent;
	public RabbitLearner() {
		this.agent =new QLearner((int)Math.pow(2,(int) STATE_COUNT),action_count);
	}
	public RabbitLearner(File file) {
		String loadMl=null;
		try {
		loadMl = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		}catch(IOException e ) {
			e.printStackTrace();
		}
		this.agent =QLearner.fromJson(loadMl);
		
	}
	public int act(int stateNum,Set<Integer> possibleMoves) {
		ArrayList<Integer> possibleMovesList =new ArrayList<Integer>(possibleMoves);
		
		IndexValue i =agent.selectAction(stateNum, possibleMoves);
		if(i.getValue()<0 ) {
			System.out.println("^^^Exploring^^^");
			return possibleMovesList.get((int)(Math.random()*possibleMovesList.size()));
		}
		System.out.println("***Exploiting***");
		return i.getIndex() ;
	}
	public void updateStrategy(int oldState,int newState,int action,SuperRabbitObject rabbit,DynamicPopulationTracker x) {
		//infoSystem.out.println("OldState: "+oldState+" New State: "+newState);
		//the reward needs to be based only on the action id eaten pretty much,color,hydration
		double reward=0;
		if(rabbit.hydration ==0) {
			reward+=50;
		}
		if(rabbit.deltaHealth == rabbit.greenEffect) {
			reward+=75;
		}else {
			reward-=3*rabbit.deltaHealth;
		}
		if(rabbit.eaten) {
			reward-=100;
		}
		
		System.out.println("Reward: "+reward);
		this.agent.update(oldState, action, newState, reward);
	}
	public void quit() {
	      try {
	          FileWriter file = new FileWriter("output.json");
	          file.write(agent.toJson());
	          file.close();
	       } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }
	}

}
