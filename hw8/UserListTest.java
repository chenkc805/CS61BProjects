import queue.*;
import static org.junit.Assert.*;

import org.junit.Test;

/** Class that demonstrates basic WordNet functionality.
 *  @author Josh Hug
 */
public class UserListTest {

    @Test
    public void testQuickSort() {
        CatenableQueue<User> q = new CatenableQueue<User>();
        User a = new User(10, 3);
        User b = new User(2, 4);
        User c = new User(12, 12);
        User d = new User(8, 5);
        User e = new User(19, 14);
        User f = new User(2, 7);
        q.enqueue(a);
        q.enqueue(b);
        q.enqueue(c);
        q.enqueue(d);
        q.enqueue(e);
        q.enqueue(f);
        System.out.println(q);
        UserList.quickSort("id", q);
        System.out.println(q);
        UserList.quickSort("pages", q);
        System.out.println(q);
        System.out.println("===================");
        System.out.println("");
    }

    @Test
    public void testMergeSort() {
        UserList list = new UserList();
        list.add(new User(10, 3));
        list.add(new User(2, 4));
        list.add(new User(12, 12));
        list.add(new User(12, 5));
        list.add(new User(19, 14));
        System.out.println(list);
        list.mergeSort("id");
        System.out.println(list);
        list.mergeSort("pages");
        System.out.println(list);
        System.out.println("===================");
        System.out.println("");
    }

    @Test
    public void testSortBothFeatures() {
        UserList list = new UserList();
        list.add(new User(10, 3));
        list.add(new User(2, 4));
        list.add(new User(12, 12));
        list.add(new User(12, 5));
        list.add(new User(19, 14));
        list.add(new User(12, 7));
        list.add(new User(2, 7));
        System.out.println(list);
        list.sortByBothFeatures();
        System.out.println(list);
        System.out.println("===================");
        System.out.println("");
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(UserListTest.class);
    }                 
} 
