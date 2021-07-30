package coolguy.maven;

import javafx.scene.paint.Color;

public class  RabbitObject{
    //instance variables for the object rabbit
	final int hydrationLimit =15;
	final int youngestDeath =5;
	final int ageDeathRange =15;
    String gender = new String();//m or f
    double color;//0-10:0black:10 white
    double fertility;//0-1
    
    double health=25;//0-100 starting health goes here
    int age;//0-24  1 per turn
    int maxSize=10;//max each grow to
    int size;//current size per age linear with age
    int turnsmoved;
    public int gen =0;
    
    RabbitObject mom;
    RabbitObject dad; 
    
    //not implemented 
    int hunger;
    double speed;//0-10
    int hydration =0;
    //default contructor for intial rabbit object
    public RabbitObject(int moveNum){
    	//this.id =setId();
        double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        setColor((int)Math.random()*10);
        setFertility(.5);
        setSpeed((int)Math.random()*10);
        

        setSize(1);
        this.turnsmoved =moveNum;
        this.mom=null;
        this.dad =null;
        
    }

	public RabbitObject(RabbitObject femaleParent, RabbitObject maleParent,int moveNum){
    	
    	this.mom =femaleParent;
    	this.dad =maleParent; 
    	this.turnsmoved =moveNum;
    	this.gen =Math.max(femaleParent.gen,maleParent.gen) +1;
    	
        //set the features based on parents
    	double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        x =Math.random()*2;
        setFertility(.75);
        if(x>1)
        	setColor(femaleParent.getColor());
        else setColor(maleParent.getColor());
        
        
        //size needs to be based on the genetics 
        

        setSize(1);
        
    }
    //methods
    public int generateDirection(int x, int y,MapTile[][] map){
    	//map only needed for super rabbit
    	if (x==9 && y ==9){ 
    	//go in diretion 2 or 3
    		return (int)(Math.random()*2)+2;
    	}
    	else if(x==0 && y==0) {
    	//go in direction 1 or 0
    		return (int)(Math.random()*2);
    	}
    	else if(x==9&& y==0 ) {
    	//12
    		return (int)(Math.random()*2)+1;
    	}
    	else if(x==0&&y==9) {
    	//03
    		int s=(int)(Math.random()*2);
    		if(s==1)
    			return 3;
    		else 
    			return s;
    	}
    	else if (x==0) {
    		//013
    		int s=(int)(Math.random()*3);
    		if(s==2)
    			return 3;
    		else 
    			return s;
    	}
    	else if(x==9) {
    		//213
    		return ((int)(Math.random()*3))+1;
    	}
    	else if(y==0) {
    		//021
    		return((int)(Math.random()*3));
    	}
    	else if(y==9) {
    		//320
    		int m =((int)(Math.random()*3));
    		if(m==1) {
    			return 3;
    		}else {
    			return m;
    		}
    	}
    	else 
    		return (int)(Math.random()*4);
    		
    }

    public void sim1(Color tile) {
    	this.turnsmoved++;
    	this.age++;
    	this.reactMove(tile);
    	if(tile!=Color.BLUE)
    		this.hydration++;
    	else
    		this.hydration =0;
    	
    	
    }
    
    
    public int getSims() {
    	return turnsmoved;
    }
    
    //Acessors and Mutators
    public double getHealth(){
        return this.health;
    }
    public String getGender(){
        return this.gender;
    }
    public double getColor(){
        return this.color;
    }
    public double getFertility(){
        return this.fertility;
    }
    public double getSpeed(){
        return this.speed;
    }
    public int getAge(){
        return this.age;
    }
    public int getSize(){
        return this.size;
    }
    public void setGender(String newGender){
        this.gender=newGender;
    }
    public void setColor(double newColor){
        this.color=newColor;
    }
    public void setFertility(double newAggresion){
        this.fertility=newAggresion;
    }
    public void setSpeed(double newSpeed){
        this.speed=newSpeed;
    }
    public void setHealth(double subtracted){
    	this.health=this.health-subtracted;
    }
    public void reactMove(Color tile) {
    	if(tile == Color.GREEN) {
    		this.setHealth(0);
    	} 
    	else if(tile ==Color.RED) {
    		this.setHealth(this.health);
    	}
    	else if(tile==Color.YELLOW) {
    		this.setHealth(4);
    	}
    	else if(tile==Color.WHITE) {
    		this.setHealth(16);
    	}
    	else if(tile ==Color.BLUE) {
    		this.setHealth(7);
    	}
    }
    public void setSize(int newSize){
        this.size=newSize;
    }
    public RabbitObject getMom() {
    	return this.mom;
    }
    public RabbitObject getDad() {
    	return this.dad; 
    }
    public boolean isAlive(DynamicPopulationTracker x) {
    	boolean result =true;
    	if(this.health <=0) {
    		//infoSystem.out.println("Died from health");
    		x.health++;
    		result= false;
    	}
    	int randLife = (int)(Math.random()*this.ageDeathRange)+this.youngestDeath;
    	
    	if(this.age >randLife) {
    		//infoSystem.out.println("Died from Age");
    		x.age++;
    		result= false;
    	}
    	if(this.hydration==this.hydrationLimit) {
    		x.hydration++;
    		//infoSystem.out.println("Died from Hydration.");
    		result= false;
    	}
    	return result;
    	
    
    }
}
