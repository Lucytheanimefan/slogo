package model.command.turtle.pen;

import model.animal.Animal;
import model.command.Parameter;

public class PenUp extends TurtlePen {

	private final double numParams = 1;
	
	public PenUp() {
		super();
	}

	/**
	 * Puts pen up so that the animal doesn't leave a trail when it moves
	 * @param params - array of parameters
	 * @return 0
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		return penDown(turtle, 0);
	}
}