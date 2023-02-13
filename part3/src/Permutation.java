/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            rq.enqueue(str);
        }

        Iterator<String> itr = rq.iterator();
        int i = 0;
        while (itr.hasNext() && i < k) {
            StdOut.println(itr.next());
            i++;
        }

    }
}
