package coolguy.maven;


import java.util.Random;

import javafx.scene.paint.Color;
import coolguy.maven.Perlin;
public class MapTile
{
    Color tileFeature;//colors will be features:blue water,red dead zone, yellow dead grass,green fresh grass,orange dessert, purple cold tundra,
    final int MAX_PER_TILE =4;
    Color[] features ={Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.WHITE};
    
    // rabbits in blue have their health filled up by 10
    //rabbits in red lose all heath and die
    //rabbits in yellow loose 30
    //rabbits in orange lose 50
    //rabbits in purple loose 70
    //rabbits in green loose zero
    RabbitObject[] here =new RabbitObject[MAX_PER_TILE];
    // burner variable
    double seed;
    //seed =.6796; has a intresting seed
    //Seed:0.6121924212818659 cool seed balanced colior
    //.5969944649864282
    
    public MapTile(int row,int column,double seed){
    	this.seed =seed;
    	
    	setTileFeature(features[(int)(((Perlin.perlin((float)(row*seed),(float)(column*seed))+1)/2)*features.length)]);
        //setTileFeature(features[(int)(Math.random()*5+.5)]);//generate random land feature  
        here[0] =null;
        here[1]=null;
        here[2]=null;
        here[3]=null;
    
    
    
    
    
    
    
    }
    
    //mehtods
    public static void moveRabbits(MapTile[][] map,RabbitObject movingRabbit,int currentX, int currentY, int where){
        if(movingRabbit!=null){
            //hello, do nothing
        	boolean moving=true;
           
            	int direction =movingRabbit.generateDirection(currentX,currentY,map);
            	
            	//implement speed later
            	int pace =1;
            	if(direction==0) {//right0
            		map[currentX+pace][currentY].setWhere(movingRabbit);
            		
            		moving =false;
            	}
            	if(direction==2 ) {//left2
            		map[currentX-pace][currentY].setWhere(movingRabbit);
            		
            		moving =false;
            	}  
            	if(direction==1 ) {//down1
            		map[currentX][currentY+pace].setWhere(movingRabbit);
            		
            		moving =false;
            	}		
            	if(direction==3) {//up3
            		map[currentX][currentY-pace].setWhere(movingRabbit);
            		
            		moving =false;
            	}
            	
            map[currentX][currentY].moved(where);
            }
        }
    	
    
    
    //already implemented in method above 
    public void moved(int from){
        this.here[from] =null;
    }
    
    
    
    //accesors and mutators
    public Color getTileFeature(){
        return this.tileFeature;
    }
    public void setTileFeature(Color feature){
       this.tileFeature =feature; 
    }
    public RabbitObject[] showWhere(){
        return this.here;
    }
    public void setWhere(RabbitObject where){
        for(int i=0;i<this.here.length;i++){
            if(this.here[i]==null){
                this.here[i]=where;
                break;
            }
        }
    }
}

