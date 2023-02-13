/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

public class ResizingArrayStackOfString {
    private String[] s;
    private int N = 0;

    public ResizingArrayStackOfString() {
        s = new String[1];
    }

    public void push(String item) {
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < s.length; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public String pop() {
        String item = s[--N];
        if (N > 0 && N == s.length / 4) {
            resize(s.length / 2);
        }
        s[N] = null;
        return item;
    }

    public static void main(String[] args) {

    }
}
