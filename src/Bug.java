import java.util.ArrayList;
import java.util.Random;

//Bug Class made by Neo Zhi Ming/Danial
//Move made by Neo Zhi Ming/Danial, Breed made by Raflee/Danial, Eat made by Danial/Neo Zhi Ming, Starve made by Arpita.
public class Bug extends Organism {

    //a MapHandler to checking the state of the map around this organism
    MapHandler mapHandler = new MapHandler();
    //The number of steps that will starve the Bug
    private int starveStep;

    //Constructor that sets the organism type to 'B' for bug. Used for Factory purposes and other checking.
    public Bug()
    {
        setOrganismType('B');
    }

    //Constructor as above, but accepting coordinates
    public Bug(int x, int y)
    {
        super(x, y);
        setOrganismType('B');
    }

    @Override
    //Done by Neo Zhi Ming/Danial
    public void move(ArrayList<Organism> organismMap) {
        //Checks for ants, if no ants, run below, if ant present run eat()
        int direction = 0;
        //Random generator that randomly selects a direction to go
        Random rGen = new Random();

        //Incrementing the steps that will starve the Bug by one
        setStarveStep(getStarveStep()+1);

        //Returns a list of directions that has ants.
        ArrayList<Integer> listEat = mapHandler.whereCanEat( organismMap, getX(), getY() );
        if( listEat.size() == 0 ){ // no Ant in sight.
            direction = rGen.nextInt(4);
        }
        else{ // Ant/Ants found.
            direction = eat(organismMap, listEat );
        }

        //a flag to determine if the organism can move.
        boolean unmovable = false;

        //A switch case of the direction, it could be a number from 0-3, runs regardless of eating or not
        switch(direction){
            case 0: //up
            {
                //Checks if there are objects blocking this direction
                unmovable =  mapHandler.orgDetect( 'B', organismMap, this.getX()-1, this.getY() ) ;
                //Checks if it is out of bounds going up
                if ( this.getX()-1 < 0 )
                {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go up by one row.
                if(!unmovable)
                {
                    this.setX(this.getX()-1);
                }
                break;
            }
            case 1: //right
            {
                //Same as above
                unmovable = mapHandler.orgDetect('B', organismMap, this.getX(), this.getY()+1 ) ;
                //Checks if it is out of bounds going right
                if( this.getY()+1 > 19 )
                {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go right by one column
                if(!unmovable)
                {
                    this.setY(this.getY()+1);
                }
                break;
            }
            case 2: //down
            {
                //Same as above
                unmovable = mapHandler.orgDetect('B', organismMap, this.getX()+1, this.getY());
                //Checks if it is out of bounds going down
                if(this.getX()+1 > 19)
                {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go down by one row
                if(!unmovable)
                {
                    this.setX(this.getX()+1);
                }
                break;
            }
            case 3: //left
            {
                //Same as above
                unmovable = mapHandler.orgDetect('B', organismMap, this.getX(), this.getY()-1 );
                //Checks if it is out of bounds by going left
                if(this.getY()-1 < 0)
                {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go left by one column
                if(!unmovable)
                {
                    this.setY(this.getY()-1);
                }
                break;
            }
        }
        //Increments the steps already taken for breeding to happen
        setBreedSteps(getBreedSteps()+1);
        //Initiate breeding function
        breed( organismMap );
        //Initiate starving function
        starve();
    }

    @Override
    //Done by Danial/Raflee
    public void breed( ArrayList<Organism> organismMap ){
        //Only breed if it has moved eight steps
        if( getBreedSteps()==8 ){
            //Checks whether it can breed, and which direction it can breed. Then, set the imminent breeding spot for this Bug. Note that the bug's spawn will consume the ant that it is spawn on.
            setBreedingSpot( mapHandler.whereCanBreed( this, organismMap) );
            //If the ant can breed
            if(getBreedingSpot().size() != 0)
            {
                //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates a factory producer to create an AbstractFactory that will hold an Organism Factory
                FactoryProducer factoryProd = new FactoryProducer();
                AbstractFactory factory = factoryProd.getFactory('O');
                //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates an ant with the factory
                Organism spawn = (Organism)factory.getOrganism('B');
                //Set the coordinates of the new ant as the imminent breeding coordinates, first index of the ArrayList is the X coordinate, the second, the Y coordinates.
                spawn.setX(getBreedingSpot().get(0));
                spawn.setY(getBreedingSpot().get(1));
                //Adds the new Bug to the Organism ArrayList
                organismMap.add(spawn);
                //If the bug was bred on top of an ant, mark it for death.
                for(Organism org: organismMap)
                {
                    if(getBreedingSpot().get(0) == org.getX() && getBreedingSpot().get(1) == org.getY() && org.getOrganismType() == 'A')
                    {
                        org.die();
                    }
                }
                //resets the Breeding Spot to empty.
                afterBreed();
            }
            //Resets the number of steps needed to breed
            setBreedSteps(0);
        }
    }

    //Returns the number of steps that will starve the bug
    public int getStarveStep()
    {
        return starveStep;
    }
    //Sets the number of steps that will starve the bug
    public void setStarveStep(int steps)
    {
        starveStep = steps;
    }

    //Done by Danial
    public int eat(ArrayList<Organism> organismMap, ArrayList<Integer> listEat ){
        //Random generator that decides which ant to eat among the directions.
        Random rGen = new Random();
        //get a random generator that gives a number from 0 to the arraylist.size()-1 (bounds = arraylist.size())
        //If there are 3 ants surrounding the bug, there will be three directions and therefore 0-3 will be generated.
        //Randomly chooses a number/direction with the random generator and assign it to finalDirection
        int finalDirection = listEat.get(rGen.nextInt(listEat.size()));

        //After eating, the bug will no longer starve, so reset the steps that will starve the bug.
        setStarveStep(0);

        //implement the consuming for each direction.
        //It goes in a clockwise directions, N, E, S, W in that order. The change in X will propagate forward from -1 to 0 and the change in Y will propagate backwards from 0 to -1
        int[] direct = {-1,0,1,0};
        for(Organism org: organismMap)
        {
            //The ant in the given direction will be marked for death
            if(org.getX() == getX()+direct[finalDirection] && org.getY() == getY()+direct[3-finalDirection])
            {
                org.die();
            }
        }
        //return the direction so that the Bug will move forward to take the ant's place.
        return finalDirection;
    }

    //Made by Arpita
    public void starve(){
        //If the Bug has moved 3 steps without eating, it will be marked for death
        if( getStarveStep() == 3 ){
            this.die();
        }
    }
}
