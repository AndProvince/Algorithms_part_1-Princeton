/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class CollisionSystem {
    private MinPQ<Event> pq;
    private double t = 0.0;
    private Ball[] balls;

    public CollisionSystem(Ball[] balls) {

    }

    private void predict(Ball a) {
        if (a == null) return;
        for (int i = 0; i < N; i++) {
            double dt = a.timeToHit(balls[i]);
            pq.insert(new Event(t + dt, a, balls[i]));
        }
        pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
        pq.insert(new Event(t + a.timeToHitHorisontalWall(), null, a));
    }

    private void redraw() {

    }

    public void simulate() {
        pq = new MinPQ<Event>();
        for (int i = 0; i < N; i++) predict(balls[i]);
        pq.insert(new Event(0, null, null));

        while (!pq.isEmpty()) {
            Event event = pq.delMin();
            if (!event.isValid()) continue;
            Ball a = event.a;
            Ball b = event.b;

            for (int i = 0; i < N; i++) {
                balls[i].move(event.time - t);
            }
            t = event.time;

            if (a != null) &&(b != null) a.bounceOff(b);
            else if (a != null) &&(b == null) a.bounceOffVerticalWall();
            else if (a == null) &&(b != null) a.bounceOffHorisontalWall();
            else if (a == null) &&(b == null) redraw();

            predict(a);
            predict(b);
        }
    }

    public static void main(String[] args) {

    }
}
