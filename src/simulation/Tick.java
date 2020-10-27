package simulation;

public class Tick extends AbstractEvent {
    /**
     * Constructor for Tick.
     */
    public Tick(double t) {
        super(t);
    }


    /**
     * Lets the event handler know that a tick happen.
     */
    @Override
    public void happen(ParticleEventHandler h) {

        h.reactTo(this);
    }

    /**
     * Returns true if this Tick is valid (it is always valid).
     */
    @Override
    public boolean isValid() {

        return true;
    }
}
