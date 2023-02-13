/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

private class Event implements Comparable<Event> {
    private double time;
    private Ball a, b;
    private int countA, countB;

    public Event(double t, Ball a, Ball b) {

    }

    public int isCompareTo(Event that) {
        return (int) (this.time - that.time);
    }

    public boolean isValid() {

    }

    public static void main(String[] args) {

    }
}
