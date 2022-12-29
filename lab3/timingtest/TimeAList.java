package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();


        for  (int bunch = 1000; bunch <= 128000; bunch *= 2) {
            /* for branch i = 1000, 2000, ... 128000, test its proformane */
            Stopwatch sw = new Stopwatch(); // test start
            AList<Integer> lst = new AList<Integer>();
            for (int i = 0; i < bunch; i++) {
                lst.addLast(i);
            }
            /* record the result */
            times.addLast(sw.elapsedTime()); // test end
            Ns.addLast(bunch);
            opCounts.addLast(bunch);
        }
        printTimingTable(Ns, times, opCounts);
    }
}
