package coolguy.maven;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SuperRabbitObject extends RabbitObject {
	public int lastAction =0;
	public int oldState =0;
	public SuperRabbitObject(int moveNum) {
		super(moveNum);
		
	}
	public SuperRabbitObject(SuperRabbitObject femaleParent, SuperRabbitObject maleParent,int moveNum) {
		super(femaleParent, maleParent,moveNum);
	}
	//only unique method is this
	// i need to get the inputs for the training model
	//classification model returns 0,1,2,3 or other version of the power set based on where it is on the map 
	//color given as an integer
	// color0(right),color1(down),color2(left),color3(up),fox(0),fox(1),fox(2),fox(3),(hydration,deltaheight,age)higher age is better
	//run 100s of runs and then create a classification model from there
	// if by one of the edges than I can create a model with one of the options dupicated and if it picks that it goes down
	
	@Override public int generateDirection(int x ,int y, MapTile[][] map,RabbitLearner ml) {
		//some needed variables 
		int n =map.length-(int)(this.speed);
    	int m =(int)(this.speed)-1;
    	int state = this.getState(x, y, map);;
    	Set<Integer> possibleDirections =new HashSet();
    	//map only needed for super rabbit
    	if (x>=n && y >=n){ 
    	//go in diretion 2 or 3
    		possibleDirections.add(2);
    		possibleDirections.add(3);		
    	}
    	else if(x<=m && y<=m) {
    	//go in direction 1 or 0
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    	}
    	else if(x>=n&& y<=m ) {
    	//12
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    	}
    	else if(x<=m&&y>=n) {
    	//03
    		possibleDirections.add(0);
    		possibleDirections.add(3);
    	}
    	else if (x<=m) {
    		//013
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    		possibleDirections.add(3);
    	}
    	else if(x>=n) {
    		//213
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    		possibleDirections.add(3);
    	}
    	else if(y<=m) {
    		//021
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    	}
    	else if(y>=n) {
    		//320
    		possibleDirections.add(0);
    		possibleDirections.add(2);
    		possibleDirections.add(3);

    	}
    	else {
    		for(int i =0;i<4;i++) {
    			possibleDirections.add(i);
    		}
    	}
    	this.oldState=state;
    	this.lastAction =ml.act(state, possibleDirections);
    	return this.lastAction;
    		
	}
	public int getState(int x, int y, MapTile[][] map) {
		//x is row y is column
		int n =map.length-(int)(this.speed);
    	int m =(int)(this.speed)-1;
    	int pace =(int)(this.speed);
    	int state =0;
    	Set<Integer> possibleDirections =new HashSet();
    	//map only needed for super rabbit
    	if (x>=n && y >=n){ 
    	//go in diretion 2 or 3
    		possibleDirections.add(2);
    		possibleDirections.add(3);
    	}
    	else if(x<=m && y<=m) {
    	//go in direction 1 or 0
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    	}
    	else if(x>=n&& y<=m ) {
    	//12
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    	}
    	else if(x<=m&&y>=n) {
    	//03
    		possibleDirections.add(0);
    		possibleDirections.add(3);
    	}
    	else if (x<=m) {
    		//013
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    		possibleDirections.add(3);
    	}
    	else if(x>=n) {
    		//213
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    		possibleDirections.add(3);
    	}
    	else if(y<=m) {
    		//021
    		possibleDirections.add(0);
    		possibleDirections.add(1);
    		possibleDirections.add(2);
    	}
    	else if(y>=n) {
    		//320
    		possibleDirections.add(0);
    		possibleDirections.add(2);
    		possibleDirections.add(3);
    	}
    	else {
    		for(int i =0;i<4;i++) {
    			possibleDirections.add(i);
    		}
    	}
		for(int it:possibleDirections) {
			if(it==0) {
				state+=(int)(Math.pow(8, it))*map[x+pace][y].returnBit();
				if(map[x+pace][y].isFoxesHere())
					state+=(int)(Math.pow(2, 12+it));
			}
			else if(it==1) {
				state+=(int)(Math.pow(8, it))*map[x][y+pace].returnBit();
				if(map[x][y+pace].isFoxesHere())
					state+=(int)(Math.pow(2, 12+it));
			}
			else if(it==2) {
				state+=(int)(Math.pow(8, it))*map[x-pace][y].returnBit();
				if(map[x-pace][y].isFoxesHere())
					state+=(int)(Math.pow(2, 12+it));
			}else {
				state+=(int)(Math.pow(8, it))*map[x][y-pace].returnBit();
				if(map[x][y-pace].isFoxesHere())
					state+=(int)(Math.pow(2, 12+it));
			}
		}
		return state;	
    	
	}
}
