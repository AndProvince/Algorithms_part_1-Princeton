import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int num = 1;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            String[] subStr;
            subStr = str.split(" ");
            double p = 1.0f / num;
            for (int i = 0; i < subStr.length; i++) {
                if (StdRandom.bernoulli(p))
                    champion = subStr[i];
                num++;
            }
        }
        StdOut.println(champion);
    }
}
