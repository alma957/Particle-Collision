package simulation;


public abstract class AbstractEvent implements Event {

    final private double time; 

    /**
     * Constructor for AbstractEvent.
     */
    public AbstractEvent(double time) {
        this.time = time;
    }

    /**
     * Returns the time (in ticks) at which this event will occur.
     */
    @Override
    public double time() {
        return this.time;
    }

    /**
     * Compares this object with the specified Event.
     */
    @Override
    public int compareTo(Event that) {
        return Double.compare(this.time, that.time()); 
    }
}
