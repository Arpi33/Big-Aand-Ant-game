//Made by Neo Zhi Ming, all methods made by Neo Zhi Ming
//PART OF ABSTRACT FACTORY DESIGN PATTERN: The Abstract factory has a getOrganism function that returns an Object
//since it can either be an Organism, or an ImageIcon of an organism.
abstract public class AbstractFactory {

    abstract public Object getOrganism(char organismtype);

}
