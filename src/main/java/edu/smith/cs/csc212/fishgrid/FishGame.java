package edu.smith.cs.csc212.fishgrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class manages our model of gameplay: missing and found fish, etc.
 * @author jfoley
 *
 */
public class FishGame {
	/**
	 * This is the world in which the fish are missing. (It's mostly a List!).
	 */
	World world;
	/**
	 * The player (a Fish.COLORS[0]-colored fish) goes seeking their friends.
	 */
	Fish player;
	
	// (Fish.COLORS[.length-1] ) Our Extra points (15) fish
	Fish goldy;
	/**
	 * The home location.
	 */
	FishHome home;
	/**
	 * These are the missing fish!
	 */
	List<Fish> missing;
	
	/**
	 * These are fish we've found!
	 */
	List<Fish> found;
	
	List<Fish> returned;
	
	/**
	 * Number of steps!
	 */
	int stepsTaken;
	
	/**
	 * Score!
	 */
	int score;
	
	// number of rocks!
	int stones;
	
	// number of food squares
	int food;
	
	// positions for click
	int x1;
	int y1;
	
	// our snail friend gary!
	// P2Snail snail;
	
	/**
	 * Create a FishGame of a particular size.
	 * @param w how wide is the grid?
	 * @param h how tall is the grid?
	 */
	public FishGame(int w, int h) {
		world = new World(w, h);
		
		missing = new ArrayList<Fish>();
		found = new ArrayList<Fish>();
		returned = new ArrayList<Fish>();
		
		// Add a home!
		home = world.insertFishHome();
		
		
		stones = 10;
		for (int i=0; i< stones; i++) {
			world.insertRockRandomly();
		}
		
		// Make our Soylent green squares
		food = 5;
		for (int i=0; i< food; i++) {
			world.insertFoodRandomly();
		}
		
		// snail = new P2Snail(world);
		world.insertSnailRandomly();
		
		// Make the player out of the 0th fish color.
		player = new Fish(0, world);
		// Start the player at "home".
		player.setPosition(home.getX(), home.getY());
		player.markAsPlayer();
		world.register(player);
		
		// Make a fish worth more points!
		goldy = new Fish(1, world);
		
		// Generate fish of all the colors but the first into the "missing" List.
		for (int ft = 1; ft < Fish.COLORS.length; ft++) {
			Fish friend = world.insertFishRandomly(ft);
			missing.add(friend);
		}
	}
	
	/**
	 * How we tell if the game is over: if missingFishLeft() == 0.
	 * @return the size of the missing list.
	 */
	public int missingFishLeft() {
		return missing.size();
	}
	
	/**
	 * This method is how the Main app tells whether we're done.
	 * @return true if the player has won (or maybe lost?).
	 */
	public boolean gameOver() {
		/* if the position of the player equals the home position,
		 * for all the fish following us, remove them from the world
		 * and add them to a "returned" Fish list
		 * Clear the found list too
		 */
		if (player.getX() == home.getX() && player.getY() == home.getY())  {
			
			for (WorldObject fish : found) {
				world.remove(fish);
				returned.add((Fish) fish);
			}
			found.clear();
		}
		/* Once all the fish have been added to returned/taken home,
		 *  which we figure out by counting total number of fish except for player,
		 * The game ends
		 */
		// if missing.fish.getX = home.X then add it to returned and remove it from missing
		return returned.size() == Fish.COLORS.length-1;				
	} 

	/**
	 * Update positions of everything (the user has just pressed a button).
	 */
	public void step() {
		// Keep track of how long the game has run.
		this.stepsTaken += 1;
		
		// Make sure missing fish *do* something.
		wanderMissingFish();
				
		// These are all the objects in the world in the same cell as the player.
		List<WorldObject> overlap = this.player.findSameCell();
		// The player is there, too, let's skip them.
		overlap.remove(this.player);
		
		// If we find a fish, remove it from missing.
		for (WorldObject thing : overlap) {
			// It is missing if it's in our missing list.
			if (missing.contains(thing)) {
				
				// Casting thing into Fish class f (help from TA)
				Fish f = (Fish) thing;
				// Remove this fish from the missing list.
				missing.remove(f);
				
				// Remove from world.
				found.add(f);
				
				/* Increase score when you find a fish!
				* The last color in our list is our orange  fish, worth an additional 10 points, 
				* so 20 total
				* Other fish are 10 points
				*/
				if (f.colorIndex == (Fish.COLORS.length-1)) {
					score += 20;
				} 	else { score += 10; 
					}
		
			} 
			// TODO fish food 
			if (thing instanceof FishFood) {
				FishFood m = (FishFood) thing;
				if (overlap.contains(m) ) {
					score += 2;
					world.remove(m);
			}
			
		} 
		}
			
		
		
		
		// When fish get added to "found" they will follow the player around.
		World.objectsFollow(player, found);
		// Step any world-objects that run themselves.
		world.stepAll();
	}
	
	/**
	 * Call moveRandomly() on all of the missing fish to make them seem alive.
	 */
	private void wanderMissingFish() {
		Random rand = ThreadLocalRandom.current();
		for (Fish lost : missing) {
			// 30% of the time, lost fish move randomly.
			if (rand.nextDouble() < 0.2) {
				lost.moveRandomly();
				
			}
		}
	}

	/**
	 * This gets a click on the grid. We want it to destroy rocks that ruin the game.
	 * @param x - the x-tile.
	 * @param y - the y-tile.
	 */
	public void click(int x, int y) {
		// TODO(FishGrid) use this print to debug your World.canSwim changes! DONE?
	System.out.println("Clicked on: "+x+","+y+ " world.canSwim(player,...)="+world.canSwim(player, x, y));
	
		List<WorldObject> atPoint = world.find(x, y);
		
		for (WorldObject stuff : atPoint) {
			if (stuff instanceof Rock) {
			world.remove(stuff);
		} }
	

			}
	} 
	


