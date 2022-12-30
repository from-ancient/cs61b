package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int maxSize;
    private int size;
    private int front;
    private int tail;
    private double R;

    public ArrayDeque() {
        maxSize = 8;
        items = (T[]) new Object[maxSize];
        front = 0;
        tail  = 0;
        size  = 0;
    }

    @Override
    public void addFirst(T item) {
        if (size >= maxSize - 1) {
            reSizing((int)(maxSize * 1.5));
        }
        if (--front < 0)
            front = maxSize - 1;
        items[front] = item;
        size ++;
    }

    @Override
    public void addLast(T item) {
        if (size >= maxSize - 1) {
            reSizing((int)(maxSize * 1.5));
        }
        items[tail++] = item;
        if (tail == maxSize)
            tail %= maxSize;
        size ++;
    }

    public void reSizing(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        if (front <= tail) {
            System.arraycopy(items, front, newArray, 0, size);
        } else {
            System.arraycopy(items, front, newArray, 0, size - front + 1);
            System.arraycopy(items, 0, newArray, size - front + 1, tail + 1);
        }
        items = newArray;
        maxSize = capacity;
        front = 0;
        tail  = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            System.out.println(" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        T ret = null;
        if (size > 0) {
            ret = items[front];
            if (++ front == maxSize)
                front = 0;
            size --;

            if((R = (double) size / maxSize) < 0.25) {
                // System.out.println("down size !");
                reSizing(maxSize / 2);
            }
        }
        return ret;
    }

    @Override
    public T removeLast() {
        T ret = null;
        if (size > 0) {
           tail = tail == 0 ? maxSize - 1 : tail - 1;
           size --;
           ret = items[tail];

            if((R = (double) size / maxSize) < 0.25) {
                // System.out.println("down size !");
                reSizing(maxSize / 2);
            }
        }
        return ret;
    }

    @Override
    public T get(int index) {
        return items[(front + index) % maxSize];
    }

    // The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;
        public ArrayDequeIterator() { pos = 0;}
        public boolean hasNext() { return pos != size; }
        public T next() {
            if (hasNext())
                return get(pos ++);
            return null;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
}
