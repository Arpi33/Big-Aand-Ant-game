import javax.swing.*;
import java.awt.*;


//PART OF ABSTRACT FACTORY DESIGN PATTERN: An ImageIcon factory that returns the corresponding
//image based on the organism type
public class ImageFactory extends AbstractFactory{

    //returns an Ant image if given an 'A', or returns a Bug image if given a 'B'.
    public Object getOrganism(char imagetype)
    {
        if (imagetype == 'A')
        {
            return new ImageIcon(getClass().getResource("Ant.png"));
        }
        else if(imagetype == 'B')
        {
            return new ImageIcon(getClass().getResource("Bug.png"));
        }
        return null;
    }
}
