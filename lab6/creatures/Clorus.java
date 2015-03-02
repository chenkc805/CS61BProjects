package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
	private int r;
	private int g;
	private int b;

	public Clorus(double e) {
		super("clorus");
		r = 34;
		g = 0;
		b = 231;
		energy = e;
	}

	public Clorus() {
		this(1);
	}

	public Color color() {
		return color(r,g,b);
	}

	public void move() {
		energy -= 0.03;
	}

	public void stay() {
		energy -= 0.01;
	}

	public void attack(Creature c) {
		energy += c.energy();
	}

	public Clorus replicate() {
        double result = 0.5*energy ;
        energy = result;
        return new Clorus(result);
	}

	public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (plips.size() > 0) {
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}