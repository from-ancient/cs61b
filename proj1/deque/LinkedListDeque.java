package deque;

import org.junit.Assert;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    public class StuffNode {
        T item;
        StuffNode prev;
        StuffNode next;

        public StuffNode(T item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

        public StuffNode(T item, StuffNode prev, StuffNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    private StuffNode sentFront;
    private StuffNode sentBack;
    private int size;

    /* Constructor */
    public LinkedListDeque() {
        sentFront = new StuffNode(null);
        sentBack  = new StuffNode(null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
        size = 0;
    }

    /* add and remove operations must not involve any looping or recursion.
     A single such operation must take “constant time”,
    * */
    @Override
    public void addFirst(T item) {
        StuffNode head = sentFront.next;
        StuffNode newNode = new StuffNode(item, sentFront, head);
        sentFront.next = newNode;
        head.prev = newNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
       StuffNode tail = sentBack.prev;
       StuffNode newNode = new StuffNode(item, tail, sentBack);
       sentBack.prev = newNode;
       tail.next = newNode;
       size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (StuffNode p = sentFront.next; p != sentBack; p = p.next) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            StuffNode head = sentFront.next;
            sentFront.next = head.next;
            head.next.prev = sentFront;
            // clear head
            head.prev = head.next = null;
            size -= 1;
            return head.item;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            // Assert.fail();
            return null;
        } else {
            StuffNode tail = sentBack.prev;
            sentBack.prev = tail.prev;
            tail.prev.next = sentBack;
            // clear tail
            tail.prev = tail.next = null;
            size -= 1;
            return tail.item;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            Assert.fail();
        }

        StuffNode p = sentFront.next;
        int cnt = 0;
        while (p != null) {
            if (cnt == index) {
                break;
            }
            cnt += 1;
            p = p.next;
        }
        return p.item;
    }
    // // Same as get, but uses recursion
    public T helper(int index, StuffNode node) {
        if (index == 0) {
            return node.item;
        } else {
            return helper(index - 1, node.next);
        }
    }
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            Assert.fail();
        }
        return helper(index, sentFront.next);
    }

    // The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
    private class LinkedListDequeIterator implements Iterator<T> {
        private int Pos;
        public LinkedListDequeIterator() { Pos = 0; }
        public boolean hasNext() { return Pos < size; }
        public T next() {
            T returnItem = get(Pos);
            Pos += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Deque)) { return false;}
        for (int i = 0; i < size(); i++) {
            if (!(get(i).equals(((Deque<?>) o).get(i)))) {
                return false;
            }
        }
        return  true;
    }
}
