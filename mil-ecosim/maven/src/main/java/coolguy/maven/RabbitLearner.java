package coolguy.maven;

import java.util.ArrayList;
import java.util.Set;

import com.github.chen0040.rl.learning.qlearn.QLearner;
import com.github.chen0040.rl.utils.IndexValue;
public class RabbitLearner {
	// if the there is no box then that box is -1
	final public int STATE_COUNT =12;
	int action_count=4;
	private final QLearner agent;
	public RabbitLearner() {
		this.agent =new QLearner((int)Math.pow(2,(int) STATE_COUNT),action_count);
	}
	public void act(int stateNum,Set<Integer> possibleMoves) {
		ArrayList<Integer> possibleMovesList =new ArrayList<Integer>(possibleMoves);
		
		IndexValue i =agent.selectAction(stateNum, possibleMoves);
		int action =i.getIndex();
		System.out.println("Value: "+i.getValue());
		 
		
		
		//return 1; 
	}
	public void updateStrategy() {
		
	}

}
