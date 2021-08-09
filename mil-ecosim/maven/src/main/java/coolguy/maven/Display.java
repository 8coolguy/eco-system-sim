package coolguy.maven;


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
import java.io.File;

import org.jfree.ui.RefineryUtilities;

	import javafx.event.*;//events
	
	
	public class Display extends Application
	{
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set to screen size
	   final int X=screenSize.width; //screensize variables
	   final int Y=screenSize.height;
	   final static int ROWS =20;//rows and columns for the map 
	   final static int COLUMNS =20;
	   int sizeX =X/ROWS;
	   int sizeY =Y/COLUMNS;
	   
	   int moveNum =0;
	   static Image rabbitPic =new Image("rabbitIcon.png");
	   static Image foxPic =new Image("foxIcon.jpg");
	  
	   
	   static DynamicPopulationTracker popTracker;
	   static DynamicStatsTracker statTracker;
	   public static void main(String[]args){
		   
	       launch(args);//creation of map with for loop and multi dimensional array   
	   }
	       
	    
	    
	    //TODO need to make the tile size adjustable 
	   @Override
	       public void start(final Stage stage) throws Exception{
	           
		   		popTracker =new DynamicPopulationTracker("Population Tracker");
		   		popTracker.pack();
		   		popTracker.setVisible(true);
		   		statTracker=new DynamicStatsTracker("Traits Tracker");
		   		statTracker.pack();
		   		statTracker.setVisible(true);
		   		
		   		RefineryUtilities.centerFrameOnScreen(popTracker);
		   		
		   		stage.setTitle("Ecosystem Simulation");
		   		
		   		final RabbitLearner ml =new RabbitLearner(new File("output.json"));
	       
	           Pane baseLayer =new Pane();  //pane for everything
	         
	           //details
	           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set to screen size
	    
	           Scene simulation = new Scene(baseLayer,X,Y);//instantiate scene
	           
	           stage.setScene(simulation);
	           
	           
	           //field and draw
	           
	           final MapTile[][] map =Display.instantiateMap();
	           
	           drawMap(baseLayer,map);
	           //instantiate rabbtis and foxes on array
	           
	           //remember thisRabbitObject x = new SuperRabbitObject(moveNum);
	           int numOfRabbits =(int)(Math.random()*15)+ROWS*COLUMNS;
	           int numOfFoxes =(int)(Math.random()*10)+(ROWS*COLUMNS)/5;
	           System.out.println("Starting Rabbits: "+numOfRabbits);
	           System.out.println("Starting Foxes: "+numOfFoxes);
	           //numOfFoxes =0;
	           //numOfRabbits =0;
	           for(int i=0;i<numOfFoxes;i++) {
	        	   FoxObject fox =new FoxObject(moveNum);
	        	   int x =(int)(Math.random()*ROWS);	
	        	   int y =(int)(Math.random()*COLUMNS);
	        	   map[x][y].setWhere(fox);
	           }
	           //chang this line to init super rabbits or regular rabbits
	           for(int i=0;i<numOfRabbits;i++) {
	        	   SuperRabbitObject parent1 =new SuperRabbitObject(moveNum);//change for smart rabbits
	        	   int x =(int)(Math.random()*ROWS);	
	        	   int y =(int)(Math.random()*COLUMNS);
	        	   map[x][y].setWhere(parent1);
	        	   if(parent1 instanceof SuperRabbitObject) {
 	        		   ((SuperRabbitObject) parent1).oldState =((SuperRabbitObject) parent1).getState(x, y, map);
 	        	   }
	           }

	           
	           
	           
	           
	           
       
	           
	           //draw the rabbit 
	           final ImageView[][][] rabbitIcon =new ImageView[ROWS][COLUMNS][4];;

	           
	           placeRabbits(baseLayer,map,rabbitIcon);
	          
	           //initilaize the button
	           final Button restart =new Button("Restart Sim");
	           
	           restart.setLayoutX(X/2 + X/10); 
	           
	           restart.setLayoutY(Y/20);
	           
	           final Button simulateButton =new Button("Simulate");
	           
	           simulateButton.setLayoutX(X/2); 
	           
	           simulateButton.setLayoutY(Y/20);
	           
	           final Button simulate100Button =new Button("Simulate 100 times");
	           
	           simulate100Button.setLayoutX(X/2 -X/10);
	           
	           simulate100Button.setLayoutY(Y/20);
	           
	           final Button endless =new Button("Endless");
	           
	           endless.setLayoutX(X/2 +2*X /10);
	           
	           endless.setLayoutY(Y/20);
	           
	           final Button quit =new Button("Quit");
	           
	           quit.setLayoutX(X/2 - 2*X /10);
	           
	           quit.setLayoutY(Y/20);
	        		   
	           baseLayer.getChildren().addAll(restart);
	           
	           baseLayer.getChildren().addAll(quit);
	           
	           baseLayer.getChildren().addAll(simulate100Button);
	           
	           baseLayer.getChildren().addAll(simulateButton);

	           baseLayer.getChildren().addAll(endless);
	           //stage
	           
	           stage.show();
	           
	           
	           simulateButton.setOnAction(new EventHandler<ActionEvent>(){
	                        public void handle( ActionEvent event){
	                        
	                        	moveNum++;
	                        	Display.movedRabbits(map,rabbitIcon,moveNum,ml);
	                        	stage.show();
	                        	try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
	                        	
	                        }
	                    }
	                    );
	           simulate100Button.setOnAction(new EventHandler<ActionEvent>(){
                   public void handle( ActionEvent event){
                	   for(int i=0;i<100; i++){
                		   moveNum++;
                		   Display.movedRabbits(map,rabbitIcon,moveNum,ml);
                		   stage.show();
                		
                	   }

                	   
                   	
                   }
               }
               );
	           endless.setOnAction(new EventHandler<ActionEvent>(){
	        	   public void handle(ActionEvent event) {
	        		   while (true) {
	        			   moveNum++;
                		   Display.movedRabbits(map,rabbitIcon,moveNum,ml);
                		   stage.show();
                		   
                		
	        		   }
	        	   }
	           });
	           restart.setOnAction(new EventHandler<ActionEvent>(){
	        	   public void handle(ActionEvent event) {
	        		   //restarts and put more rabbits on board
	        		   int numOfFoxes =(int)(Math.random()*5)+(ROWS*COLUMNS)/5;
	        		   int numOfRabbits =(int)(Math.random()*15)+ROWS*COLUMNS;
	    	           System.out.println("Restarted Rabbits: "+numOfRabbits);
	    	           System.out.println("Restarted Foxes: "+numOfFoxes);
	    	           for(int i=0;i<numOfFoxes;i++) {
	    	        	   FoxObject fox =new FoxObject(moveNum);
	    	        	   int x =(int)(Math.random()*ROWS-1);	
	    	        	   int y =(int)(Math.random()*COLUMNS-1);
	    	        	   map[x][y].setWhere(fox);
	    	           }
	     	           for(int i=0;i<numOfRabbits;i++) {
	     	        	   SuperRabbitObject parent1 =new SuperRabbitObject(moveNum);
	     	        	   int x =(int)(Math.random()*ROWS);	
	     	        	   int y =(int)(Math.random()*COLUMNS-1);
	     	        	   map[x][y].setWhere(parent1);
	     	        	   if(parent1 instanceof SuperRabbitObject) {
	     	        		   ((SuperRabbitObject) parent1).oldState =((SuperRabbitObject) parent1).getState(x, y, map);
	     	        	   }
	     	           }
	     	          

	        		   
	        	   }
	           });
	           quit.setOnAction(new EventHandler<ActionEvent>(){
	        	   public void handle(ActionEvent event) {
	        		   ml.quit();
	        		   stage.close();
	        		   System.exit(0);
	        		   
	        	   }
	           });
	        
	        }
	    

	        
	   //methods
	   // this method updates the screen AFTER  the rabbits have been moved 
	   // this function also will update the data and stats that are created
	   public static void movedRabbits(MapTile[][] map, ImageView[][][] rabbitMap,int moveNum,RabbitLearner ml){
		   
		   for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	        	   RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               createOffspring(map,rabbitsHere,row,column,moveNum);
	               
	               
	               for(int where =0;where<rabbitsHere.length;where++) {
	            	   RabbitObject movingRabbit =rabbitsHere[where];
	            	 
	                   //got to make sure the sim number is 1 behind the move num so im not moving something twice 
	                   if(movingRabbit!=null && movingRabbit.getSims()==moveNum-1){
	                	   if(movingRabbit instanceof FoxObject) {
	                		   FoxObject temp =(FoxObject)movingRabbit;	
	                		   temp.eat(map[row][column]);
	                		   movingRabbit =temp;
	                	   }
	                	   if(movingRabbit.eaten)
	                		   System.out.println(movingRabbit.eaten);
	                	   //simulating each rabbit
	                	   movingRabbit.sim1(map[row][column].getTileFeature());
	                	   if(movingRabbit instanceof SuperRabbitObject) {
	                		   SuperRabbitObject temp =(SuperRabbitObject)movingRabbit;	
	                		   ml.updateStrategy(temp.oldState, temp.getState(row, column, map),temp.lastAction, temp,popTracker);
	                		   movingRabbit =temp;
	                	   }
	                	   if(!movingRabbit.isAlive(popTracker,true)) {
	                		   rabbitsHere[where] =null;
	                	   }else {
	                		   MapTile.moveRabbits(map, movingRabbit, row, column, where,ml);
	                		   map[row][column].moved(where);
	                	   } 
	                   }	
	               }
	               
	               
	            }
	        }
		   //when displaying the rabbits i can take their stats
		   int rabbitCount =0;
		   int cumGen=0;
		   int mvf=0;
		   int foxCount=0;
		   double fertCum =0;
		   double colorCum=0;
		   double speedCum =0;
		   double sizeCum =0;
		   int totalAge =0;
		   double foxColor=0;
	        for(int row =0;row<map.length;row++){//traversing through rows
	           for(int column=0;column<map[row].length;column++){//traversing through cloumns
	               RabbitObject[] rabbitsHere=map[row][column].showWhere();
	               for(int where =0;where<rabbitsHere.length;where++){
	                   
	            	   
	            	   RabbitObject movingRabbit =rabbitsHere[where];
	                	   if(movingRabbit!=null ){
	                		   
	                		   
	                		   if(movingRabbit instanceof FoxObject) {
	                			   rabbitMap[row][column][where].setImage(foxPic);
	                			   foxCount++;
	                			   foxColor+=movingRabbit.getColor();
	                		   }else {
	                			   if(movingRabbit.getGender().equals("m"))
	                				   mvf++;
	                			   rabbitMap[row][column][where].setImage(rabbitPic);
	                			   rabbitCount++;
		                		   cumGen+=movingRabbit.gen;
		                		   fertCum+=movingRabbit.getFertility();
		                		   colorCum+=movingRabbit.getColor();
		                		   speedCum+=movingRabbit.getSpeed();
		                		   sizeCum+=movingRabbit.getSize();
		                		   totalAge+=movingRabbit.getAge();
	                		   }
		                		   
		                	   rabbitMap[row][column][where].setVisible(true);
	                		   
	                	   }
	                	   else if (movingRabbit ==null){
	                		   rabbitMap[row][column][where].setVisible(false);
	                	   }
	                   
	                	   
	                	   
	                	   
	               }
	            }
	        }
	        popTracker.updatePop(moveNum, rabbitCount,foxCount);
	        statTracker.updateStats(moveNum, fertCum/(double)rabbitCount, colorCum/(double)rabbitCount, (double)mvf/(double)rabbitCount, (double)totalAge/(double)rabbitCount, sizeCum/(double)rabbitCount,speedCum/(double)rabbitCount);
	        System.out.println("-----------------------------"+moveNum+"------------------------------");
	        //infoSystem.out.println("Rabbit Count: "+ rabbitCount);
	        //infoSystem.out.println("Fox Count: "+ foxCount);
	        //infoSystem.out.println("Male vs Females: "+(double)mvf/(double)rabbitCount);
	        System.out.println("Average generation: "+(double)cumGen/(double)rabbitCount);
	        //info System.out.println("Average Fertility: "+fertCum/(double)rabbitCount);
	        //infoSystem.out.println("Average Color: "+colorCum/(double)rabbitCount);
	        //infoSystem.out.println("Average Fox Color: "+foxColor/(double)foxCount);
	        //infoSystem.out.println("Average Size: "+sizeCum/(double)rabbitCount);
	        //infoSystem.out.println("Average Speed: "+speedCum/(double)rabbitCount);
	        System.out.println("Average Age: "+(double)totalAge/(double)rabbitCount);
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
	                       rabbitIcon[row][column][where].setFitHeight(Y/(COLUMNS*2));
	                       rabbitIcon[row][column][where].setFitWidth(X/(ROWS*2));
	                       
	                       //position of rabbit icon 
	                       if(where==0){
	                           rabbitIcon[row][column][where].setX(row*(sizeX));
	                           rabbitIcon[row][column][where].setY(column*(sizeY));
	                       }    
	                       else if(where==1){
	                           rabbitIcon[row][column][where].setX(row*(sizeX)+(X/(ROWS*2)));
	                           rabbitIcon[row][column][where].setY(column*(sizeY));  
	                        }
	                       else if(where==2){
	                           rabbitIcon[row][column][where].setX(row*(sizeX));
	                           rabbitIcon[row][column][where].setY(column*(sizeY)+(Y/(COLUMNS*2)));  
	                        }
	                       else if(where==3){
	                           rabbitIcon[row][column][where].setX(row*(sizeX)+(X/(ROWS*2)));
	                           rabbitIcon[row][column][where].setY(column*(sizeY)+(Y/(COLUMNS*2))); 
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
	   //seed info 
	   //math.random gives ok maps
	   public static MapTile[][] instantiateMap(){
	       
		   double seed =Math.random();
		   System.out.println("Seed:"+seed);
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
						   if(!(rabbits[j].getGender().equals(rabbits[i].getGender())) && Math.random() < rabbits[i].getFertility()*rabbits[j].getFertility()) {
							   SuperRabbitObject child;
							   FoxObject foxChild;
							   if(!(rabbits[i] instanceof FoxObject) && !(rabbits[j] instanceof FoxObject) ) {
								   if(rabbits[i].getGender() == "m") 
								   {
	
									   child =new SuperRabbitObject((SuperRabbitObject)rabbits[j],(SuperRabbitObject)rabbits[i],moveNum);
										   
								   }
								   else {
									   child =new SuperRabbitObject((SuperRabbitObject)rabbits[i],(SuperRabbitObject)rabbits[j],moveNum);
									   
								   }
								   map[row][column].setWhere(child);
								   System.out.println("New Rabbit Child");
								   return;
							   }else if(rabbits[i] instanceof FoxObject && rabbits[j] instanceof FoxObject) {
								   FoxObject p1=(FoxObject)rabbits[i];
								   FoxObject p2 =(FoxObject)rabbits[j];
								   if(rabbits[i].getGender() == "m") 
								   {
	
									   foxChild =new FoxObject(p2,p1,moveNum);
										   
								   }
								   else {
									   foxChild =new FoxObject(p1,p2,moveNum);
									   
								   }
								   map[row][column].setWhere(foxChild);
								   
							   }
							   
						   }
						   else {
							   
						   }
					   }
				   }
			   }
		   }
	   }
	}


