/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

public class FixedCapacityStackOfString {

    private String[] s;
    private int N = 0;

    public FixedCapacityStackOfString(int capacity) {
        s = new String[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        s[N++] = item;
    }

    public String pop() {
        // return s[--N]; but loitering (хранение ссылок на не используемые объекты)
        String item = s[--N];
        s[N] = null;
        return item;
    }

    public static void main(String[] args) {

    }
}
