##Code outline

* .game: Only the main game and app preferences right now 
* .game.actors: Actors, since this was a tutorials seems to only be an old none ECS method of Actors for the loading screen
* .game.controllers: Only a keyboard controller from now, will need game pad and touch screen at some point.
* .game.components: All the ECS components 
* .game.systems: All the ECS systems
* .game.loader: The Depths asset loader
* .game.physics: The box 2d physics listener
* .game.physics.factory: Two classes, a level factory that uses the class body factory
* .game.simplexnoise: OpenSimplexNoise
* .game.util: DFUtils a utils class for textures etc.
* .game.view: All the screens in the game.


The Depths class has a preferences object this is used in a the debug physics system.
The MainScreen needs a pause button that will allow music to be turned on and off, etc.