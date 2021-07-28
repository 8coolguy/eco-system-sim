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
	   
	   int moveNum =0;
	    
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
	           
	           
	           int numOfRabbits =(int)(Math.random()*15)+10;
	           System.out.println(numOfRabbits);
	           for(int i=0;i<numOfRabbits;i++) {
	        	   RabbitObject parent1 =new RabbitObject();
	        	   int x =(int)(Math.random()*9);	
	        	   int y =(int)(Math.random()*9);
	        	   map[x][y].setWhere(parent1);
	           }

	           
	           
	           
	           
	           
       
	           
	           //draw the rabbit 
	           ImageView[][][] rabbitIcon =new ImageView[ROWS][COLUMNS][4];;

	           
	           placeRabbits(baseLayer,map,rabbitIcon);
	          
	           //initilaize the button
	           
	           Button simulateButton =new Button("Simulate");
	           
	           simulateButton.setLayoutX(X/2); 
	           
	           simulateButton.setLayoutY(Y/20);
	           
	           Button simulate100Button =new Button("Simulate 100 times");
	           
	           simulate100Button.setLayoutX(X/2);
	           
	           simulate100Button.setLayoutY(Y/13);
	           
	           baseLayer.getChildren().addAll(simulate100Button);
	           
	           baseLayer.getChildren().addAll(simulateButton);

	           //stage
	           
	           stage.show();
	           
	           simulateButton.setOnAction(new EventHandler<ActionEvent>(){
	                        public void handle( ActionEvent event){
	                        
	                        	moveNum++;
	                        	Display.movedRabbits(map,rabbitIcon,moveNum);
	                        	stage.show();
	                        	try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        	
	                        }
	                    }
	                    );
	           simulate100Button.setOnAction(new EventHandler<ActionEvent>(){
                   public void handle( ActionEvent event){
                	   for(int i=0;i<100; i++){
                		   moveNum++;
                		   Display.movedRabbits(map,rabbitIcon,moveNum);
                		   stage.show();
                		
                	   }

                	   
                   	
                   }
               }
               );
	        
	        }
	    

	        
	   //methods
	   // this method updates the screen AFTER  the rabbits have been moved 
	   // this function also will update the data and stats that are created
	   public static void movedRabbits(MapTile[][] map, ImageView[][][] rabbitMap,int moveNum){
		   //System.out.println(moveNum);
		   for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	        	   RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               createOffspring(map,rabbitsHere,row,column,moveNum);
	               
	               
	               for(int where =0;where<rabbitsHere.length;where++) {
	            	   RabbitObject movingRabbit =rabbitsHere[where];
	              
	                   //got to make sure the sim number is 1 behind the move num so im not moving something twice 
	                   if(movingRabbit!=null && movingRabbit.getSims()==moveNum-1){
	                	   //simulating each rabbit
	                	   movingRabbit.sim1(map[row][column].getTileFeature());
	                	   if(!movingRabbit.isAlive()) {
	                		   rabbitsHere[where] =null;
	                	   }else {
	                		   MapTile.moveRabbits(map, movingRabbit, row, column, where);
	                		   map[row][column].moved(where);
	                	   } 
	                   }	
	               }
	               
	               
	            }
	        }
		   //when displaying the rabbits i can take their stats
		   int rabbitCount =0;
		   int mvf =0;
	        for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	               RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               for(int where =0;where<rabbitsHere.length;where++){
	                   
	            	   
	            	   RabbitObject movingRabbit =rabbitsHere[where];
	                	   if(movingRabbit!=null){
	                		   rabbitCount++;
	                		   if(movingRabbit.getGender()=="m") {
	                			   mvf+=1;
	                		   }
	                		   
	                		   
	                		   rabbitMap[row][column][where].setVisible(true);
	                	   }
	                	   else if (movingRabbit ==null){
	                		   rabbitMap[row][column][where].setVisible(false);
	                	   }
	                   
	                	   
	                	   
	                	   
	               }
	            }
	        }
	        System.out.println("Rabbit Count: "+ rabbitCount);
	        System.out.println("Male/Female Percentage: "+(double)mvf/(double)rabbitCount+"/"+((double)1-(double)mvf/(double)rabbitCount));
	    }
	   
	   //placing the rabbits at the start of the program
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

	   // drew the map on the screen on start up
	   public void drawMap(Pane layer,MapTile[][] map){
	       Rectangle[][] drawnMap = new Rectangle[ROWS][COLUMNS];
	       for(int row =0;row<map.length;row++){
	    	   System.out.println("");
	           for(int column=0;column<map[row].length;column++){
	               Rectangle tile =new Rectangle((X/ROWS)*row,(Y/COLUMNS)*column,X  /ROWS,Y/COLUMNS);
	               drawnMap[row][column] =tile;
	               tile.setFill(map[row][column].getTileFeature());
	               tile.setStroke(Color.rgb(0,0,0));
	               layer.getChildren().addAll(tile);
	               //System.out.print(row+" "+column);
	               
	                
	           }
	       }

	   }

	   ///create a map from a seed based on my perlin function 
	   public static MapTile[][] instantiateMap(){
	       
		   double seed =Math.random();
	       MapTile[][] map =new MapTile[ROWS][COLUMNS];
	       for(int row =0;row<map.length;row++){
	            for(int column=0;column<map[row].length;column++){
	                map[row][column]= new MapTile(row,column,seed);
	            }
	       }
	       return map;
	   }
	   
	   // need to make sure it doesnt repro with itself and with the opposite gender and the both need to want to reproduce
	   public static void createOffspring(MapTile[][] map,RabbitObject[] rabbits, int row, int column,int moveNum) {
		   //System.out.println(moveNum);
		   for(int i =0; i<rabbits.length;i++) {
			   if(rabbits[i] !=null) {
				   for(int j =0;j<rabbits.length;j++) {
					   if(rabbits[j] !=null && i !=j ) {
						   if((rabbits[j].getGender()!=rabbits[i].getGender()) && Math.random() < rabbits[i].getFertility()*rabbits[j].getFertility()) {
							   RabbitObject child;
							   if(rabbits[i].getGender() == "m") 
							   {

								   child =new RabbitObject(rabbits[j],rabbits[i],moveNum);
									   
							   }
							   else {
								   child =new RabbitObject(rabbits[i],rabbits[j],moveNum);
								   
							   }
							   map[row][column].setWhere(child);
							   System.out.println("New Rabbit Child");
							   return;
							   
						   }
						   else {
							   
						   }
					   }
				   }
			   }
		   }
	   }
	}


