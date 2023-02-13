/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

public class UnorderedMaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int n;

    public UnorderedMaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    /*
    public void insert(Key x) {
        pq[n++] = x;
    }

    public Key delMax() {
        int max = 0;
        for (int i = 1; i < n; i++) {
            if (less(max, i)) max = i;
        }
        exch(max, n - 1);
        return pq[--n];
    }*/

    private boolean less(int v, int w) {
        return pq[v].compareTo(pq[w]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    public void insert(Key x) {
        pq[++n] = x;
        swim(n);
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;
        return max;
    }

    public static void main(String[] args) {

    }
}
