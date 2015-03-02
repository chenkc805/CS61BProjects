package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class   
 *  @authr FIXME
 */

public class TestClorus {

    @Test
    public void testChooseStayImpassible() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible()); 

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseStayPlip() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Plip());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseAttack() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> clear = new HashMap<Direction, Occupant>();
        clear.put(Direction.TOP, new Empty());
        clear.put(Direction.BOTTOM, new Empty());
        clear.put(Direction.LEFT, new Plip());
        clear.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(clear);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseReplicate() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> clear = new HashMap<Direction, Occupant>();
        clear.put(Direction.TOP, new Impassible());
        clear.put(Direction.BOTTOM, new Empty());
        clear.put(Direction.LEFT, new Impassible());
        clear.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(clear);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseMove() {
        Clorus c = new Clorus(0.2);
        HashMap<Direction, Occupant> clear = new HashMap<Direction, Occupant>();
        clear.put(Direction.TOP, new Impassible());
        clear.put(Direction.BOTTOM, new Empty());
        clear.put(Direction.LEFT, new Impassible());
        clear.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(clear);
        Action expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

        assertEquals(expected, actual);
    }
    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
} 
