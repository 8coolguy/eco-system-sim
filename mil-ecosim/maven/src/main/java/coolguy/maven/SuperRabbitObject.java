package coolguy.maven;


public class SuperRabbitObject extends RabbitObject {

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
		

		int n =map.length-(int)(this.speed);
    	int m =(int)(this.speed)-1;
    	//map only needed for super rabbit
    	if (x>=n && y >=n){ 
    	//go in diretion 2 or 3
    		return (int)(Math.random()*2)+2;
    	}
    	else if(x<=m && y<=m) {
    	//go in direction 1 or 0
    		return (int)(Math.random()*2);
    	}
    	else if(x>=n&& y<=m ) {
    	//12
    		return (int)(Math.random()*2)+1;
    	}
    	else if(x<=m&&y>=n) {
    	//03
    		int s=(int)(Math.random()*2);
    		if(s==1)
    			return 3;
    		else 
    			return s;
    	}
    	else if (x<=m) {
    		//013
    		int s=(int)(Math.random()*3);
    		if(s==2)
    			return 3;
    		else 
    			return s;
    	}
    	else if(x>=n) {
    		//213
    		return ((int)(Math.random()*3))+1;
    	}
    	else if(y<=m) {
    		//021
    		return((int)(Math.random()*3));
    	}
    	else if(y>=n) {
    		//320
    		int s =((int)(Math.random()*3));
    		if(s==1) {
    			return 3;
    		}else {
    			return s;
    		}
    	}
    	else 
    		return (int)(Math.random()*4);//use ml here only myb
    		
	}
}
