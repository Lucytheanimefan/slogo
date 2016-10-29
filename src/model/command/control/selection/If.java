package model.command.control.selection;

import java.util.ArrayList;

import Parsing.ExpressionTree;
import Parsing.TreeNode;
import model.animal.Animal;
import model.command.Command;
import model.command.CommandProcessor;
import model.command.Parameter;
import model.command.control.ControlCommand;

public class If extends ControlCommand {
	private final double paramCount;
	
	public If() {
		super();
		numParams = 3;
		paramCount = 2;
	}
	
	/**
	 * Runs commands if a given expression is not 0
	 * @param params - array of parameters
	 * @return the value of the final command executed or 0 if no command is executed
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		double expression = params[1].getValue();
		TreeNode node = params[2].getNode();
		
		double value = 0;
		if (expression != 0) {
			value = ExpressionTree.getInstance().process(turtle, node);
			return value;
		} else {
			return 0;
		}
	}
}