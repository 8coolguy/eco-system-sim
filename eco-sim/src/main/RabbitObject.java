package main;

public class RabbitObject{
    //instance variables for the object rabbit
    String gender = new String();//m or f
    double color;//0-10:0black:10 white
    double fertility;//0-10
    double speed;//0-10
    double health;
    int age;//0-24  1 per turn
    int maxSize=10;//max each grow to
    int size;//current size per age linear with age
    
    
    //not implemented 
    int hunger;
    
    
    //default contructor for intial rabbit object
    public RabbitObject(){
        double x =Math.random()*2;
        if (x>1)
            setGender("m");
        else setGender("f");
        setColor(Math.random()*10);
        setFertility(0);
        setSpeed(Math.random()*10);
        setHealth(10);
        setAge(1);
        setSize(1);
        
    }
    public RabbitObject(RabbitObject femaleParent, RabbitObject maleParent){
        //set the features based on parents
        setHealth(10);
        setAge(1);
        setSize(1);
    }
    //methods
    public int generateDirection(){
        return (int)(Math.random()*4);
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
