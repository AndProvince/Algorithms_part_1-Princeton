/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item was not null");
        if (n == s.length)
            resize(2 * s.length);
        s[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniformInt(n);
        Item item = s[index];
        s[index] = s[--n];
        s[n] = null;
        if (n > 0 && n == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniformInt(n);
        return s[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int[] visIndex = StdRandom.permutation(n);
        private int curIndex = 0;

        public boolean hasNext() {
            return curIndex < visIndex.length;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            int index = visIndex[curIndex++];
            Item item = s[index];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        StdOut.println(rq.isEmpty());
        StdOut.println("Size : " + rq.size());
        for (int i = 0; i < 5; i++) {
            rq.enqueue(Integer.toString(i));
        }
        StdOut.println(rq.isEmpty());
        StdOut.println("Size : " + rq.size());
        while (!rq.isEmpty()) {
            StdOut.println(rq.dequeue());
        }
        StdOut.println(rq.isEmpty());
        StdOut.println("Size : " + rq.size());
        for (int i = 20; i < 30; i++) {
            rq.enqueue(Integer.toString(i));
        }
        StdOut.println(rq.sample());
        for (String s : rq) {
            StdOut.println(s);
        }
    }
}
