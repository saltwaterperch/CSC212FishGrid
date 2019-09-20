package edu.smith.cs.csc212.fishgrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * It would be awful nice to have multi-colored rocks at random.
 * This is not <a href="https://en.wikipedia.org/wiki/Dwayne_Johnson">the Rock</a>, but a Rock.
 * @author jfoley
 */

// extends worldobject is bascially copy and paste inheritance 
public class Rock extends WorldObject {
	/**
	 * I took these colors from Wikipedia's Cool and Warm Gray sections.
	 * https://en.wikipedia.org/wiki/Shades_of_gray#Cool_grays
	 * https://en.wikipedia.org/wiki/Shades_of_gray#Warm_grays
	 */
	// storing each color as an int, from the index position
	int color;
	
	private static Color[] ROCK_COLORS = new Color[] {
			new Color(144,144,192),
			new Color(145,163,176),
			new Color(112,128,144),
			new Color(94,113,106),
			new Color(76,88,102),
			new Color(170,152,169),
			new Color(152,129,123),
			new Color(138,129,141),
			new Color(72,60,50)
	};
	
	
	
	// method that puts our list of colors into variable color as an interger index position
	public Color getColor() {
		return ROCK_COLORS[this.color];
	}
	
	/**
	 * Construct a Rock in our world.
	 * @param world - the grid world.
	 */
	public Rock(World world) {
		super(world);
		// pick a random number from the length of our list
		// color is a variable that is part of the class, so we don't choose it when it's drawn
		this.color = rand.nextInt(ROCK_COLORS.length);
	}

	/**
	 * Draw a rock!
	 */
	@Override
	public void draw(Graphics2D g) {
		// retrieve color from our method 
		g.setColor(this.getColor());
		RoundRectangle2D rock = new RoundRectangle2D.Double(-.5,-.5,1,1,0.3,0.3);
		g.fill(rock);
	}

	@Override
	public void step() {
		// Rocks don't actually *do* anything.		
	}

}
