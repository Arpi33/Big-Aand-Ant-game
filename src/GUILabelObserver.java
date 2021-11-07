import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;

//PART OF OBSERVER DESIGN PATTERN: A Label observer that updates the labels in the grid map whenever the organism
//arraylist changes
public class GUILabelObserver extends Observer {

    //attaches the observer to a GUI upon instantiation
    public GUILabelObserver(GUI program)
    {
        this.program = program;
        program.attach(this);
    }

    @Override
    //Whenever the organism arraylist changes, the observer will update the labels accordingly
    public void update()
    {
        //setting the grid map labels all to null
        for(int i = 0; i<20; i++)
        {
            for(int j = 0; j<20; j++)
            {
                program.getMapLabels()[i][j].setIcon(null);
            }
        }
        //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates a factory producer to create an AbstractFactory that will hold an Image Factory
        FactoryProducer factoryProd = new FactoryProducer();
        AbstractFactory imageFactory = factoryProd.getFactory('I');
        //PART OF ABSTRACT FACTORY DESIGN PATTERN: Cycling through the organism ArrayList and
        //updates each of the Labels with a corresponding ImageIcon based on the type of organism
        for(Organism org: program.getOrganismMap())
        {
            program.getMapLabels()[org.getX()][org.getY()].setIcon((ImageIcon)imageFactory.getOrganism(org.getOrganismType()));
        }
    }
}
