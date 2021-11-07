import java.util.ArrayList;
import java.util.Random;

//To check the state of the map relative to the coordinates of an object passed
public class MapHandler {

    //Detects if there is a blockage in a specified direction
    public boolean orgDetect( char orgtype, ArrayList<Organism> organismMap, int x, int y ){
        //false if there is nothing blocking, true if something is blocking
        boolean inM = false;
        //iterate through each organism
        for (Organism organism : organismMap) {
            //if there is an organism in the specified direction
            if (organism.getX() == x && organism.getY() == y) {
                //if the organism that the direction is coming from is a bug
                if(orgtype == 'B')
                {
                    //if the organism blocking the way is a bug
                    if(organism.getOrganismType()== 'B')
                    {
                        //then the path is blocked
                        inM = true;
                    }
                }
                //if the organism is an ant, then any blockage restricts movement
                else
                {
                    //the path is blocked
                    inM = true;
                }
                break;
            }
        }
        //returns the state of the path in a specified direction
        return inM;
    }

    //a function that returns an ArrayList of numbers depicting directions that has ants,
    //which will force the Bug to go that direction
    public ArrayList<Integer> whereCanEat( ArrayList<Organism> organismMap, int x, int y ){
        ArrayList<Integer> al_eat = new ArrayList<>();
        int[] direct = {-1,0,1,0};

        //check if adjacent spaces has ant
        for( int i = 0; i<4; i++){
            for (Organism organism : organismMap) {
                if ((organism.getX() == x+direct[i] && organism.getY() == y+direct[3-i]) ) {
                    //put all the directions that has an ant into an ArrayList<Integer>
                    if( organism.getOrganismType()=='A' ){
                        //if another bug hasn't already eaten it
                        if(organism.getLife() == true) {
                            al_eat.add(i);
                        }
                    }
                }
            }
        }

        //returns the ArrayList
        return al_eat;
    }

    //a function that returns a ArrayList of coordinates that depict the imminent breeding spot of an organism.
    public ArrayList<Integer> whereCanBreed( Organism org, ArrayList<Organism> organismMap){
        ArrayList<Integer> al_breedSpot = new ArrayList<>();
        ArrayList<Integer> al_X = new ArrayList<>();
        ArrayList<Integer> al_Y = new ArrayList<>();
        //flag that checks if the direction is occupied by something that blocks the way
        boolean occupied;

        //It goes in a clockwise directions, N, E, S, W in that order. The change in X will propagate forward from -1 to 0 and the change in Y will propagate backwards from 0 to -1
        int[] direct = {-1,0,1,0};
        //Random generator that randomly chooses a spot from a set of potential breeding locations
        Random R = new Random();

        //checking for empty spots
        for( int i = 0; i<4; i++){

            //check if adjacent spaces has an organism that blocks the way
             occupied = orgDetect( org.getOrganismType() , organismMap, org.getX()+direct[i], org.getY()+direct[3-i] );

             //boundary check
            if( org.getX()+direct[i] < 0 || org.getX()+direct[i]>19 || org.getY()+direct[3-i] < 0 || org.getY()+direct[3-i]>19 )
            {
                occupied = true;
            }
            //if it doesn't go out of boundary and nothing is blocking the way in this direction
            if(!occupied)
            {
                //add the X and Y coordinates to an arraylist, first index of al_X corresponds to the first index of al_Y, and so on
                al_X.add( org.getX()+direct[i] );
                al_Y.add( org.getY()+direct[3-i] );
            }
        }
        //if there are locations to breed
        if(al_X.size() != 0){
            //randomly chooses a spot based on how many spots there are
            int spot = R.nextInt(al_X.size());
            al_breedSpot.add( al_X.get(spot) );
            al_breedSpot.add( al_Y.get(spot) );
        }

        //returns the ArrayList
        return al_breedSpot;
    }

}
