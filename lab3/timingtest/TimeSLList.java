package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();

        int M = 10000;
        for  (int bunch = 1000; bunch <= 128000; bunch *= 2) {
            /* for branch i = 1000, 2000, ... 128000, test its proformane */
            SLList<Integer> lst = new SLList<Integer>();
            for (int i = 0; i < bunch; i++) {
                lst.addLast(i);
            }
            /* record the result */
            Stopwatch sw = new Stopwatch(); // test start
            for (int i = 0; i < M; i++) {
                lst.getLast();
            }
            times.addLast(sw.elapsedTime()); // test end
            Ns.addLast(bunch);
            opCounts.addLast(M);
        }
        printTimingTable(Ns, times, opCounts);
    }
}
