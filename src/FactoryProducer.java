//Made by Neo Zhi Ming, all methods made by Neo Zhi Ming
//PART OF ABSTRACT FACTORY DESIGN PATTERN: A Factory Producer that instantiates the necessary Factories
//depending on the need.
public class FactoryProducer {

    //Either returns an Organism Factory if given an 'O', or an ImageFactory if given an 'I'
    public AbstractFactory getFactory(char imgOrOrg)
    {
        if(imgOrOrg == 'O')
        {
            return new OrganismFactory();
        }
        else if(imgOrOrg == 'I')
        {
            return new ImageFactory();
        }
        return null;
    }
}
