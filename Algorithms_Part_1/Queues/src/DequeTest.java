import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DequeTest {

    @Test
    public void testDeque(){
        Deque te = new Deque();
        assertTrue(te.isEmpty());
        assertTrue(te.size() == 0);
        te.addFirst("hzy");
        assertTrue(te.toString().equals("hzy"));
        assertTrue(te.size() == 1);
        te.addFirst("gzy");
        assertTrue(te.toString().equals("gzy hzy"));
        assertTrue(te.size() == 2);
        te.addFirst("zy");
        assertTrue(te.toString().equals("zy gzy hzy"));
        assertTrue(te.size() == 3);
        te.addLast("zz");
        assertTrue(te.toString().equals("zy gzy hzy zz"));
        assertTrue(te.size() == 4);
        te.addLast("zz");
        assertTrue(te.toString().equals("zy gzy hzy zz zz"));
        assertTrue(te.size() == 5);
        assertTrue(te.removeFirst().equals("zy"));
        assertTrue(te.toString().equals("gzy hzy zz zz"));
        assertTrue(te.size() == 4);
        assertTrue(te.removeLast().equals("zz"));
        assertTrue(te.toString().equals("gzy hzy zz"));
        assertTrue(te.size() == 3);
        assertTrue(te.removeLast().equals("zz"));
        assertTrue(te.toString().equals("gzy hzy"));
        assertTrue(te.size() == 2);
        assertTrue(te.removeLast().equals("hzy"));
        assertTrue(te.toString().equals("gzy"));
        assertTrue(te.size() == 1);
        assertTrue(te.removeLast().equals("gzy"));
        assertTrue(te.size() == 0);
    }
}
