import java.lang.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.ArrayList;
import randomtestingrt.GUI;


public class Main {

    public static boolean[][] artGraph;
    public static boolean[][] rtGraph;

    public static final int GRAPH_SIZE = 100;

    static Scanner input = new Scanner(System.in);

    public static int artHit = 0;
    public static int rtHit = 0;
    

    public static void main(String[] args) {

        double failureRate = 0;
        boolean validInput = false;
        
        GUI gui = new GUI();
        gui.setVisible(true);
        //gui.setResizable(false);

        //Gets user input failure rate
        while(!validInput) {
            System.out.println("Enter failure rate percentage (0-1): ");
            failureRate = input.nextDouble();
            if( 0 < failureRate && failureRate <= 1 )
                validInput = true;
            else
                System.out.println("!!! Failure Rate invalid !!!");
        }
        
        /*while(gui.beginTest() == false){
            failureRate = gui.getFailureRate();
        }
        failureRate = gui.getFailureRate();*/
        

        //sets failure region
        int blockRegion = (int) Math.sqrt((failureRate * 10000));

        //create graph
        artGraph = new boolean [GRAPH_SIZE][GRAPH_SIZE];
        rtGraph = new boolean [GRAPH_SIZE][GRAPH_SIZE];
        

        //initiale coords for failureblock region
        int randomX = RandomInt(100);
        int randomY = RandomInt(100);
        
        gui.createFailureBlock(randomX, randomY, blockRegion);

        //creates the blockRegion
        for(int y=0; y<blockRegion; y++){
            for(int x=0; x<blockRegion; x++) {
                // Assign next x-coord for failure region
                int newX = randomX + x;
                // check if coordinate overflows the graph bounds
                if (randomX + blockRegion > GRAPH_SIZE - 1) {
                    newX = randomX - x;
                } 

                // Assign next y-coord for failure region
                int newY = randomY + y;
                // check if coordinate overflows the graph bounds
                if (randomY + blockRegion > GRAPH_SIZE - 1) {
                    newY = randomY - y;
                }
                artGraph[newX][newY] = true;
                rtGraph[newX][newY] = true;
            }
        }
        
        //testing
        //printArray(artGraph);

        boolean hit = false;
        int index = 1;
        
        ArrayList<Position> artTest = new ArrayList<Position>();

        //initial test case before ART
        System.out.printf("Test case %d: ", index);

        //=-=-=-=WHY IS BLOCKREGION SET AS THE MAX RAND VALUE?? shouldn't it be 99/100 or graphsize?
        int artX = RandomInt(100);
        int artY = RandomInt(100);
        artTest.add(new Position(artX, artY));
        int rtX = artX;//RandomInt(blockRegion);
        int rtY = artY;//RandomInt(blockRegion);

        
        //tests if RT hit
        
        gui.showRTShot(rtX, rtY);
        if(rtGraph[rtX][rtY]) {
            rtHit++; //might only need this for the while of x tests
            hit = true;
            System.out.print("RT – HIT!!!\t");
        }
        else {
            System.out.print("RT – missed;\t");
        }

        ArrayList<Position> artTests = new ArrayList<>(); //List of attempted tests
        //tests if ART hit
        artTests.add(new Position(artX, artY));
        gui.showARTShot(artX, artY);
        if(artGraph[artX][artY]) {
            artHit++; //might only need this for the while of x tests
            hit = true;
            
            System.out.print("ART – HIT!!!\n");
        }
        else {
            System.out.print("ART – missed;\n");
        }
        index++;

        
        //Arrays.fill(myArray, null); 
        
        //Random Plot selection + Art random
        while(!hit) {
            System.out.printf("Test case %d: ", index);
            //Art Fancy bit here   
            Position c1 = new Position(RandomInt(100),RandomInt(100));
            Position c2 = new Position(RandomInt(100),RandomInt(100));
            Position c3 = new Position(RandomInt(100),RandomInt(100));

            Position shortest1 = artTests.get(0);
            for(Position p : artTests){
                if(p.getDistance(c1) <= shortest1.getDistance(c1)){
                    shortest1 = p;
                }
            }
            Position shortest2 = artTests.get(0);
            for(Position p : artTests){
                if(p.getDistance(c2) <= shortest2.getDistance(c2)){
                    shortest2 = p;
                }
            }
            Position shortest3 = artTests.get(0);
            for(Position p : artTests){
                if(p.getDistance(c3) <= shortest3.getDistance(c3)){
                    shortest3 = p;
                }
            }

            //might need to check to make sure this doesn't change when other object is changed
            Position newTestPoint;

            if(shortest1.getDistance(c1) > shortest2.getDistance(c2)){
                if(shortest1.getDistance(c1) > shortest3.getDistance(c3)){
                    newTestPoint = c1;
                }else {
                    newTestPoint = c3;
                }
            } else if(shortest2.getDistance(c2) > shortest3.getDistance(c3)) {
                newTestPoint = c2;
            } else {
                newTestPoint = c3;
            }
            System.out.println(newTestPoint.getX() + " " + newTestPoint.getY());

            artX = newTestPoint.getX();
            artY = newTestPoint.getY();
            gui.showARTShot(artX, artY);
            
            rtX = RandomInt(100);
            rtY = RandomInt(100);
            gui.showRTShot(rtX, rtY);
            
            
            
            if(rtGraph[rtX][rtY]) {
                rtHit++;
                hit = true;
                System.out.print("RT – HIT!!!\t");
            }
            else {
                System.out.print("RT – missed;\t");
            }
            artTests.add(new Position(artX, artY));
            
            if(artGraph[artX][artY]) {
                artHit++;
                hit = true;
                System.out.print("ART – HIT!!!\n");
                //artTest = new ArrayList<Position>();
            }
            else {
                System.out.print("ART – missed;\n");
            }

            index++;

        }





        //extra competitions
        
        RepeatTest(blockRegion);

        input.close();
    }

