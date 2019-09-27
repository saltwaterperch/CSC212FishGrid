package edu.smith.cs.csc212.fishgrid;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class FishFood extends WorldObject {
	
	Color color;
	
	
	// method that puts our list of colors into 
	// variable color as an integer index position
	public Color getColor() {
		return color.green;
	}
	
	/**
	 * Construct a Rock in our world.
	 * @param world - the grid world.
	 */
	public FishFood(World world) {
		super(world);
		// pick a random number from the length of our list
		// color is a variable that is part of the class,
		//so we don't choose it when it's drawn
		this.color = Color.green;
	}

	/**
	 * Draw fishfood!
	 */
	@Override
	public void draw(Graphics2D g) {
		// retrieve color from our method 
		g.setColor(this.getColor());
		RoundRectangle2D food = new RoundRectangle2D.Double(-.5,-.5,1,1,0.3,0.3);
		g.fill(food);
	}

	@Override
	public void step() {
		// Rocks don't actually *do* anything.		
	}

}

	
	

