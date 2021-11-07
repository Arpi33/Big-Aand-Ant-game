import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

//PART OF OBSERVER DESIGN PATTERN: the GUI is both the Subject and the Client for the Observer pattern
public class GUI extends JFrame {

    //components of GUI
    private JPanel GUIMap;
    private JPanel header;
    private JPanel timeSkipPanel;
    private JPanel headerleft;
    private JPanel headerright;
    private JPanel headerright2;
    private JLabel numAntsLabel;
    private JLabel numBugsLabel;
    private JLabel numAntsEatenLabel;
    private JLabel numBugsDiedLabel;
    private JLabel timestepLabel;
    private JLabel modeSelectLabel;
    private JToggleButton autoManual;
    private JButton timeSkipButton;
    private JLabel[][] mapLabels = new JLabel[20][20];
    private Timer autotimer;

    //Data attributes
    private ArrayList<Organism> organismMap = new ArrayList<>();
    private int numAnts = 0;
    private int numBugs = 0;
    private int numAntsEaten = 0;
    private int numBugsDied = 0;
    private int timestep = 0;

    //Observers
    private ArrayList<Observer> observer = new ArrayList<>();

    //PART OF SINGLETON DESIGN PATTERN: The GUI program can only be created once, and will be stored in this
    //instance
    private static GUI program;

    //Made by Neo Zhi Ming and Arpita
    private GUI()
    {
        //set title of frame
        super("Ants and Bugs");
        new GUILabelObserver(this);
        //initialise components
        initComponents();
    }
    
