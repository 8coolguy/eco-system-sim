package coolguy.maven;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.paint.Color;

//TODO need to finish the genetic traits part
//TODO need to impement the super rabbit that can make informed descions
public class  RabbitObject{
    //instance variables for the object rabbit
	int hydrationLimit =10;
	int youngestDeath =8;
	int ageDeathRange =15;
	boolean eaten =false;
	final int BASE =10;
    String gender = new String();//m or f
    //0-10 color trait will  change how attarctive it is to hunt
    int health=7;//0-100 starting health goes here
    int deltaHealth=0;
    int age;//0-24  1 per turn\
    //inherited properties form parents 
    double color;//0-1
    double fertility;//0-1
    double size;// will change how  attractive it is to hunt 1,small,2 medium,3 large
    double speed; //1-3 1space 2 space 3 spaces 
    //stats props 
    int turnsmoved;
    public int gen =0;
    //parents
    RabbitObject mom;
    RabbitObject dad; 
    //no hunger implemented  
    int hydration =0;
    //tile effects
    int greenEffect=-1;
    int yellowEffect = 4;
    int whiteEffect = 11;
    int blueEffect =7;
    //default contructor for intial rabbit object
    public RabbitObject(int moveNum){
    	//this.id =setId();
    	
    	this.turnsmoved =moveNum;
        this.mom=null;
        this.dad =null;
        
        double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        
        
        setColor(Math.random());
        setFertility(Math.random());
        setSpeed(Math.random()*3);
        setSize(Math.random()*3);

        
        this.health=((int)(this.health*this.size))+BASE;
        //infoSystem.out.println("Total Health: "+ this.health);
        
    }
    //constructor for child rabbit
	public RabbitObject(RabbitObject femaleParent, RabbitObject maleParent,int moveNum){
    	
    	this.mom =femaleParent;
    	this.dad =maleParent; 
    	this.turnsmoved =moveNum;
    	this.gen =Math.max(femaleParent.gen,maleParent.gen) +1;
    	double randomMod =(Math.random()/2)-.25;
        //set the features based on parents
    	double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        if(femaleParent.mom !=null && femaleParent.dad !=null && maleParent.mom !=null && maleParent.dad !=null) {
        	setSpeed(((maleParent.getSpeed()+femaleParent.getSpeed()+maleParent.mom.getSpeed()+maleParent.dad.getSpeed()+femaleParent.dad.getSpeed()+femaleParent.mom.getSpeed())/6.0));
        	setSize(((maleParent.getSize()+femaleParent.getSize()+maleParent.mom.getSize()+maleParent.dad.getSize()+femaleParent.dad.getSize()+femaleParent.mom.getSize())/6.0));
        	setColor((maleParent.getColor()+femaleParent.getColor()+maleParent.mom.getColor()+maleParent.dad.getColor()+femaleParent.dad.getColor()+femaleParent.mom.getColor())/6.0);
        	setFertility((maleParent.getFertility()+femaleParent.getFertility()+maleParent.mom.getFertility()+maleParent.dad.getFertility()+femaleParent.dad.getFertility()+femaleParent.mom.getFertility())/6.0);
        }else {
        	setSpeed(((maleParent.getSpeed()+femaleParent.getSpeed())/2.0));
        	setSize(((maleParent.getSize()+femaleParent.getSize())/2.0));
        	setFertility((maleParent.getFertility()+femaleParent.getFertility())/2.0);
        	setColor((maleParent.getColor()+femaleParent.getColor())/2.0);
        }
        
        setSpeed(this.speed*randomMod +this.speed);
        if(this.speed >6.0) {
        	this.speed =6.0;
        }else if(this.speed <1.0){
        	this.speed=1.0;
        }
    	setSize(this.size*randomMod+this.size);
    	if(this.size >5.0) {
    		this.size=5.0;
    	}
    	setFertility(this.fertility*randomMod+this.fertility);
    	if(this.fertility>1.0) {
    		this.fertility=1.0;
    	}else if(this.fertility<0) {
    		this.fertility=0.0;
    	}
    	
    	setColor(this.color*randomMod+this.color);
        if(this.color>1) {
        	this.color =1.0;
        }else if(this.color<0) {
        	this.color=0;
        }
        
        this.health=((int)(this.health*this.size))+BASE;
        //infoSystem.out.println("Total Health: "+ this.health);
        //size needs to be based on the genetics 
        
    }
    //methods
    public int generateDirection(int x, int y,MapTile[][] map,RabbitLearner ml){
    	
    	
    	
    	
    	int n =map.length-(int)(this.speed);
    	int m =(int)(this.speed)-1;
    	int pace=(int)(this.speed);
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
    	else {

    		return (int)(Math.random()*4);
    	}
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
    public int getHealth(){
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
    public double getSize(){
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
    public void setHealth(int subtracted){
    	this.health=this.health-subtracted;
    }
    public void reactMove(Color tile) {
    	if(tile == Color.GREEN) {
    		this.setHealth(greenEffect);
    	} 
    	else if(tile ==Color.RED) {
    		this.setHealth(this.health);
    		this.deltaHealth =this.health;
    	}
    	else if(tile==Color.YELLOW) {
    		this.setHealth(yellowEffect);
    		this.deltaHealth =yellowEffect;
    	}
    	else if(tile==Color.WHITE) {
    		this.setHealth(whiteEffect);
    		this.deltaHealth=this.whiteEffect;
    	}
    	else if(tile ==Color.BLUE) {
    		this.setHealth(blueEffect);
    		this.deltaHealth = this.blueEffect;
    	}
    }
    public void setSize(double newSize){
        this.size=newSize;
    }
    public RabbitObject getMom() {
    	return this.mom;
    }
    public RabbitObject getDad() {
    	return this.dad; 
    }
    public boolean isAlive(DynamicPopulationTracker x,boolean update) {
    	boolean result =true;
    	if(this.health <=0) {
    		//infoSystem.out.println("Died from health");
    		if (update)
    			x.health++;
    		result= false;
    	}
    	int randLife = (int)(Math.random()*this.ageDeathRange)+this.youngestDeath;
    	
    	if(this.age >randLife) {
    		//infoSystem.out.println("Died from Age");
    		if (update)
    			x.age++;
    		result= false;
    	}
    	if(this.hydration==this.hydrationLimit) {
    		if(update)
    			x.hydration++;
    		
    		//infoSystem.out.println("Died from Hydration.");
    		result= false;
    	}
    	if(eaten) {
    		if(update)
    			x.huntedNum++;
    		//infoSystem.out.println("Died from fox");
    		result=false;
    	}
    	return result;
    	
    
    }
    public void isEaten() {
    	this.eaten =true;
    }
}
