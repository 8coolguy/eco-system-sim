package main;


	/*
	 * 
	 * 
	 * Arnav Choudhury 
	 * 2/7,2/8
	 */
	import javafx.application.Application;// for other stuff
	import javafx.scene.image.*;//for the image
	import javafx.scene.Scene;//for scene
	import javafx.scene.shape.*;//made rectangles
	import javafx.scene.control.Button;//for button
	import javafx.scene.control.Label;//labeled button
	import javafx.scene.layout.Pane;//pane to set stage
	import javafx.stage.Stage;//the stage for everything
	import javafx.scene.paint.Color; //colored the rectanagles
	import java.awt.Dimension;//used for screen size
	import java.awt.Toolkit;//got screen size
	import javafx.event.*;//events

	public class Display extends Application
	{
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set to screen size
	   final int X=screenSize.width; //screensize variables
	   final int Y=screenSize.height;
	   
	    int sizeX =X/10;
	    int sizeY =Y/10;
	   
	   
	   final static int ROWS =10;//rows and columns for the map 
	   final static int COLUMNS =10;
	   
	   
	   public static void main(String[]args){
	        launch(args);//creation of map with for loop and multi dimensional array   
	   }
	       
	    
	    
	    
	   @Override
	       public void start(Stage stage) throws Exception{
	           stage.setTitle("Ecosystem Simulation");
	       
	           Pane baseLayer =new Pane();  //pane for everything
	         
	           //details
	           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set to screen size
	    
	           Scene simulation = new Scene(baseLayer,X,Y);//instantiate scene
	           
	           stage.setScene(simulation);
	           
	           
	           //field and draw
	           
	           MapTile[][] map =Display.instantiateMap();
	           
	           drawMap(baseLayer,map);
	           //instantiate rabbtis on array
	           
	           
	           
	           RabbitObject test =new RabbitObject();
	           RabbitObject test2 =new RabbitObject();
	           RabbitObject test3 =new RabbitObject();
	           
	           
	           map[4][7].setWhere(test);
	           map[4][7].setWhere(test2); 
	           map[4][7].setWhere(test3);
	           
	           
	           
	           
	           
	           //draw the rabbit 
	           ImageView[][][] rabbitIcon =new ImageView[ROWS][COLUMNS][4];;

	           
	           placeRabbits(baseLayer,map,rabbitIcon);
	          // rabbitIcon[0][11].setVisible(true);
	           //initilaize the button
	           
	           Button simulateButton =new Button("Simulate");
	           
	           simulateButton.setLayoutX(X/2);
	           
	           simulateButton.setLayoutY(Y/20);
	           
	           
	           baseLayer.getChildren().addAll(simulateButton);

	           //stage
	           
	           stage.show();
	           
	           simulateButton.setOnAction(new EventHandler<ActionEvent>(){
	                        public void handle( ActionEvent event){
	                            Display.movedRabbits(map,rabbitIcon);
	                             
	                            stage.show();
	                            
	                        }
	                    }
	                    );
	        
	        }
	    
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	   //methods
	   public static void movedRabbits(MapTile[][] map, ImageView[][][] rabbitMap){
	        for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	               RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               for(int where =0;where<rabbitsHere.length;where++){
	                   RabbitObject movingRabbit =rabbitsHere[where];
	                   if(movingRabbit!=null){
	                       MapTile.moveRabbits(map,movingRabbit,row,column,where);
	                       rabbitMap[row][column][where].setVisible(true);
	                    }
	                    else{
	                     rabbitMap[row][column][where].setVisible(false);
	                     
	                    }
	                }
	            }
	        }
	    }
	   public void placeRabbits(Pane baseLayer,MapTile[][] map,ImageView[][][] rabbitIcon){
	       for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	               RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               for(int where =0;where<4;where++){
	                       RabbitObject movingRabbit =rabbitsHere[where];
	                       
	                       rabbitIcon[row][column][where] = new ImageView("rabbitIcon.png");
	                       
	                       //scale of the rabit icon
	                       rabbitIcon[row][column][where].setFitHeight(Y/24);
	                       rabbitIcon[row][column][where].setFitWidth(X/24);
	                       
	                       //position of rabbit icon 
	                       if(where==0){
	                           rabbitIcon[row][column][where].setX(row*(sizeX));
	                           rabbitIcon[row][column][where].setY(column*(sizeY));
	                       }    
	                       else if(where==1){
	                           rabbitIcon[row][column][where].setX(row*(sizeX)+(X/20));
	                           rabbitIcon[row][column][where].setY(column*(sizeY));  
	                        }
	                       else if(where==2){
	                           rabbitIcon[row][column][where].setX(row*(sizeX));
	                           rabbitIcon[row][column][where].setY(column*(sizeY)+(Y/20));  
	                        }
	                       else if(where==3){
	                           rabbitIcon[row][column][where].setX(row*(sizeX)+(X/20));
	                           rabbitIcon[row][column][where].setY(column*(sizeY)+(Y/20)); 
	                        }
	                       if(movingRabbit ==null){
	                           rabbitIcon[row][column][where].setVisible(false);
	                        }
	                        else rabbitIcon[row][column][where].setVisible(true);
	                       baseLayer.getChildren().addAll(rabbitIcon[row][column][where]);

	                       
	                       //add to the scene 
	                       
	                    
	               }
	            } 
	       }
	   }
	   public void drawMap(Pane layer,MapTile[][] map){
	       Rectangle[][] drawnMap = new Rectangle[ROWS][COLUMNS];
	       for(int row =0;row<map.length;row++){
	           for(int column=0;column<map[row].length;column++){
	               Rectangle tile =new Rectangle((X/ROWS)*row,(Y/COLUMNS)*column,X  /ROWS,Y/COLUMNS);
	               drawnMap[row][column] =tile;
	               tile.setFill(map[row][column].getTileFeature());
	               tile.setStroke(Color.rgb(0,0,0));
	               layer.getChildren().addAll(tile);
	           }
	       }

	   }
	   public static MapTile[][] instantiateMap(){
	       //create map
	       MapTile[][] map =new MapTile[ROWS][COLUMNS];
	       for(int row =0;row<map.length;row++){
	            for(int column=0;column<map[row].length;column++){
	                map[row][column]= new MapTile();
	            }
	       }
	       return map;
	   }
	}


