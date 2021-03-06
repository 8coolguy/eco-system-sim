package coolguy.maven;

import javafx.scene.paint.Color;

public class FoxObject extends RabbitObject {
	final int HUNGER_LIMIT = 13;
	
	int hunger =0;
	int sizeFactor =18;
	double fertilityFactor =.92;
	int speedFox =1;
	int hydrationFactor=5;
	int yDfactor =5;
	
	public FoxObject(int moveNums) {

		super(moveNums);
		this.fertility*=this.fertilityFactor;
		this.size*=sizeFactor;
		this.speed=this.speedFox;
		this.hydrationLimit*=this.hydrationFactor;
		this.youngestDeath*=this.yDfactor;
	}
	public FoxObject(FoxObject femaleParent,FoxObject maleParent,int moveNum) {
		super(femaleParent,maleParent,moveNum);
		this.fertility*=this.fertilityFactor;
		this.size*=sizeFactor;
		this.speed=this.speedFox;
		this.hydrationLimit*=this.hydrationFactor;
		this.youngestDeath*=this.yDfactor;
	}
	
	//new methods
	@Override public void sim1(Color tile) {
		this.turnsmoved++;
    	this.age++;
    	this.reactMove(tile);
    	this.hunger++;
    	if(tile!=Color.BLUE)
    		this.hydration++;
    	else
    		this.hydration =0;
    	
    	
	}
	@Override public boolean isAlive(DynamicPopulationTracker x,boolean update) {
    	boolean result =true;
    	if(this.health <=0) {
    		//infoSystem.out.println("Died from health");
    		result= false;
    	}
    	int randLife = (int)(Math.random()*this.ageDeathRange)+this.youngestDeath;
    	
    	if(this.age >randLife) {
    		//infoSystem.out.println("Died from Age");
    		result= false;
    	}
    	if(this.hydration==this.hydrationLimit) {
    		//infoSystem.out.println("Died from Hydration.");
    		result= false;
    	}
    	if(this.hunger ==this.HUNGER_LIMIT) {
    		//infoSystem.out.println("Died from Hunger.");
    		result=false;
    	}
    	return result;
    }
	public void eat(MapTile tile) {
		for(int i =0;i<tile.MAX_PER_TILE;i++) {
			RabbitObject hunted = tile.showWhere()[i];
			if((!(hunted instanceof FoxObject)) && hunted !=null) {
				if(hunted.getColor() <this.color) {
					//infoSystem.out.println("Age: "+hunted.getAge());
					tile.here[i].isEaten();
					this.hunger =0;
				}
			}
		}
	}
}
