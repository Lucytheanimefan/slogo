package model.command.turtle.rotation;

import model.animal.Animal;
import model.command.Parameter;

public class Right extends TurtleRotation {

	private final double numParams = 2;
	
	public Right() {
		super();
	}

	/**
	 * Turns the animal counterclockwise by a specified number of degrees
	 * @param params - array of parameters
	 * @return the number of degrees
	 */
	@Override
	public double run(Parameter[] params) {
		double degrees = params[0].getValue();
		Animal turtle = params[1].getAnimal();
		return turn(turtle, degrees, 0);
	}
}