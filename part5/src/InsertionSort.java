/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import java.util.Comparator;

public class InsertionSort {

    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j - 1]))
                    exch(a, j, j - 1);
                else break;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void sortC(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0 && lessC(comparator, a[j], a[j - 1]); j--)
                exchC(a, j, j - 1);
        }
    }

    private static boolean lessC(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exchC(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {

    }
}
