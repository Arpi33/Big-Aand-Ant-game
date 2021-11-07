import java.util.ArrayList;
import java.util.Random;

//Ant Class done by Neo Zhi Ming/Danial
//Move done by Neo Zhi Ming/Danial, Breed done by Raflee/Danial.
public class Ant extends Organism {

    //a MapHandler to checking the state of the map around this organism
    MapHandler mapHandler = new MapHandler();

    //Constructor that sets the organism type to 'A' for ant. Used for Factory purposes and other checking.
    public Ant()
    {
        setOrganismType('A');
    }
    //Constructor as above, but accepting coordinates.
    public Ant(int x, int y)
    {
        super(x, y);
        setOrganismType('A');
    }

    @Override
    //Done by Neo Zhi Ming/Danial
    public void move(ArrayList<Organism> organismMap) {
        //Random generator that randomly selects a direction to go
        Random rGen = new Random();
        //Random generator generates 0-3, four numbers for four different directions.
        int direction = rGen.nextInt(4);
        //a flag to determine if the organism can move.
        boolean unmovable = false;

        //A switch case of the direction, it could be a number from 0-3
        switch(direction) {
            case 0: //up
            {
                //Checks if there are objects blocking this direction
                unmovable =  mapHandler.orgDetect('A', organismMap, this.getX()-1, this.getY() );
                //Checks if it is out of bounds going up
                if (this.getX() - 1 < 0) {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go up by one row.
                if (!unmovable) {
                    this.setX(this.getX() - 1);
                }
                break;
            }
            case 1: //right
            {
                //Same as above
                unmovable = mapHandler.orgDetect('A', organismMap, this.getX(), this.getY()+1 );
                //Checks if it is out of bounds going right
                if (this.getY() + 1 > 19) {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go right by one column
                if (!unmovable) {
                    this.setY(this.getY() + 1);
                }
                break;
            }
            case 2: //down
            {
                //Same as above
                unmovable = mapHandler.orgDetect('A', organismMap, this.getX()+1, this.getY());
                //Checks if it is out of bounds going down
                if (this.getX() + 1 > 19) {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go down by one row
                if (!unmovable) {
                    this.setX(this.getX() + 1);
                }
                break;
            }
            case 3: //left
            {
                //Same as above
                unmovable = mapHandler.orgDetect('A', organismMap, this.getX(), this.getY()-1 ) ;
                //Checks if it is out of bounds by going left
                if (this.getY() - 1 < 0) {
                    unmovable = true;
                }
                //If it can be moved, then set its coordinates to go left by one column
                if (!unmovable) {
                    this.setY(this.getY() - 1);
                }
                break;
            }
        }
        //Increments the steps already taken for breeding to happen
        setBreedSteps(getBreedSteps()+1);
        //Initiate breeding function
        breed( organismMap );
    }

    @Override
    //Done by Raflee/Danial
    public void breed( ArrayList<Organism> organismMap ) {
        //Only breed if it has moved three steps
        if( getBreedSteps()==3 ){
            //Checks whether it can breed, and which direction it can breed. Then, set the imminent breeding spot for this Ant
            setBreedingSpot( mapHandler.whereCanBreed( this, organismMap) );
            //If the ant can breed,
            if(getBreedingSpot().size() != 0)
            {
                //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates a factory producer to create an AbstractFactory that will hold an Organism Factory
                FactoryProducer factoryProd = new FactoryProducer();
                AbstractFactory factory = factoryProd.getFactory('O');
                //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates an ant with the factory
                Organism spawn = (Organism)factory.getOrganism('A');
                //Set the coordinates of the new ant as the imminent breeding coordinates, first index of the ArrayList is the X coordinate, the second, the Y coordinates.
                spawn.setX(getBreedingSpot().get(0));
                spawn.setY(getBreedingSpot().get(1));
                //Adds the new Ant to the Organism ArrayList
                organismMap.add(spawn);
                //resets the Breeding Spot to empty.
                afterBreed();
            }
            //Resets the number of steps needed to breed
            setBreedSteps(0);
        }
    }

}
