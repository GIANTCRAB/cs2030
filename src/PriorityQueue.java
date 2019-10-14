import java.util.Optional;

public class PriorityQueue<T> {
    /**
     * The actual priority queue that stores the items.
     */
    private final java.util.PriorityQueue<T> pq;

    /**
     * Constructor for an empty priority queue.
     **/
    public PriorityQueue() {
        pq = new java.util.PriorityQueue<>();
    }

    /**
     * Add an object into the priority queue following the
     * add() method of the JCF PriorityQueue.  Return the
     * priority queue after the object is added.
     *
     * @param object The item to add
     **/
    public PriorityQueue<T> add(T object) {
        final PriorityQueue<T> pqCopy = this.clone();
        pqCopy.pq.add(object);
        return pqCopy;
    }

    /**
     * Return as a pair, (i) the next priortized item according
     * to the natural order of the objects in the priority queue,
     * and (ii) the priority queue after the item is removed.
     *
     * @return The pair (item, priority queue)
     **/
    public Pair<Optional<T>, PriorityQueue<T>> poll() {
        final PriorityQueue<T> pqCopy = this.clone();
        Optional<T> t = Optional.ofNullable(pqCopy.pq.poll());
        return Pair.of(t, pqCopy);
    }

    public PriorityQueue<T> clone() {
        final PriorityQueue<T> pqCopy = new PriorityQueue<>();
        pqCopy.pq.addAll(this.pq);
        return pqCopy;
    }
}
