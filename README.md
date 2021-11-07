# Big-Aand-Ant-game

<p><h4>Within this assignment, Object Oriented Java programming language was used in the making of the following simulation, and factory design pattern was implemented during the making of this project. We were given the following requirements:

There are two creatures – ants and bugs – and they live in a grid world of size 20 x 20 cells. Your task is to simulate a simple 2D simulation of predator (bugs) and prey (ants) behaviours. 
	
The grid world is enclosed so the creatures can’t move out of the grid world and only one creature may occupy a cell at a time. The time is simulated in time steps and the creatures perform some action every time step. 

The ants behave according to the following model: 
•	Move: At every time step it moves to the next cell randomly top, bottom, left , or right direction. If the cell is occupied or out of boundary, then it does not move. 
•	Breed: A new ant will be created at an adjacent (up, down, left, or right) empty cell if it survives at the end of third time step (i.e. after moving). If no empty cell available no breeding occurs. After that, the ant can’t reproduce until three more time stamp elapsed.

The bugs behave according to the following model: 
•	Move: It is same as the ant’s move model, except at each time step, if there is an adjacent cell occupied by an ant, the bug will move to that cell and eat the ant. But a bug can’t eat another bug. 
•	Breed: it is the same as the ant’s model except the time step required is eight. 
•	Starve: if a bug has not eaten an ant in the last three time step moves, it will die at the end of the third time step and should be removed from the grid. 

During one turn, all the bugs should move before the ants. 

Write a Graphical User Interface (GUI) program to implement this simulation

We were also asked to use images to represent the bugs and ants, and use a superclass of Organism to encapsulate all data variables for Bugs and Ants.
Moreover, we were also asked to have an overridden method called move that is implemented by the Bug and Ant class.
The Ants and Bugs world will be initialised with 100 ants and 5 bugs, and the user will be prompted to press Enter to go to the next time step.
	</h4></p>
