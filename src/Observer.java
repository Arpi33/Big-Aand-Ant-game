//Made by Arpita, all methods made by Arpita
//PART OF OBSERVER DESIGN PATTERN: An Observer super class
abstract public class Observer {
    //The GUI that it is Observing
    protected GUI program;

    //to update the GUI
    abstract public void update();
}
