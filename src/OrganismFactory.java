import javax.swing.*;

//PART OF ABSTRACT FACTORY DESIGN PATTERN: An Organism factory that returns the corresponding
//organism based on the organism type
public class OrganismFactory extends AbstractFactory{

    //returns an Ant if given an 'A', or returns a Bug if given a 'B'.
    public Object getOrganism(char organismtype)
    {
        if (organismtype == 'A')
        {
            return new Ant();
        }
        else if(organismtype == 'B')
        {
            return new Bug();
        }
        return null;
    }
}
