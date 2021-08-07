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
		//infoSystem.out.println("Index/Value: "+i.getIndex()+"/"+i.getValue());
		if(i.getValue() <0 ) {
			return possibleMovesList.get((int)(Math.random()*possibleMovesList.size()));
		}
		
		return i.getIndex() ;
		
		
		 
	}
	public void updateStrategy(int oldState,int newState,int action,SuperRabbitObject rabbit,DynamicPopulationTracker x) {
		System.out.println("OldState: "+oldState+" New State: "+newState);
		double reward=0;
		if(!(rabbit.isAlive(x, false))) {
			reward -=35.0;
		}
		if(rabbit.hydration ==0) {
			reward+=25;
		}else {
			reward-=rabbit.hydration*2;
		}
		if(rabbit.deltaHealth<0) {
			reward+=20;
		}else {
			reward-=rabbit.deltaHealth;
		}
		reward+=1;
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
