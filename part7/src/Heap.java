/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

public class Heap {

    public static void sort(Comparable[] pq) {
        int n = pq.length;
        for (int k = n / 2; k >= 1; k--) {
            sink(pq, k, n);
        }
        while (n > 1) {
            exch(pq, 1, n);
            sink(pq, 1, --n);
        }
    }

    private static void sink(Comparable[] pq, int i, int n) {
        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && less(pq, j, j + 1)) j++;
            if (!less(pq, i, j)) break;
            exch(pq, i, j);
            i = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {

    }
}
