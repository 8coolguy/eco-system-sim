package main;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
public class MapTile
{
    Color tileFeature;//colors will be features:blue water,red dead zone, yellow dead grass,green fresh grass,orange dessert, purple cold tundra,
    final int MAX_PER_TILE =4;
    Color[] features ={Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN,Color.ORANGE,Color.PURPLE};
    
    RabbitObject[] here =new RabbitObject[MAX_PER_TILE];
    // burner variable
    int x;
    
    
    public MapTile(){
        setTileFeature(features[(int)(Math.random()*5+.5)]);//generate random land feature  
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
            while(moving) {
            	int direction =movingRabbit.generateDirection();
            	//implement speed later
            	int pace =1;
            	if(direction ==0 ||direction==2 && currentX+pace<10 && currentX-pace>-1){//move up or down
            		if(direction==0) {
            			map[currentX+pace][currentY].setWhere(movingRabbit);
            			break;
            		}
            		if(direction==2) {
            			map[currentX-pace][currentY].setWhere(movingRabbit);
            			break;
            			}
            		}  
            	else{//move right or left 
            		if(direction==1 && currentY+pace<10 && currentY-pace>-1) {
            			map[currentX][currentY+pace].setWhere(movingRabbit);
            			break;
            		}
            			
            		if(direction==3&& currentY+pace<10 && currentY-pace>-1) {
            			map[currentX][currentY-pace].setWhere(movingRabbit);
            			break;
            		}
            	}	
            map[currentX][currentY].moved(where);
            }
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
                i=5;
            }
        }
    }
}