    static int RandomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    //uncomment later, fix first test run first
    
    static void RepeatTest(int r) {
        int numberOfRepeat = 0;
        System.out.println("How many more competitions do you want to run? ");
        numberOfRepeat = input.nextInt();
        int index = 1;
        int rtWinTally = 0;
        int artWinTally = 0;

        for(int tests=0; tests <= numberOfRepeat; tests++) {

            //initial test case before ART
            System.out.printf("Test case %d: ", index);
            int artX = RandomInt(100);
            int artY = RandomInt(100);
            int rtX = artX;//RandomInt(blockRegion);
            int rtY = artY;//RandomInt(blockRegion);
            boolean hit = false;

        
            //tests if RT hit
        
            //gui.showRTShot(rtX, rtY);
            if(rtGraph[rtX][rtY]) {
                rtHit++; //might only need this for the while of x tests
                hit = true;
                rtWinTally++;
                System.out.print("RT – HIT!!!\t");
            }
            else {
                System.out.print("RT – missed;\t");
            }

            ArrayList<Position> artTests = new ArrayList<>(); //List of attempted tests
            //tests if ART hit
            artTests.add(new Position(artX, artY));
            //gui.showARTShot(artX, artY);
            if(artGraph[artX][artY]) {
                artHit++; //might only need this for the while of x tests
                hit = true;
                artWinTally++;
                System.out.print("ART – HIT!!!\n");
            }
             else {
                 System.out.print("ART – missed;\n");
            }
            index++;

        
            //Arrays.fill(myArray, null); 
        
            //Random Plot selection + Art random
            while(!hit) {
                 System.out.printf("Test case %d: ", index);
                //Art Fancy bit here   
                Position c1 = new Position(RandomInt(100),RandomInt(100));
                Position c2 = new Position(RandomInt(100),RandomInt(100));
                Position c3 = new Position(RandomInt(100),RandomInt(100));

                Position shortest1 = artTests.get(0);
                for(Position p : artTests){
                    if(p.getDistance(c1) <= shortest1.getDistance(c1)){
                        shortest1 = p;
                    }
                }
                Position shortest2 = artTests.get(0);
                for(Position p : artTests){
                    if(p.getDistance(c2) <= shortest2.getDistance(c2)){
                        shortest2 = p;
                    }
                }
                Position shortest3 = artTests.get(0);
                for(Position p : artTests){
                    if(p.getDistance(c3) <= shortest3.getDistance(c3)){
                        shortest3 = p;
                    }
                }

                //might need to check to make sure this doesn't change when other object is changed
                Position newTestPoint;

                if(shortest1.getDistance(c1) > shortest2.getDistance(c2)){
                    if(shortest1.getDistance(c1) > shortest3.getDistance(c3)){
                        newTestPoint = c1;
                    }else {
                        newTestPoint = c3;
                    }
                } else if(shortest2.getDistance(c2) > shortest3.getDistance(c3)) {
                    newTestPoint = c2;
                } else {
                    newTestPoint = c3;
                }
                System.out.println(newTestPoint.getX() + " " + newTestPoint.getY());

                artX = newTestPoint.getX();
                artY = newTestPoint.getY();
                //gui.showARTShot(artX, artY);
            
                rtX = RandomInt(100);
                rtY = RandomInt(100);
                //gui.showRTShot(rtX, rtY);
            
            
            
                if(rtGraph[rtX][rtY]) {
                    rtHit++;
                    hit = true;
                    rtWinTally++;
                    System.out.print("RT – HIT!!!\t");
                }
                else {
                    System.out.print("RT – missed;\t");
                }
                artTests.add(new Position(artX, artY));
            
                if(artGraph[artX][artY]) {
                    artHit++;
                    hit = true;
                    artWinTally++;
                    System.out.print("ART – HIT!!!\n");
                    //artTest = new ArrayList<Position>();
                }
                else {
                    System.out.print("ART – missed;\n");
                }

                index++;

            }


        }

        System.out.println("\n*--------------------------------------*\n");
        System.out.printf("%d competitions have been completed, of which RT wins %d times and ART wins %d times.\n", numberOfRepeat, rtWinTally, artWinTally);
        System.out.println("\n*--------------------------------------*\n");

    }


     
    //TEMPORARY FOR PRINTING
    //prints 2d array with formatting
    static void printArray(boolean[][] inArray){
        int count = 0;
        for (int i = 0; i < inArray.length; ++i) {
            for(int j = 0; j < inArray[i].length; ++j) {
                if (inArray[i][j] == false){
                    System.out.print( "|0");
                }else{
                    System.out.print( "|X");
                    count++;
                }

                //format printing here

            }
            System.out.println("");
        }
        System.out.println("-=-=-=-=-");
        System.out.println(count); //might need longer later
    }
    
    
}

class Position {
    private int x;
    private int y;

    public Position (int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Position ()
    {
        this.x = -1;
        this.y = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDistance(Position p) {
        int x1 = this.x;
        int x2 = p.getX();
        int y1 = this.y;
        int y2 = p.getY();

        return Math.sqrt(
            Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)
        );
    }
}