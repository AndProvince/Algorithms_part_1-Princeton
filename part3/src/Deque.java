/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int s = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public boolean isEmpty() {
        return s == 0;
    }

    public int size() {
        return s;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item was not null");
        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        first.prev = null;
        first.item = item;
        if (oldfirst != null) oldfirst.prev = first;
        else last = first;
        s++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item was not null");
        Node oldlast = last;
        last = new Node();
        last.prev = oldlast;
        last.next = null;
        last.item = item;
        if (oldlast != null) oldlast.next = last;
        else first = last;
        s++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Node res = first;
        first = first.next;
        if (first != null) first.prev = null;
        res.next = null;
        s--;
        return res.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Node res = last;
        last = last.prev;
        if (last != null) last.next = null;
        res.prev = null;
        s--;
        return res.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        StdOut.println(dq.isEmpty());
        StdOut.println("Size deque: " + dq.size());
        for (int i = 0; i < 3; i++) {
            dq.addLast(Integer.toString(i));
            dq.addFirst(Integer.toString(i + 6));
        }

        StdOut.println("Size deque: " + dq.size());
        StdOut.println("Last item: " + dq.removeLast());
        StdOut.println("Last item: " + dq.removeLast());
        StdOut.println("First item: " + dq.removeFirst());
        StdOut.println("Size deque: " + dq.size());

        for (String s : dq) {
            StdOut.println(s);
        }
        StdOut.println(dq.iterator().next());
        StdOut.println("Last item: " + dq.removeLast());
        StdOut.println("First item: " + dq.removeFirst());
        StdOut.println("Last item: " + dq.removeLast());
        StdOut.println("Size deque: " + dq.size());
        StdOut.println(dq.isEmpty());

    }
}
