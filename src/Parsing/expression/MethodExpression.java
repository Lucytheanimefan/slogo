/**
 * This is the MethodExpression class, which extends the expression class and is used to process commands that are entered so that 
 * their parameters are entered appropriately
 * 
 * @author Teddy Franceschi
 * @author Aninda Manocha
 */

package Parsing.expression;

import java.util.ArrayList;
import Controller.Data;
import ErrorHandling.Errors;
import Parsing.TreeNode;
import View.helper.Coordinate;
import model.animal.Animal;
import model.command.Command;
import model.command.ListCommand;
import model.command.NewCommand;
import model.command.Parameter;
import model.command.control.variable.To;

public class MethodExpression extends Expression{

    private Class<Command> method;
    
    public MethodExpression (String name, Class<Command> method) {
        super(name);
        this.method = method;
    }
    
    @Override
    public double run(Animal turtle, TreeNode node) {
    	double value = 0;
    	Object obj = null;
		try {
			obj = method.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Command command = (Command)obj;
		String prevCurrentCommand = null;
		if (command instanceof NewCommand) {
			if (Data.getInstance().containsCommand(node.toString())) {
				command = Data.getInstance().getCommand(node.toString());
				prevCurrentCommand = ExpressionTree.getInstance().getCurrentCommand();
				ExpressionTree.getInstance().setCurrentCommand(command.getName());
			} else {
				Errors.getInstance().displayError("Invalid Input Error!", "Invalid Command Name", 
						"You have entered a command name that does not exist.");
				return -1;
			}
		}
		Parameter[] parameters = new Parameter[(int)command.getNumParams()];
		int paramIndex = 1;
		int endIndex = node.getChildren().size();
		parameters[0] = new Parameter(turtle);
		if (command instanceof To) {
			node = node.getChildren().get(0); //node containing command name
			parameters[1] = new Parameter(node.toString());
			paramIndex++;
			endIndex = node.getChildren().size();
		}
		for (int c = 0; c < endIndex; c++) {
			if (command instanceof ListCommand) {
				TreeNode listNode = node.getChildren().get(c);
				ArrayList<TreeNode> list = createChildrenNodeList(listNode);
				parameters[paramIndex] = new Parameter(list);
				paramIndex++;
			} else {
				parameters[c+1] = new Parameter(node.getChildren().get(c));
			}
		}
		value = command.run(parameters);
		if (ExpressionTree.getInstance().getCurrentCommand() != null && !ExpressionTree.getInstance().getCurrentCommand().equals(prevCurrentCommand) 
				&& command instanceof NewCommand) {
			ExpressionTree.getInstance().setCurrentCommand(prevCurrentCommand); //reset so that a local variable isn't always returned
		}
		Coordinate coordinates = new Coordinate(turtle.getX(), turtle.getY(), turtle.getHeading(), turtle.getPen(), turtle.getShowing());
		ExpressionTree.getInstance().addPoint(coordinates);
		return value;
    }
    
    public ArrayList<TreeNode> createChildrenNodeList(TreeNode listNode) {
    	ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
    	for (int n = 0; n < listNode.getChildren().size(); n++) {
			nodes.add(listNode.getChildren().get(n));
		}
    	return nodes;
    }
    
    /**
     * Gets the name of the command corresponding to this node
     * @return command name
     */
    public String getMethodType() {
    	return method.getSimpleName();
    }
}
