package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    // creates a MaxArrayDeque with the given Comparator
    Comparator<T> cmp;
    public MaxArrayDeque(Comparator<T> c) {
        cmp = c;
    }
    // returns the maximum element in the deque as governed by the previously given Comparator. If the MaxArrayDeque is empty, simply return null.
    public T max() {
        return max(cmp);
    }
    // returns the maximum element in the deque as governed by the parameter Comparator c. If the MaxArrayDeque is empty, simply return null.
    public T max(Comparator<T> c)  {
        if (size() == 0)  return null;

        T cho = get(0);
        for (int i = 0; i < size(); i++) {
            if (c.compare(cho, get(i)) < 0) {
                cho = get(i);
            }
        }
        return cho;
    }

    public static void main(String[] args) {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            }
        });
        mad.addFirst(1);
        mad.addFirst(4);
        mad.addFirst(2);
        mad.addFirst(3);
        mad.addFirst(1);
        System.out.println(mad.max());
        System.out.println(mad.max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.intValue() - o1.intValue();
            }
        }));
    }
}
