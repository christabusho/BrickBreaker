# BrickBreaker

=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: dushime
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List of the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept.

  1. 2D Array : I implemented that stores the different coordinates of all the
  bricks to be drawn. It is iterated through to first read out from a file that
  contains the coordinates and again when to create the bricks since we need the
  coordinates to create the bricks. The 2D array was really helpful in keeping
  track of the x and y coordinates before creating a brick.

  2. Inheritance: I applied inheritance in extending the abstract class PowerUp
     by ScoreUp, Extender, ExtraLives and Slowdown. The power ups had some functions
    in common like move(), getters and setters but their apply() functions were
    implemented differently, because each power up has its effect on the game,
    then dynamic dispatch was agood way of doing that.

  3. Collections: I use 3 linked lists. The first one stores the different bricks to
   draw, the second one stores the powerups created and the last one the powerups to
   remove. The linked list was an appropriate collection to use because I wanted to
   do an action for each component in the list one by one.

  4. file I/O: I use the file I/O to read the file that contains the positions of the
  brick to draw and to catch any exceptions in the level class. I used a buffered
  reader to read the file line by line and extract from it the coordinate.


=========================
=: Implementation :=
=========================

An overview of each of the classes in your code, and what their
  function is in the overall game.

 Ball.java is a subclass of the GameObj class, representing the game's ball that
 bounces off the paddle, walls, and bricks. Its attributes include size, position,
 velocity, and number of lives.

 Brick.java is another subclass of GameObj, representing the game's fixed bricks
 that the ball hits, sometimes containing a powerup. Its attributes include size,
 position, and velocity.

 Coordinates.java is a class that defines a coordinate on the screen, represented
 by an x and y position.

 Direction.java defines an enumeration called Direction that specifies the direction
 an object should move after colliding with another object. This enumeration is used
 in GameObj and its subclasses.

 Game.java initializes the runnable game class of your choosing and runs it.

 GameObj.java is an abstract class that serves as a base for all the game's components,
 including the ball, brick, paddle, and powerup.

 Level.java reads a file that contains brick coordinates and returns a 2D array of all
 the bricks' positions.


 Paddle.java is a subclass of GameObj that represents the game's paddle, which moves
 horizontally when the user presses the left or right keys. Its attributes include
 size, position, and velocity. The paddle's role is to intersect with the ball so
 that it bounces and catches powerups.

 RunBrickBreaker.java is the main class that defines the GUI frame and widgets. It
 includes a reset button and a button to launch the ball.

 Screen.java is the class that manages the game's primary logic, including the paddle,
 ball, bricks, score, game state, brick positions, and powerups. It also includes an
 inner class for powerups. Powerups appear when certain bricks are destroyed and can
 provide various benefits to the player. Screen repaints the GUI on every update, checking
 for collisions and handling hits, updates, and game-ending conditions.


