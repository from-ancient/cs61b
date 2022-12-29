package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

public class TestDemo {
    @Test
    public void testThreeAddThreeRemove () {
        AListNoResizing<Integer> ref = new AListNoResizing<Integer>();
        BuggyAList <Integer> test = new BuggyAList<Integer>();

        ref.addLast(1);
        ref.addLast(2);
        ref.addLast(3);
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);

        Assert.assertEquals(ref.removeLast(), test.removeLast());
        Assert.assertEquals(ref.removeLast(), test.removeLast());
        Assert.assertEquals(ref.removeLast(), test.removeLast());
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L2 = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L2.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size_1 = L.size();
                int size_2 = L.size();

                Assert.assertEquals(size_1, size_2);
                // System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() != 0) {
                // getLast
                int lastVal_1 = L.getLast();
                int lastVal_2 = L2.getLast();

                Assert.assertEquals(lastVal_1, lastVal_2);
                // System.out.println("getLast(" + lastVal_1 + ")");
            } else if (operationNumber == 3 && L.size() != 0) {
                // removeLast
                int removeVal_1 = L.removeLast();
                int removeVal_2 = L2.removeLast();
                Assert.assertEquals(removeVal_1, removeVal_2);
                // System.out.println("removeLast(" + removeVal + ")");
            }
        }
    }
}