    public void initComponents()
    {
        //setting size of frame
        setSize(700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //adding a 20x20 grid into a panel
        GUIMap = new JPanel(new GridLayout(20, 20));
        for(int i = 0; i<20; i++)
        {
            for(int j = 0; j<20; j++)
            {
                JLabel cell = new JLabel();
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                GUIMap.add(cell);
                mapLabels[i][j] = cell;
            }
        }

        //Top HUD comprises of 2 parts, left and right
        header = new JPanel(new BorderLayout());
        headerleft = new JPanel(new FlowLayout());
        headerright = new JPanel(new FlowLayout());
        headerright2 = new JPanel(new BorderLayout());

        //Left HUD displays number of ants, bugs and how many ants eaten and how many bugs died, as well as the timestep.
        numAntsLabel = new JLabel("Ants: " + numAnts + "   ");
        numBugsLabel = new JLabel("Bugs: " + numBugs + "   ");
        numAntsEatenLabel = new JLabel("Ants Eaten: " + numAntsEaten + "   ");
        numBugsDiedLabel = new JLabel("Bugs Died: " + numBugsDied + "       ");
        timestepLabel = new JLabel();
        //adds the labels into the left HUD JPanel
        headerleft.add(numAntsLabel);
        headerleft.add(numBugsLabel);
        headerleft.add(numAntsEatenLabel);
        headerleft.add(numBugsDiedLabel);
        headerleft.add(timestepLabel);

        //Right HUD consists of a button that automates/reset to manual input.
        autoManual = new JToggleButton("Manual");
        autoManual.setEnabled(false);
        autoManual.setFocusable(false);
        autoManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoManualActionPerformed(e);
            }
        });
        //adds the button to the right HUD JPanel
        headerright.add(autoManual);

        //Label to tell user what the button is for
        modeSelectLabel = new JLabel("Click on this button to control how to progress the time:   ");

        //at the right side, add the label at the top, and the button at the bottom.
        headerright2.add(modeSelectLabel, BorderLayout.NORTH);
        headerright2.add(headerright, BorderLayout.CENTER);

        //adds both parts of the JPanels to a main HUD JPanel.
        header.add(headerleft, BorderLayout.CENTER);
        header.add(headerright2, BorderLayout.EAST);

        //JPanel that has the button that starts/restarts the simulation and goes to the next time step.
        timeSkipPanel = new JPanel(new BorderLayout());
        timeSkipButton = new JButton("Start");
        timeSkipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeSkipButtonActionPerformed(e);
            }
        });
        timeSkipButton.setFocusable(false);
        //JPanels that are empty to add padding to the button.
        JPanel tempty = new JPanel();
        JPanel tempty2 = new JPanel();
        JPanel tempty3 = new JPanel();
        JPanel tempty4 = new JPanel();
        timeSkipPanel.add(timeSkipButton, BorderLayout.CENTER);
        timeSkipPanel.add(tempty, BorderLayout.EAST);
        timeSkipPanel.add(tempty2, BorderLayout.NORTH);
        timeSkipPanel.add(tempty3, BorderLayout.WEST);
        timeSkipPanel.add(tempty4, BorderLayout.SOUTH);

        //Timer for the automated function.
        autotimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeSkipButtonActionPerformed(e);
            }
        });

        //JPanels that are empty for padding.
        JPanel empty = new JPanel();
        JPanel empty2 = new JPanel();

        //adds all the JPanels into the JFrame
        add(header, BorderLayout.NORTH);
        add(GUIMap, BorderLayout.CENTER);
        add(timeSkipPanel, BorderLayout.SOUTH);
        add(empty, BorderLayout.EAST);
        add(empty2, BorderLayout.WEST);
        //KeyListener that allows user to press Enter to skip to the next time step, also to press A to automate, and M to revert to manual.
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (timeSkipButton.getText().equals("Please click me or press \"Enter\" to go to the next timestep")) {
                        timeSkipButton.doClick();
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_M)
                {
                    if(autoManual.isSelected())
                    {
                        autoManual.doClick();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_A)
                {
                    if(!autoManual.isSelected())
                    {
                        autoManual.doClick();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if(timeSkipButton.getText().equals("Please click me or press \"Enter\" to go to the next timestep"))
                    {
                        timeSkipButton.doClick();
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_M)
                {
                    if(autoManual.isSelected())
                    {
                        autoManual.doClick();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_A)
                {
                    if(!autoManual.isSelected())
                    {
                        autoManual.doClick();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setFocusable(true);
        setResizable(false);
        setVisible(true);


    }

    //ToggleButton function that automates timeskip/reverts timeskip back to manual.
    public void autoManualActionPerformed(ActionEvent e)
    {
        //if user clicks to automate, the button will switch to display the current mode, and starts the automated timer
        if(autoManual.isSelected())
        {
            autoManual.setText("Auto");
            timeSkipButton.setText("Automating timeskip...");
            timeSkipButton.setEnabled(false);
            autotimer.start();
        }
        //if user clicks to revert back to manual, the button will switch to display the current mode, and stops the automated timer.
        else
        {
            autoManual.setText("Manual");
            timeSkipButton.setEnabled(true);
            timeSkipButton.setText("Please click me or press \"Enter\" to go to the next timestep");
            autotimer.stop();
        }


    }
    
    //The function for the button to start/restart the simulation and skip to the next time step.
    public void timeSkipButtonActionPerformed(ActionEvent e)
    {
        //The function will check the text of the button to know what function it should run.
        if(timeSkipButton.getText().equals("Start")) {
            //Random generator to randomly spawn Organisms in different cells of the grid.
            Random rGen = new Random();
            //ArrayList that ensures numbers do not repeat and that all randomised numbers only occur once. This ensures no organisms land on the same spot.
            ArrayList<Integer> usednumbers = new ArrayList<>();
            //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates a factory producer to create an AbstractFactory that will hold an Organism Factory
            FactoryProducer factoryProd = new FactoryProducer();
            AbstractFactory factory = factoryProd.getFactory('O');
            //variable that stores the random number
            int index;
            //variable that determines how many ants should be created
            int ants = 0;
            //variable that determines how many bugs should be created
            int bugs = 0;


            //Loop that loops 100 times for 100 ants
            while (ants < 100) {
                //generate a number from 0 to 399 that represents the cell number
                index = rGen.nextInt(400);
                //if the number hasn't reoccured yet
                if (!usednumbers.contains(index)) {
                    //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates an ant with the factory
                    organismMap.add((Organism) factory.getOrganism('A'));
                    //the cell number has its rows and columns, every 20 in the cell number corresponds to one row, and the remainder corresponds to the column number. It gets the recently added organism and set the row (number/20) and the column (number%20)
                    organismMap.get(organismMap.size()-1).setX(index/20);
                    organismMap.get(organismMap.size()-1).setY(index%20);
                    //adds the generated number into a number arraylist, representing that the number has already occured once
                    usednumbers.add(index);
                    ants++;
                }
            }

            //Loop that loops 5 times for 5 bugs
            while (bugs < 5) {
                //same logic as Ant creation
                index = rGen.nextInt(400);
                if (!usednumbers.contains(index)) {
                    //PART OF ABSTRACT FACTORY DESIGN PATTERN: Creates a bug with the factory
                    organismMap.add((Organism) factory.getOrganism('B'));
                    //Same as above
                    organismMap.get(organismMap.size()-1).setX(index/20);
                    organismMap.get(organismMap.size()-1).setY(index%20);
                    //Same as above
                    usednumbers.add(index);
                    bugs++;
                }

            }


            //after the organism ArrayList is changed, observer is notified to update the GUI.
            notifyAllObservers();


            ants = 0;
            bugs = 0;

            //counts how many ants or bugs there are after eating, breeding etc.
            for(Organism org: organismMap)
            {
                if(org.getOrganismType() == 'A')
                {
                    ants++;
                }
                else if(org.getOrganismType() == 'B')
                {
                    bugs++;
                }
            }

            numAnts = ants;
            numBugs = bugs;

            //updates the HUD based on how many ants and bugs there are
            numAntsLabel.setText("Ants: " + numAnts + "   ");
            numBugsLabel.setText("Bugs: " + numBugs + "   ");


            //updates the timeskip label to show the current timeskip.
            timestepLabel.setText("Timestep: " + timestep);
            //sets the button with a different text so that it executes the proper function.
            timeSkipButton.setText("Please click me or press \"Enter\" to go to the next timestep");
            autoManual.setEnabled(true);


        }
        //the function if the timestep is to be skipped.
        else if(timeSkipButton.getText().equals("Please click me or press \"Enter\" to go to the next timestep") || timeSkipButton.getText().equals("Automating timeskip...") )
        {
            //The number of bugs bred after moving
            int bugsBred = 0;
            //Since after breeding, the organisms appended to the back of the arraylist doesn't matter, we only iterate through the size of the arraylist before modification.
            int orgsize = organismMap.size();
            //Moves bugs first
            for(int i = 0; i<orgsize; i++){

                if(organismMap.get(i).getOrganismType() == 'B')
                {
                    organismMap.get(i).move(organismMap);
                }
            }

            //Counts the number of bugs after breeding the starved ones are marked for death, but haven't disappeared from the arraylist yet.
            for(Organism org: organismMap)
            {
                if(org.getOrganismType() == 'B') {
                    bugsBred++;
                }
            }

            //The bred bugs are equal to the difference between total number of bugs after breeding and before breeding.
            bugsBred = bugsBred - numBugs;

            //Kills all the bugs and ants that are marked for death.
            organismMap.removeIf(org -> (org.getLife() == false));

            //The number of ants that has been eaten at this time.
            int numAntsEatenAtTimeStep = 0;

            //Count the total of ants after they are eaten.
            for(Organism org: organismMap)
            {
                if(org.getOrganismType() == 'A') {
                    numAntsEatenAtTimeStep++;
                }

            }

            //The number of ants that has been eaten at this time is equal to the difference between the number of ants before being eaten and the number of ands after being eaten.
            numAntsEatenAtTimeStep = numAnts - numAntsEatenAtTimeStep;


            //Since after breeding, the organisms appended to the back of the arraylist doesn't matter, we only iterate through the size of the arraylist before modification.
            orgsize = organismMap.size();
            //Ants move after bugs move
            for(int i = 0; i< orgsize; i++)
            {
                if(organismMap.get(i).getOrganismType() == 'A')
                {
                    organismMap.get(i).move(organismMap);
                }
            }

            //After the arraylist of organisms have been modified, the program will notify the observer so that the observer will update the GUI.
            notifyAllObservers();

            int ants = 0;
            int bugs = 0;

            //counts the number of ants and bugs after breeding and eating
            for(Organism org: organismMap)
            {
                if(org.getOrganismType() == 'A')
                {
                    ants++;
                }
                else if(org.getOrganismType() == 'B')
                {
                    bugs++;
                }
            }

            //The number of bugs that have died at this time is equal to the number of bugs before breeding and dying minus the difference between the bugs after dying and breeding and the bugs after breeding.
            int numBugsDiedAtTimeStep = numBugs - (bugs-bugsBred);
            //updating the HUD variables
            numBugsDied = numBugsDied + numBugsDiedAtTimeStep;
            numAntsEaten = numAntsEaten + numAntsEatenAtTimeStep;
            numAnts = ants;
            numBugs = bugs;

            //updating the HUD labels.
            numAntsLabel.setText("Ants: " + numAnts + "   ");
            numBugsLabel.setText("Bugs: " + numBugs + "   ");
            numAntsEatenLabel.setText("Ants Eaten: " + numAntsEaten + "   ");
            numBugsDiedLabel.setText("Bugs Died: " + numBugsDied + "       ");

            //One timestep has passed
            timestep++;
            //Updates the timestep.
            timestepLabel.setText("Timestep: " + timestep);

            //If there are no more organisms on the board or if the whole board is full of ants, the simulation is over.
            if(numAnts == 400 || numAnts + numBugs == 0)
            {
                //if the auto function is selected, the button to skip manually would have been disabled. This is to re-enable it.
                if(autoManual.isSelected())
                {
                    timeSkipButton.setEnabled(true);
                }
                //changes the button so that it runs the restart function.
                timeSkipButton.setText("Simulation ended. Please click this button to restart.");
                //Resets the auto/revert to manual button and stops the automatic function to timeskip.
                autoManual.setEnabled(false);
                autoManual.setSelected(false);
                autoManual.setText("Manual");
                autotimer.stop();
            }
        }
        //function to restart
        else if(timeSkipButton.getText().equals("Simulation ended. Please click this button to restart."))
        {
            //The organisms are cleared from the arraylist
            organismMap.clear();
            //The GUI is updated by the observer, since the organism arraylist has changed
            notifyAllObservers();
            //resets the HUD variables
            numAnts = 0;
            numBugs = 0;
            numAntsEaten = 0;
            numBugsDied = 0;
            timestep = 0;
            //resets the HUD labels
            numAntsLabel.setText("Ants: " + numAnts + "   ");
            numBugsLabel.setText("Bugs: " + numBugs + "   ");
            numAntsEatenLabel.setText("Ants Eaten: " + numAntsEaten + "   ");
            numBugsDiedLabel.setText("Bugs Died: " + numBugsDied + "       ");
            //resets the timestep label.
            timestepLabel.setText("Timestep: " + timestep);

            timeSkipButton.setText("Start");
        }
    }

   
    //PART OF OBSERVER DESIGN PATTERN: attaches an observer to the subject to be observed(this)
    public void attach(Observer observer)
    {
        this.observer.add(observer);
    }

    
    //PART OF OBSERVER DESIGN PATTERN: notifies the observer if the ArrayList has been modified.
    public void notifyAllObservers() {
        for(Observer obs : observer)
        {
            obs.update();
        }
    }

 
    //Returns the labels that comprises the grid of Organisms
    public JLabel[][] getMapLabels()
    {
        return mapLabels;
    }

  
    //Returns the Organism ArrayList that has all the data of the organisms.
    public ArrayList<Organism> getOrganismMap() {
        return organismMap;
    }

   
    //PART OF SINGLETON DESIGN PATTERN: Gets the instance of one GUI program, if there is no program, create it once,
    //and return it afterwards.
    public static GUI getInstance()
    {
        if (program == null)
        {
            program = new GUI();
            return program;
        }
        else
        {
            return program;
        }
    }


   
    //main function
    public static void main(String[] args)
    {
        //PART OF SINGLETON DESIGN PATTERN: The GUI program will be instantiated once.
        GUI program = GUI.getInstance();
    }

}
