package main;

public class RabbitObject{
    //instance variables for the object rabbit
    String gender = new String();//m or f
    double color;//0-10:0black:10 white
    double fertility;//0-10
    double speed;//0-10
    double health;//0-10
    int age;//0-24  1 per turn
    int maxSize=10;//max each grow to
    int size;//current size per age linear with age
    int turnsmoved;
    
    //not implemented 
    int hunger;
    
    
    //default contructor for intial rabbit object
    public RabbitObject(){
        double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        setColor((int)Math.random()*10);
        setFertility(0);
        setSpeed((int)Math.random()*10);
        setHealth(10);
        setAge(1);
        setSize(1);
        turnsmoved =0;
        
    }
    public RabbitObject(RabbitObject femaleParent, RabbitObject maleParent){
        //set the features based on parents
        setHealth(10);
        setAge(1);
        setSize(1);
        turnsmoved =0;
    }
    //methods
    public int generateDirection(int x, int y){
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
    public void sim1() {
    	this.turnsmoved++;
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
    public void setHealth(double newHealth){
        this.health =newHealth;
    }
    public void setAge(int newAge){
        this.age=newAge;
    }
    public void setSize(int newSize){
        this.size=newSize;
    }
}
